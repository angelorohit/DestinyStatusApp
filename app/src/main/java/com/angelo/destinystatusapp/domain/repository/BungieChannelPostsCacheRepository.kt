package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList

interface BungieChannelPostsCacheRepository {
    fun readBungieHelpPosts(): ImmutableList<BungiePost>

    fun saveBungieHelpPosts(posts: List<BungiePost>)
}
