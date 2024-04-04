package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.remote.api.DestinyStatusService
import com.angelo.destinystatusapp.data.remote.exception.RequestErrorException
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost

class RemoteDataSourceImpl(private val destinyStatusService: DestinyStatusService) : RemoteDataSource {
    companion object {
        private const val MAX_UPDATE_ITEMS = 20
    }

    override suspend fun fetchBungieHelpPosts(): List<RemoteBungiePost> {
        val response = destinyStatusService.fetchBungieHelpPosts()
        with(response) {
            if (isSuccessful) {
                return body()?.take(MAX_UPDATE_ITEMS) ?: throw RequestErrorException(code(), "Empty response body")
            } else {
                throw RequestErrorException(code(), errorBody()?.string() ?: "")
            }
        }
    }
}
