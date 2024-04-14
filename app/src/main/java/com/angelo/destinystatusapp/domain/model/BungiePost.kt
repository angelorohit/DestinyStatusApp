package com.angelo.destinystatusapp.domain.model

import kotlin.time.Duration

data class BungiePost(
    val id: String?,
    val createdAt: String?,
    val text: String?,
    val timestamp: Duration?,
    val url: String?,
    val media: List<BungiePostMedia>?,
)

data class BungiePostMedia(
    val imageUrl: String?,
    val type: BungiePostMediaType?,
    val sizes: BungiePostMediaSizes?,
)

enum class BungiePostMediaType {
    Photo,
}

data class BungiePostMediaSizes(
    val thumb: BungiePostMediaSize?,
    val small: BungiePostMediaSize?,
    val medium: BungiePostMediaSize?,
    val large: BungiePostMediaSize?,
)

data class BungiePostMediaSize(
    val width: Int?,
    val height: Int?,
)
