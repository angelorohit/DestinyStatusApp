package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.cache.MemoryCacheImpl
import com.angelo.destinystatusapp.proto.BungieChannelPosts
import com.angelo.destinystatusapp.proto.bungieChannelPosts
import com.angelo.destinystatusapp.proto.bungiePost
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import com.angelo.destinystatusapp.data.model.BungieChannelType as DataChannelType
import com.angelo.destinystatusapp.domain.model.BungieChannelType as DomainChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost as DomainPost
import com.angelo.destinystatusapp.proto.BungiePost as DaoPost

fun DomainChannelType.toDataChannelType(): DataChannelType = when (this) {
    DomainChannelType.BungieHelp -> DataChannelType.BungieHelp
    DomainChannelType.Destiny2Team -> DataChannelType.Destiny2Team
    DomainChannelType.DestinyTheGame -> DataChannelType.DestinyTheGame
}

fun RemoteBungiePost.toDomainPost() = DomainPost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp?.seconds,
    url = url,
)

private fun DomainPost.toDaoPost(): DaoPost {
    val domainModel: DomainPost = this
    return bungiePost {
        id = domainModel.id.orEmpty()
        createdAt = domainModel.createdAt.orEmpty()
        text = domainModel.text.orEmpty()
        timestampSeconds = domainModel.timestamp?.inWholeSeconds ?: 0L
        url = domainModel.url.orEmpty()
    }
}

fun List<DomainPost>.toDaoPosts(updateTime: Duration): BungieChannelPosts = bungieChannelPosts {
    writeTimestampMillis = updateTime.inWholeMilliseconds
    items += map { it.toDaoPost() }
}

private fun DaoPost.toDomainPost() = DomainPost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestampSeconds.seconds,
    url = url,
)

fun BungieChannelPosts.toMemoryCache(freshnessDuration: Duration): MemoryCache<ImmutableList<DomainPost>> =
    MemoryCacheImpl<ImmutableList<DomainPost>>(
        freshnessDuration = freshnessDuration,
        emptyData = persistentListOf(),
    ).apply {
        saveData(
            data = itemsList.map { it.toDomainPost() }.toImmutableList(),
            updateTime = writeTimestampMillis.milliseconds,
        )
    }
