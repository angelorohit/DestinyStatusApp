package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.remote.model.BungieHelpUpdatesResponse
import retrofit2.Response

interface RemoteDataSource {
    suspend fun fetchBungieHelpUpdates(): Response<List<BungieHelpUpdatesResponse>>
}
