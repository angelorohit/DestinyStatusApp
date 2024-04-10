package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.remote.api.DestinyStatusService
import com.angelo.destinystatusapp.data.remote.exception.RequestErrorException
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

class RemoteDataSourceImpl : RemoteDataSource, KoinComponent {
    companion object {
        private const val MAX_UPDATE_ITEMS = 20
    }

    private val statusService: DestinyStatusService by inject()

    override suspend fun fetchBungieHelpPosts(): List<RemoteBungiePost> =
        statusService.fetchBungieHelpPosts().parseResponse()

    private fun <T> Response<List<T>>.parseResponse(): List<T> {
        if (isSuccessful) {
            return body()?.take(MAX_UPDATE_ITEMS) ?: throw RequestErrorException(code(), "Empty response body")
        } else {
            throw RequestErrorException(code(), errorBody()?.string() ?: "")
        }
    }
}
