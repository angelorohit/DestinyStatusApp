package com.angelo.destinystatusapp.domain.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.time.Duration

data class BungiePost(
    val id: String?,
    val createdAt: String?,
    val userName: String?,
    val text: String?,
    val timestamp: Duration?,
    val url: String?,
    val media: List<BungiePostMedia>?,
    val isRepost: Boolean?,
) {
    fun getValidMedia(): ImmutableList<BungiePostMedia> =
        media?.filter { it.hasImageUrl() }?.toImmutableList() ?: persistentListOf()
}

data class BungiePostMedia(
    val id: String?,
    val imageUrl: String?,
    val type: BungiePostMediaType?,
    val sizes: BungiePostMediaSizes?,
    val videoInfo: BungiePostVideoInfo?,
) {
    fun hasImageUrl() = !imageUrl.isNullOrBlank()
}

enum class BungiePostMediaType {
    Photo,
    Video,
}

data class BungiePostMediaSizes(
    val large: BungiePostMediaSize?,
)

data class BungiePostMediaSize(
    val imageUrl: String?,
    val width: Int?,
    val height: Int?,
) {
    val aspectRatio = width?.toFloat()?.div(height ?: 1) ?: 1f
}

data class BungiePostVideoInfo(
    val aspectRatio: Float?,
    val variants: List<BungiePostVideoVariant>?,
) {
    fun getHighestQualityUrl(): String? {
        return BungiePostVideoQuality.entries.reversed().firstNotNullOfOrNull { quality ->
            variants?.find { it.quality == quality }
        }?.url
    }
}

data class BungiePostVideoVariant(
    val quality: BungiePostVideoQuality?,
    val url: String?,
)

// Make sure this is in order of lowest quality to highest.
enum class BungiePostVideoQuality {
    Low,
    Medium,
    HD720,
    HD1080,
}
