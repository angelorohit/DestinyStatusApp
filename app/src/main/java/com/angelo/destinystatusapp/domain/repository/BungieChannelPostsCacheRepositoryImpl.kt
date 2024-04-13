package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.cache.MemoryCacheImpl
import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import kotlin.time.Duration

class BungieChannelPostsCacheRepositoryImpl : BungieChannelPostsCacheRepository, KoinComponent {
    private val bungieHelpPostsFreshness: Duration by inject(named("bungieHelpPostsFreshness"))

    override var bungieHelpPosts: MemoryCache<ImmutableList<BungiePost>> = MemoryCacheImpl(
        freshnessDuration = bungieHelpPostsFreshness,
        emptyData = persistentListOf(),
    )
}
