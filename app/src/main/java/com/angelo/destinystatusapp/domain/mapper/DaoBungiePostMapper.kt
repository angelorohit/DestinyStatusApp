package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.domain.cache.MemoryCache
import com.angelo.destinystatusapp.domain.cache.MemoryCacheImpl
import com.angelo.destinystatusapp.proto.BungieChannelPosts
import com.angelo.destinystatusapp.proto.bungieChannelPosts
import com.angelo.destinystatusapp.proto.bungiePost
import com.angelo.destinystatusapp.proto.bungiePostMedia
import com.angelo.destinystatusapp.proto.bungiePostMediaSize
import com.angelo.destinystatusapp.proto.bungiePostMediaSizes
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import com.angelo.destinystatusapp.domain.model.BungiePost as DomainPost
import com.angelo.destinystatusapp.domain.model.BungiePostMedia as DomainPostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaSize as DomainPostMediaSize
import com.angelo.destinystatusapp.domain.model.BungiePostMediaSizes as DomainPostMediaSizes
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType as DomainPostMediaType
import com.angelo.destinystatusapp.proto.BungiePost as DaoPost
import com.angelo.destinystatusapp.proto.BungiePostMedia as DaoPostMedia
import com.angelo.destinystatusapp.proto.BungiePostMediaSize as DaoPostMediaSize
import com.angelo.destinystatusapp.proto.BungiePostMediaSizes as DaoPostMediaSizes
import com.angelo.destinystatusapp.proto.BungiePostMediaType as DaoPostMediaType

private fun DomainPostMedia.toDaoPostMedia(): DaoPostMedia {
    val domainModel: DomainPostMedia = this
    return bungiePostMedia {
        id = domainModel.id.orEmpty()
        imageUrl = domainModel.imageUrl.orEmpty()
        type = when (domainModel.type) {
            DomainPostMediaType.Photo -> DaoPostMediaType.PHOTO
            null -> DaoPostMediaType.UNRECOGNIZED
        }
        sizes = domainModel.sizes?.toDaoPostMediaSizes() ?: bungiePostMediaSizes {}
    }
}

private fun DomainPostMediaSizes.toDaoPostMediaSizes(): DaoPostMediaSizes {
    val domainModel: DomainPostMediaSizes = this
    return bungiePostMediaSizes {
        large = domainModel.large?.toDaoPostMediaSize() ?: bungiePostMediaSize {}
    }
}

private fun DomainPostMediaSize.toDaoPostMediaSize(): DaoPostMediaSize {
    val domainModel: DomainPostMediaSize = this
    return bungiePostMediaSize {
        imageUrl = domainModel.imageUrl.orEmpty()
        width = domainModel.width ?: 0
        height = domainModel.height ?: 0
    }
}

private fun DomainPost.toDaoPost(): DaoPost {
    val domainModel: DomainPost = this
    return bungiePost {
        id = domainModel.id.orEmpty()
        createdAt = domainModel.createdAt.orEmpty()
        text = domainModel.text.orEmpty()
        timestampSeconds = domainModel.timestamp?.inWholeSeconds ?: 0L
        url = domainModel.url.orEmpty()
        media += domainModel.media?.map { it.toDaoPostMedia() }.orEmpty()
    }
}

fun List<DomainPost>.toDaoPosts(updateTime: Duration): BungieChannelPosts = bungieChannelPosts {
    writeTimestampMillis = updateTime.inWholeMilliseconds
    items += map { it.toDaoPost() }
}

private fun DaoPostMediaSize.toDomainPostMediaSize() = DomainPostMediaSize(
    width = width,
    height = height,
    imageUrl = imageUrl,
)

private fun DaoPostMediaSizes.toDomainPostMediaSizes() = DomainPostMediaSizes(
    large = large.takeIf { it.width > 0 && it.height > 0 }?.toDomainPostMediaSize(),
)

private fun DaoPostMediaType.toDomainPostMediaType() = when (this) {
    DaoPostMediaType.PHOTO -> DomainPostMediaType.Photo
    DaoPostMediaType.UNRECOGNIZED -> null
}

private fun DaoPostMedia.toDomainPostMedia() = DomainPostMedia(
    id = id,
    imageUrl = imageUrl,
    type = type.toDomainPostMediaType(),
    sizes = sizes?.toDomainPostMediaSizes(),
)

private fun DaoPost.toDomainPost() = DomainPost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestampSeconds.seconds,
    url = url,
    media = mediaList.map { it.toDomainPostMedia() },
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
