package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.BungieHelpUpdatesResponse
import com.angelo.destinystatusapp.domain.model.BungieHelpUpdate
import kotlin.time.Duration.Companion.seconds

fun BungieHelpUpdatesResponse.toBungieHelpUpdate() = BungieHelpUpdate(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp?.seconds,
    url = url,
)
