package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.map
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepository
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FetchBungieHelpPostsUseCaseImpl : FetchBungieHelpPostsUseCase, KoinComponent {
    private val statusRepository: DestinyStatusRepository by inject()
    private val daoRepository: BungieChannelPostsDaoRepository by inject()
    private val cacheRepository: BungieChannelPostsCacheRepository by inject()

    private suspend fun FlowCollector<State<ImmutableList<BungiePost>>>.fetchRemoteBungieHelpPosts() {
        // Only fetch from remote source if the cache has expired.
        if (cacheRepository.bungieHelpPosts.isExpired()) {
            statusRepository.fetchBungieHelpPosts()
                .map { it.toImmutableList() }
                .also { remoteFetchState ->
                    if (remoteFetchState is State.Success) {
                        // First, save the remote data to the cache.
                        val fetchedPosts = remoteFetchState.data
                        cacheRepository.bungieHelpPosts.saveData(fetchedPosts)

                        // Emit the remote fetch state only after updating the local cache.
                        emit(remoteFetchState)

                        // Then, save the remote data to the local database.
                        daoRepository.saveBungieHelpPosts(fetchedPosts)
                    } else {
                        emit(remoteFetchState)
                    }
                }
        } else {
            emit(State.Success(cacheRepository.bungieHelpPosts.getData()))
        }
    }

    override suspend fun invoke(): Flow<State<ImmutableList<BungiePost>>> {
        return flow {
            // If the cache is empty, then read from persistent storage, and then try to fetch from the remote source.
            // Otherwise, try to fetch from the remote source.
            if (cacheRepository.bungieHelpPosts.getData().isEmpty()) {
                daoRepository.readBungieHelpPosts()
                    .also { localFetchState ->
                        if (localFetchState is State.Success) {
                            cacheRepository.bungieHelpPosts = localFetchState.data
                        } else {
                            emit(localFetchState.map { it.getData() })
                        }

                        fetchRemoteBungieHelpPosts()
                    }
            } else {
                fetchRemoteBungieHelpPosts()
            }
        }
    }
}
