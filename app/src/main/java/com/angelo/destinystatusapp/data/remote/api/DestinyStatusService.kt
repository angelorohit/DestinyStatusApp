package com.angelo.destinystatusapp.data.remote.api

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import retrofit2.Response
import retrofit2.http.GET

interface DestinyStatusService {
    @GET(ApiConstants.BUNGIE_HELP_API_PATH)
    suspend fun fetchBungieHelpPosts(): Response<List<RemoteBungiePost>>

    @GET(ApiConstants.DESTINY_2_TEAM_API_PATH)
    suspend fun fetchDestiny2TeamPosts(): Response<List<RemoteBungiePost>>

    @GET(ApiConstants.DESTINY_THE_GAME_API_PATH)
    suspend fun fetchDestinyTheGamePosts(): Response<List<RemoteBungiePost>>
}
