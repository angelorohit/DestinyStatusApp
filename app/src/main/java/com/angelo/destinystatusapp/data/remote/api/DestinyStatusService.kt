package com.angelo.destinystatusapp.data.remote.api

import com.angelo.destinystatusapp.data.remote.model.BungieHelpUpdatesResponse
import retrofit2.Response
import retrofit2.http.GET

interface DestinyStatusService {
    @GET(ApiConstants.BUNGIE_HELP_API_PATH)
    suspend fun fetchBungieHelpUpdates(): Response<List<BungieHelpUpdatesResponse>>
}
