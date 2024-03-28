package com.angelo.destinystatusapp.data.remote.model

import com.squareup.moshi.Json

data class BungieHelpUpdatesResponse(
    @Json(name = "id") val id: String? = null,
    @Json(name = "created_at") val createdAt: String? = null,
    @Json(name = "text") val text: String? = null,
    @Json(name = "unix") val timestamp: Long? = null,
    @Json(name = "url") val url: String? = null,
)
