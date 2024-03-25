package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.State
import com.angelo.destinystatusapp.data.remote.api.DestinyStatusService
import com.angelo.destinystatusapp.data.remote.exception.NoConnectivityException
import com.angelo.destinystatusapp.data.remote.model.DestinyStatusUpdate
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import retrofit2.Response
import java.net.SocketTimeoutException

class RemoteDataSourceImpl(private val destinyStatusService: DestinyStatusService) : RemoteDataSource {
    companion object {
        private const val MAX_UPDATES_PER_REQUEST = 10
    }

    override suspend fun fetchDestinyStatusUpdates(): State<ImmutableList<DestinyStatusUpdate>> {
        return runCatching { destinyStatusService.fetchUpdates().toState() }
            .getOrElse { throwable -> throwable.toState() }
    }

    private fun Response<List<DestinyStatusUpdate>>.toState(): State<ImmutableList<DestinyStatusUpdate>> {
        return if (isSuccessful) {
            body()?.let { updates ->
                State.Success(updates.take(MAX_UPDATES_PER_REQUEST).toImmutableList())
            } ?: State.Error(State.ErrorType.Remote.Unknown)
        } else {
            State.Error(State.ErrorType.Remote.Unknown)
        }
    }

    private fun Throwable.toState(): State<ImmutableList<DestinyStatusUpdate>> {
        val errorType = when (this) {
            is NoConnectivityException -> State.ErrorType.Remote.NoConnectivity
            is SocketTimeoutException -> State.ErrorType.Remote.Timeout
            else -> State.ErrorType.Remote.Unknown
        }

        return State.Error(errorType)
    }
}
