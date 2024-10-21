package com.angelo.destinystatusapp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteBungiePost(
    @SerialName("id") val id: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("user") val userName: String? = null,
    @SerialName("text") val text: String? = null,
    @SerialName("thread_text") val threadText: String? = null,
    @SerialName("unix") val timestamp: Double? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("media") val media: List<RemoteBungiePostMedia>? = emptyList(),
    @SerialName("retweeted_tweet") val repost: RemoteBungiePost? = null,
) {
    fun RemoteBungiePost.getLastRepost(): RemoteBungiePost {
        return generateSequence(this) { it.repost }
            .lastOrNull() ?: this
    }
}

@Serializable
data class RemoteBungiePostMedia(
    @SerialName("id_str") val id: String? = null,
    @SerialName("media_url_https") val imageUrl: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("sizes") val sizes: RemoteBungiePostMediaSizes? = null,
    @SerialName("video_info") val videoInfo: RemoteBungiePostVideoInfo? = null,
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

@Serializable
data class RemoteBungiePostMediaSizes(
    @SerialName("large") val large: RemoteBungiePostMediaSize? = null,
)

@Serializable
data class RemoteBungiePostMediaSize(
    @SerialName("w") val width: Int? = null,
    @SerialName("h") val height: Int? = null,
)

@Serializable
data class RemoteBungiePostVideoInfo(
    @SerialName("aspect_ratio") val aspectRatio: List<Int>? = null,
    @SerialName("variants") val variants: List<RemoteBungiePostVideoVariant>? = null,
)

@Serializable
data class RemoteBungiePostVideoVariant(
    @SerialName("bitrate") val bitrate: Int? = null,
    @SerialName("content_type") val contentType: String? = null,
    @SerialName("url") val url: String? = null,
)
