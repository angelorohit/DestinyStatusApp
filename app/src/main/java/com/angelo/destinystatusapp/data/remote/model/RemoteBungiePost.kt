package com.angelo.destinystatusapp.data.remote.model

import com.squareup.moshi.Json

data class RemoteBungiePost(
    @Json(name = "id") val id: String? = null,
    @Json(name = "created_at") val createdAt: String? = null,
    @Json(name = "text") val text: String? = null,
    @Json(name = "unix") val timestamp: Long? = null,
    @Json(name = "url") val url: String? = null,
    @Json(name = "media") val media: List<RemoteBungiePostMedia>? = emptyList(),
)

data class RemoteBungiePostMedia(
    @Json(name = "id_str") val id: String? = null,
    @Json(name = "media_url_https") val imageUrl: String? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "sizes") val sizes: RemoteBungiePostMediaSizes? = null,
    @Json(name = "video_info") val videoInfo: RemoteBungiePostVideoInfo? = null,
) {
    private fun hasImageUrl() = !imageUrl.isNullOrBlank()

    private fun hasLargeSize(): Boolean =
        sizes?.large?.width?.takeIf { it > 0 } != null && sizes.large.height?.takeIf { it > 0 } != null

    fun getLargeImageUrl(): String? {
        if (!hasImageUrl() || !hasLargeSize()) return null

        return imageUrl?.let { fullPath ->
            val extensionDelimiter = '.'
            val format = if (fullPath.contains(extensionDelimiter)) {
                fullPath.substringAfterLast(extensionDelimiter).lowercase()
            } else {
                // Assume default JPG format if no extension is found.
                "jpg"
            }

            val basePath = fullPath.substringBeforeLast(extensionDelimiter)
            "$basePath?format=$format&name=large"
        }
    }
}

data class RemoteBungiePostMediaSizes(
    @Json(name = "large") val large: RemoteBungiePostMediaSize? = null,
)

data class RemoteBungiePostMediaSize(
    @Json(name = "w") val width: Int? = null,
    @Json(name = "h") val height: Int? = null,
)

data class RemoteBungiePostVideoInfo(
    @Json(name = "variants") val variants: List<RemoteBungiePostVideoVariant>? = null,
)

data class RemoteBungiePostVideoVariant(
    @Json(name = "bitrate") val bitrate: Int? = null,
    @Json(name = "content_type") val contentType: String? = null,
    @Json(name = "url") val url: String? = null,
)
