package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.data.remote.RemoteDataSource
import com.angelo.destinystatusapp.data.remote.exception.NoConnectivityException
import com.angelo.destinystatusapp.data.remote.exception.RequestErrorException
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.mapper.toDataChannelType
import com.angelo.destinystatusapp.domain.mapper.toDomainPost
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.net.SocketTimeoutException

class RemoteBungieChannelPostsRepositoryImpl : RemoteBungieChannelPostsRepository, KoinComponent {
    private val remoteDataSource: RemoteDataSource by inject()

    override suspend fun fetchPosts(channelType: BungieChannelType): State<List<BungiePost>> {
        return runCatching {
            remoteDataSource.fetchPosts(channelType.toDataChannelType()).toState()
        }.getOrElse { throwable ->
            Timber.e(throwable, "Failed to fetch Bungie Help Posts")
            throwable.toState()
        }
    }

    private fun List<RemoteBungiePost>.toState(): State<List<BungiePost>> {
        return State.Success(map { it.toDomainPost() })
    }

    private fun Throwable.toState(): State<List<BungiePost>> {
        val errorType = when (this) {
            is RequestErrorException -> State.ErrorType.Remote.Request(httpStatusCode, errorBody)
            is NoConnectivityException -> State.ErrorType.Remote.NoConnectivity
            is SocketTimeoutException -> State.ErrorType.Remote.Timeout
            else -> State.ErrorType.Remote.Unknown
        }

        return State.Error(errorType)
    }
}
