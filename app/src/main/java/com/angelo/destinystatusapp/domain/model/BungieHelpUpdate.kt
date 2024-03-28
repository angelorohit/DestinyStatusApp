package com.angelo.destinystatusapp.domain.model

import kotlin.time.Duration

data class BungieHelpUpdate(
    val id: String?,
    val createdAt: String?,
    val text: String?,
    val timestamp: Duration?,
    val url: String?,
)
