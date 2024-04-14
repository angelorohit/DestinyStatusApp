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
    @Json(name = "media_url_https") val imageUrl: String? = null,
    @Json(name = "type") val type: String? = null,
    @Json(name = "sizes") val sizes: RemoteBungiePostMediaSizes? = null,
)

data class RemoteBungiePostMediaSizes(
    @Json(name = "thumb") val thumb: RemoteBungiePostMediaSize? = null,
    @Json(name = "small") val small: RemoteBungiePostMediaSize? = null,
    @Json(name = "medium") val medium: RemoteBungiePostMediaSize? = null,
    @Json(name = "large") val large: RemoteBungiePostMediaSize? = null,
)

data class RemoteBungiePostMediaSize(
    @Json(name = "w") val width: Int? = null,
    @Json(name = "h") val height: Int? = null,
)
