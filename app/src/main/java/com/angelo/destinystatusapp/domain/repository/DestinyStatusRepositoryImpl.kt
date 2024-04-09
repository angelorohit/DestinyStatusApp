package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.data.remote.RemoteDataSource
import com.angelo.destinystatusapp.data.remote.exception.NoConnectivityException
import com.angelo.destinystatusapp.data.remote.exception.RequestErrorException
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.mapper.toDomainModel
import com.angelo.destinystatusapp.domain.model.BungiePost
import timber.log.Timber
import java.net.SocketTimeoutException

class DestinyStatusRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DestinyStatusRepository {
    override suspend fun fetchBungieHelpPosts(): State<List<BungiePost>> {
        return runCatching { remoteDataSource.fetchBungieHelpPosts().toState() }
            .getOrElse { throwable ->
                Timber.e(throwable, "Failed to fetch Bungie Help Posts")
                throwable.toState()
            }
    }

    private fun List<RemoteBungiePost>.toState(): State<List<BungiePost>> {
        return State.Success(map { it.toDomainModel() })
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
