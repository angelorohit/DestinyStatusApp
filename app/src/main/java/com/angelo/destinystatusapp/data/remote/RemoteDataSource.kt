package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.remote.model.BungieHelpUpdatesResponse

interface RemoteDataSource {
    suspend fun fetchBungieHelpUpdates(): List<BungieHelpUpdatesResponse>
}
