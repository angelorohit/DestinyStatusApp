package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.RemoteBungieHelpPost
import com.angelo.destinystatusapp.domain.model.BungieHelpPost
import kotlin.time.Duration.Companion.seconds

fun RemoteBungieHelpPost.toDomainModel() = BungieHelpPost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp?.seconds,
    url = url,
)
