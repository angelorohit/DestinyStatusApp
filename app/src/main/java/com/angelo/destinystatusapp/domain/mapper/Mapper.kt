package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.proto.BungiePostItem
import com.angelo.destinystatusapp.proto.BungiePostItems
import com.angelo.destinystatusapp.proto.bungiePostItem
import com.angelo.destinystatusapp.proto.bungiePostItems
import kotlin.time.Duration.Companion.seconds

fun RemoteBungiePost.toDomainModel() = BungiePost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp?.seconds,
    url = url,
)

private fun BungiePost.toDao(): BungiePostItem {
    val domainModel: BungiePost = this
    return bungiePostItem {
        id = domainModel.id.orEmpty()
        createdAt = domainModel.createdAt.orEmpty()
        text = domainModel.text.orEmpty()
        timestamp = domainModel.timestamp?.inWholeSeconds ?: 0L
        url = domainModel.url.orEmpty()
    }
}

fun List<BungiePost>.toDao(): BungiePostItems = bungiePostItems {
    items.addAll(map { post -> post.toDao() })
}

fun BungiePostItem.toDomainModel() = BungiePost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp.seconds,
    url = url,
)

fun BungiePostItems.toDomainModel(): List<BungiePost> = itemsList.map { item -> item.toDomainModel() }
