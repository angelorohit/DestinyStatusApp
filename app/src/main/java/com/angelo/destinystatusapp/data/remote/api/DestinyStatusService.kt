package com.angelo.destinystatusapp.data.remote.api

import com.angelo.destinystatusapp.data.remote.model.DestinyStatusUpdate
import retrofit2.Response
import retrofit2.http.GET

interface DestinyStatusService {
    @GET(ApiConstants.API_PATH)
    suspend fun fetchUpdates(): Response<List<DestinyStatusUpdate>>
}
