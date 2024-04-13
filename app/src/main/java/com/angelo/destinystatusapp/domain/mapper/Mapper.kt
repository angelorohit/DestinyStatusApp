package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.cache.MemoryCacheImpl
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.proto.BungieHelpPostItems
import com.angelo.destinystatusapp.proto.BungiePostItem
import com.angelo.destinystatusapp.proto.bungieHelpPostItems
import com.angelo.destinystatusapp.proto.bungiePostItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

fun RemoteBungiePost.toDomainModel() = BungiePost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp?.seconds,
    url = url,
)

fun BungiePost.toDao(): BungiePostItem {
    val domainModel: BungiePost = this
    return bungiePostItem {
        id = domainModel.id.orEmpty()
        createdAt = domainModel.createdAt.orEmpty()
        text = domainModel.text.orEmpty()
        timestampSeconds = domainModel.timestamp?.inWholeSeconds ?: 0L
        url = domainModel.url.orEmpty()
    }
}

fun List<BungiePost>.toDao(updateTime: Duration): BungieHelpPostItems = bungieHelpPostItems {
    writeTimestampMillis = updateTime.inWholeMilliseconds
    items += map { it.toDao() }
}

fun BungiePostItem.toDomainModel() = BungiePost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestampSeconds.seconds,
    url = url,
)

fun BungieHelpPostItems.toMemoryCache(freshnessDuration: Duration): MemoryCache<ImmutableList<BungiePost>> =
    MemoryCacheImpl<ImmutableList<BungiePost>>(
        freshnessDuration = freshnessDuration,
        emptyData = persistentListOf(),
    ).apply {
        saveData(
            data = itemsList.map { it.toDomainModel() }.toImmutableList(),
            updateTime = writeTimestampMillis.milliseconds,
        )
    }
