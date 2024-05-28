package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePostMedia
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePostMediaSize
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePostMediaSizes
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePostVideoInfo
import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePostVideoVariant
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaSize
import com.angelo.destinystatusapp.domain.model.BungiePostMediaSizes
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import com.angelo.destinystatusapp.domain.model.BungiePostVideoInfo
import com.angelo.destinystatusapp.domain.model.BungiePostVideoQuality
import com.angelo.destinystatusapp.domain.model.BungiePostVideoVariant
import kotlin.time.Duration.Companion.seconds

private fun RemoteBungiePostMediaSize.toDomainSize(largeImageUrl: String?) = BungiePostMediaSize(
    imageUrl = largeImageUrl,
    width = width,
    height = height,
)

private fun RemoteBungiePostMediaSizes.toDomainSizes(largeImageUrl: String?) = BungiePostMediaSizes(
    large = large?.toDomainSize(largeImageUrl),
)

private fun RemoteBungiePostVideoInfo.toDomainVideoInfo(): BungiePostVideoInfo {
    val qualityToVariantMap = variants
        ?.groupBy { it.toDomainVideoVariant().quality }
        ?.mapValues { (_, variants) -> variants.maxByOrNull { it.bitrate ?: Int.MIN_VALUE } }

    val domainVariants = qualityToVariantMap?.mapNotNull { (_, variant) ->
        variant?.toDomainVideoVariant()
    }

    return BungiePostVideoInfo(
        aspectRatio = aspectRatio?.takeIf { it.size == 2 }?.let { aspectRatio ->
            aspectRatio[0].toFloat() / aspectRatio[1]
        },
        variants = domainVariants,
    )
}

private fun RemoteBungiePostVideoVariant.toDomainVideoVariant() = BungiePostVideoVariant(
    quality = when (bitrate) {
        in 0..500_000 -> BungiePostVideoQuality.Low
        in 500_001..1_000_000 -> BungiePostVideoQuality.Medium
        in 1_000_001..3_000_000 -> BungiePostVideoQuality.HD720
        else -> BungiePostVideoQuality.HD1080
    },
    url = url,
)

private fun RemoteBungiePostMedia.toDomainMedia() = BungiePostMedia(
    id = id,
    imageUrl = imageUrl,
    type = when (type) {
        "photo" -> BungiePostMediaType.Photo
        "animated_gif", "video" -> BungiePostMediaType.Video
        else -> null
    },
    sizes = sizes?.toDomainSizes(largeImageUrl = getLargeImageUrl()),
    videoInfo = videoInfo?.toDomainVideoInfo(),
)

fun RemoteBungiePost.toDomainPost(): BungiePost {
    val firstPost = this
    return with(getLastRepost()) {
        BungiePost(
            id = id,
            createdAt = createdAt,
            userName = userName,
            text = (threadText ?: text).fixErrantNewlines(),
            timestamp = timestamp?.seconds,
            url = url,
            media = media?.map { it.toDomainMedia() },
            isRepost = this != firstPost,
        )
    }
}

private fun String?.fixErrantNewlines() = this?.replace("\\\\n", "\n")
