package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.data.local.datastore.BungieChannelPostsDao
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.helper.datetime.clock.Clock
import com.angelo.destinystatusapp.domain.mapper.toDaoPosts
import com.angelo.destinystatusapp.domain.mapper.toDataChannelType
import com.angelo.destinystatusapp.domain.mapper.toMemoryCache
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import timber.log.Timber
import kotlin.time.Duration

class BungieChannelPostsDaoRepositoryImpl : BungieChannelPostsDaoRepository, KoinComponent {
    private val dao: BungieChannelPostsDao by inject()
    private val postsFreshness: Duration by inject(named("bungieChannelPostsFreshness"))
    private val clock: Clock by inject()

    override suspend fun savePosts(channelType: BungieChannelType, posts: List<BungiePost>): State<List<BungiePost>> {
        return runCatching {
            dao.savePosts(
                channelType = channelType.toDataChannelType(),
                channelPosts = posts.toDaoPosts(updateTime = clock.now()),
            )
        }.fold(
            onSuccess = { State.Success(posts) },
            onFailure = { throwable ->
                Timber.e(throwable, "Failed to save Bungie Help posts to storage")
                State.Error(State.ErrorType.Local.Write)
            }
        )
    }

    override suspend fun readPosts(channelType: BungieChannelType): State<MemoryCache<ImmutableList<BungiePost>>> {
        return runCatching {
            val channelPosts = dao.readPosts(channelType.toDataChannelType())
            State.Success(channelPosts.toMemoryCache(postsFreshness))
        }.getOrElse { throwable ->
            Timber.e(throwable, "Failed to read Bungie Help posts from storage")
            State.Error(State.ErrorType.Local.Read)
        }
    }
}
