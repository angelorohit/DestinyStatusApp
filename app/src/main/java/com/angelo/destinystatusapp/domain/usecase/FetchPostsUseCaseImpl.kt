package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.map
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepository
import com.angelo.destinystatusapp.domain.repository.RemoteBungieChannelPostsRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration.Companion.seconds

class FetchPostsUseCaseImpl : FetchPostsUseCase, KoinComponent {
    private val remoteRepository: RemoteBungieChannelPostsRepository by inject()
    private val daoRepository: BungieChannelPostsDaoRepository by inject()
    private val cacheRepository: BungieChannelPostsCacheRepository by inject()

    companion object {
        private val ARTIFICIAL_DELAY = 1.seconds
    }

    private suspend fun FlowCollector<State<ImmutableList<BungiePost>>>.fetchRemotePosts(
        channelType: BungieChannelType,
    ) {
        // Only fetch from remote source if the cache has expired.
        if (cacheRepository.isExpired(channelType)) {
            remoteRepository.fetchPosts(channelType)
                .map { it.toImmutableList() }
                .also { remoteFetchState ->
                    if (remoteFetchState is State.Success) {
                        // First, save the remote data to the cache.
                        val fetchedPosts = remoteFetchState.data
                        cacheRepository.savePosts(channelType, fetchedPosts)

                        // Then, save the remote data to the local database.
                        daoRepository.savePosts(channelType, fetchedPosts)
                            .map { it.toImmutableList() }
                            .also { localSavedState ->
                                emit(localSavedState)
                            }
                    }

                    emit(remoteFetchState)
                }
        } else {
            delay(ARTIFICIAL_DELAY)
            emit(State.Success(cacheRepository.getPosts(channelType)))
        }
    }

    override suspend fun invoke(channelType: BungieChannelType): Flow<State<ImmutableList<BungiePost>>> {
        return flow {
            // If the cache is empty, then read from persistent storage, and then try to fetch from the remote source.
            // Otherwise, try to fetch from the remote source.
            if (cacheRepository.getPosts(channelType).isEmpty()) {
                daoRepository.readPosts(channelType)
                    .also { localFetchState ->
                        if (localFetchState is State.Success) {
                            cacheRepository.updateCache(channelType, localFetchState.data)
                        } else {
                            emit(localFetchState.map { it.getData() })
                        }

                        fetchRemotePosts(channelType)
                    }
            } else {
                fetchRemotePosts(channelType)
            }
        }
    }
}
