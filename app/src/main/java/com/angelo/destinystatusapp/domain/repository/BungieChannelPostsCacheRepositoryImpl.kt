package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.cache.MemoryCacheImpl
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.time.Duration

class BungieChannelPostsCacheRepositoryImpl : BungieChannelPostsCacheRepository, KoinComponent {
    private val postsFreshness: Duration by inject(named("bungieChannelPostsFreshness"))
    private val channelPostCaches: MutableMap<BungieChannelType, MemoryCache<ImmutableList<BungiePost>>> =
        enumValues<BungieChannelType>().associateWith { createEmptyPostsCache() }.toMutableMap()

    private fun createEmptyPostsCache(): MemoryCache<ImmutableList<BungiePost>> =
        MemoryCacheImpl(freshnessDuration = postsFreshness, emptyData = persistentListOf())

    override fun getPosts(channelType: BungieChannelType): ImmutableList<BungiePost> {
        return channelPostCaches[channelType]?.getData() ?: persistentListOf()
    }

    override fun savePosts(channelType: BungieChannelType, posts: ImmutableList<BungiePost>) {
        channelPostCaches[channelType]?.saveData(posts)
    }

    override fun isExpired(channelType: BungieChannelType): Boolean {
        return channelPostCaches[channelType]?.isExpired() ?: true
    }

    override fun updateCache(channelType: BungieChannelType, cache: MemoryCache<ImmutableList<BungiePost>>) {
        channelPostCaches[channelType] = cache
    }
}
