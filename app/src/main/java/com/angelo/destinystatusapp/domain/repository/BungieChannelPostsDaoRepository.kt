package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList

interface BungieChannelPostsDaoRepository {
    suspend fun saveBungieHelpPosts(posts: List<BungiePost>): State<List<BungiePost>>

    suspend fun readBungieHelpPosts(): State<MemoryCache<ImmutableList<BungiePost>>>
}
