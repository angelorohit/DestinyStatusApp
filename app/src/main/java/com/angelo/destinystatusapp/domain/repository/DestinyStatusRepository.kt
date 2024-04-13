package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost

interface DestinyStatusRepository {
    suspend fun fetchPosts(channelType: BungieChannelType): State<List<BungiePost>>
}
