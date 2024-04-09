package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.proto.BungiePostItem
import com.angelo.destinystatusapp.proto.bungiePostItem
import kotlin.time.Duration.Companion.seconds

fun RemoteBungiePost.toDomainModel() = BungiePost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp?.seconds,
    url = url,
)

fun BungiePost.toDao(): BungiePostItem {
    val domainModel: BungiePost = this
    return bungiePostItem {
        id = domainModel.id.orEmpty()
        createdAt = domainModel.createdAt.orEmpty()
        text = domainModel.text.orEmpty()
        timestamp = domainModel.timestamp?.inWholeSeconds ?: 0L
        url = domainModel.url.orEmpty()
    }
}

fun BungiePostItem.toDomainModel() = BungiePost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp.seconds,
    url = url,
)
