package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.data.remote.RemoteDataSource
import com.angelo.destinystatusapp.data.remote.exception.NoConnectivityException
import com.angelo.destinystatusapp.data.remote.model.BungieHelpUpdatesResponse
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.mapper.toBungieHelpUpdate
import com.angelo.destinystatusapp.domain.model.BungieHelpUpdate
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException

class DestinyStatusRepositoryImpl(private val remoteDataSource: RemoteDataSource) : DestinyStatusRepository {
    override suspend fun fetchBungieHelpUpdates(): State<List<BungieHelpUpdate>> {
        return runCatching { remoteDataSource.fetchBungieHelpUpdates().toState() }
            .getOrElse { throwable ->
                Timber.e("Failed to fetch Destiny status updates", throwable)
                throwable.toState()
            }
    }

    private fun Response<List<BungieHelpUpdatesResponse>>.toState(): State<List<BungieHelpUpdate>> {
        return if (isSuccessful) {
            body()?.let { updates ->
                State.Success(updates.map { it.toBungieHelpUpdate() })
            } ?: State.Error(State.ErrorType.Remote.Network("Empty response body"))
        } else {
            State.Error(State.ErrorType.Remote.Network(errorBody()?.string() ?: "Network request failed"))
        }
    }

    private fun Throwable.toState(): State<List<BungieHelpUpdate>> {
        val errorType = when (this) {
            is NoConnectivityException -> State.ErrorType.Remote.NoConnectivity
            is SocketTimeoutException -> State.ErrorType.Remote.Timeout
            else -> State.ErrorType.Remote.Unknown
        }

        return State.Error(errorType)
    }
}
