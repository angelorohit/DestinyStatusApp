package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList

class BungieChannelPostsCacheRepositoryImpl : BungieChannelPostsCacheRepository {
    private var bungieHelpPostsCache: ImmutableList<BungiePost> = persistentListOf()

    override fun readBungieHelpPosts(): ImmutableList<BungiePost> = bungieHelpPostsCache

    override fun saveBungieHelpPosts(posts: List<BungiePost>) {
        bungieHelpPostsCache = posts.toImmutableList()
    }
}
