package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePostMedia
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePostMediaSize
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePostMediaSizes
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaSize
import com.angelo.destinystatusapp.domain.model.BungiePostMediaSizes
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import kotlin.time.Duration.Companion.seconds

private fun RemoteBungiePostMediaSize.toDomainSize() = BungiePostMediaSize(
    width = width,
    height = height,
)

private fun RemoteBungiePostMediaSizes.toDomainSizes() = BungiePostMediaSizes(
    thumb = thumb?.toDomainSize(),
    small = small?.toDomainSize(),
    medium = medium?.toDomainSize(),
    large = large?.toDomainSize(),
)

private fun RemoteBungiePostMedia.toDomainMedia() = BungiePostMedia(
    imageUrl = imageUrl,
    type = type?.let { BungiePostMediaType.Photo },
    sizes = sizes?.toDomainSizes(),
)

fun RemoteBungiePost.toDomainPost() = BungiePost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp?.seconds,
    url = url,
    media = media?.map { it.toDomainMedia() },
)
