package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList

interface BungieChannelPostsCacheRepository {
    fun getPosts(channelType: BungieChannelType): ImmutableList<BungiePost>

    fun savePosts(channelType: BungieChannelType, posts: ImmutableList<BungiePost>)

    fun isExpired(channelType: BungieChannelType): Boolean

    fun updateCache(channelType: BungieChannelType, cache: MemoryCache<ImmutableList<BungiePost>>)
}
