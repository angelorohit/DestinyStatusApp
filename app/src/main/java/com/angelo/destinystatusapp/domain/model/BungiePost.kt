package com.angelo.destinystatusapp.domain.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlin.time.Duration

data class BungiePost(
    val id: String?,
    val createdAt: String?,
    val text: String?,
    val timestamp: Duration?,
    val url: String?,
    val media: List<BungiePostMedia>?,
) {
    fun getValidMedia(): ImmutableList<BungiePostMedia> =
        media?.filter { it.hasImageUrl() }?.toImmutableList() ?: persistentListOf()
}

data class BungiePostMedia(
    val imageUrl: String?,
    val type: BungiePostMediaType?,
    val sizes: BungiePostMediaSizes?,
) {
    fun hasImageUrl() = !imageUrl.isNullOrBlank()
}

enum class BungiePostMediaType {
    Photo,
}

data class BungiePostMediaSizes(
    val large: BungiePostMediaSize?,
)

data class BungiePostMediaSize(
    val imageUrl: String?,
    val width: Int?,
    val height: Int?,
)
