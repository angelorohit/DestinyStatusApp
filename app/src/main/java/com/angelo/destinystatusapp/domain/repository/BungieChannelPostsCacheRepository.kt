package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList

interface BungieChannelPostsCacheRepository {
    var bungieHelpPosts: MemoryCache<ImmutableList<BungiePost>>
}
