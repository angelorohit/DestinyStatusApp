package com.angelo.destinystatusapp.data.local.datastore

import com.angelo.destinystatusapp.data.model.BungieChannelType
import com.angelo.destinystatusapp.proto.BungieChannelPosts

interface BungieChannelPostsDao {
    suspend fun readPosts(channelType: BungieChannelType): BungieChannelPosts
    suspend fun savePosts(channelType: BungieChannelType, channelPosts: BungieChannelPosts)
}
