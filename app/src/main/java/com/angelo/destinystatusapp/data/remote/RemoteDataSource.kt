package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.model.BungieChannelType
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost

interface RemoteDataSource {
    suspend fun fetchPosts(channelType: BungieChannelType): List<RemoteBungiePost>
}
