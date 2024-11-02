package com.angelo.destinystatusapp.data.remote.api

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import retrofit2.Response
import retrofit2.http.GET

interface DestinyStatusService {
    companion object {
        const val BUNGIE_HELP_API_PATH = "/data/BungieHelp.json"
        const val DESTINY_2_TEAM_API_PATH = "/data/Destiny2Team.json"
        const val DESTINY_THE_GAME_API_PATH = "/data/DestinyTheGame.json"
    }

    @GET(BUNGIE_HELP_API_PATH)
    suspend fun fetchBungieHelpPosts(): Response<List<RemoteBungiePost>>

    @GET(DESTINY_2_TEAM_API_PATH)
    suspend fun fetchDestiny2TeamPosts(): Response<List<RemoteBungiePost>>

    @GET(DESTINY_THE_GAME_API_PATH)
    suspend fun fetchDestinyTheGamePosts(): Response<List<RemoteBungiePost>>
}
