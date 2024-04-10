package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.map
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepository
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import com.angelo.destinystatusapp.presentation.helper.datetime.clock.Clock
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class FetchBungieHelpPostsUseCaseImpl : FetchBungieHelpPostsUseCase, KoinComponent {
    private companion object {
        // Only allow refresh to happen every one and a half minutes.
        val UPDATE_INTERVAL = 1.5.minutes
    }

    private val statusRepository: DestinyStatusRepository by inject()
    private val daoRepository: BungieChannelPostsDaoRepository by inject()
    private val cacheRepository: BungieChannelPostsCacheRepository by inject()
    private val clock: Clock by inject()

    private var lastUpdateTime: Duration = Duration.ZERO

    override suspend fun invoke(): Flow<State<ImmutableList<BungiePost>>> {
        return flow {
            if (clock.exceedsThreshold(lastUpdateTime, UPDATE_INTERVAL)) {
                lastUpdateTime = clock.now()

                statusRepository.fetchBungieHelpPosts()
                    .map { it.toImmutableList() }
                    .also { remoteFetchState ->
                        emit(remoteFetchState)

                        when (remoteFetchState) {
                            is State.Success -> {
                                val existingData = remoteFetchState.data
                                cacheRepository.saveBungieHelpPosts(existingData)
                                daoRepository.saveBungieHelpPosts(existingData)
                                    .map { it.toImmutableList() }
                                    .also { localSaveState ->
                                        emit(localSaveState)
                                    }
                            }

                            is State.Error -> {
                                daoRepository.readBungieHelpPosts()
                                    .map { it.toImmutableList() }
                                    .also { localFetchState ->
                                        if (localFetchState is State.Success) {
                                            cacheRepository.saveBungieHelpPosts(localFetchState.data)
                                        }
                                        emit(localFetchState)
                                    }
                            }
                        }
                    }
            } else {
                emit(State.Success(cacheRepository.readBungieHelpPosts()))
            }
        }
    }
}
