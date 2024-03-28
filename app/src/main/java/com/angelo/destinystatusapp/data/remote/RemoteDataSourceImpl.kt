package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.remote.api.DestinyStatusService

class RemoteDataSourceImpl(private val destinyStatusService: DestinyStatusService) : RemoteDataSource {
    override suspend fun fetchBungieHelpUpdates() = destinyStatusService.fetchBungieHelpUpdates()
}
