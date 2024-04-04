package com.angelo.destinystatusapp.domain.mapper

import com.angelo.destinystatusapp.data.remote.model.RemoteBungieHelpPost
import com.angelo.destinystatusapp.domain.model.BungieHelpPost
import com.angelo.destinystatusapp.proto.BungieHelpPostItem
import com.angelo.destinystatusapp.proto.BungieHelpPostItems
import com.angelo.destinystatusapp.proto.bungieHelpPostItem
import com.angelo.destinystatusapp.proto.bungieHelpPostItems
import kotlin.time.Duration.Companion.seconds

fun RemoteBungieHelpPost.toDomainModel() = BungieHelpPost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp?.seconds,
    url = url,
)

private fun BungieHelpPost.toDao(): BungieHelpPostItem {
    val domainModel: BungieHelpPost = this
    return bungieHelpPostItem {
        id = domainModel.id.orEmpty()
        createdAt = domainModel.createdAt.orEmpty()
        text = domainModel.text.orEmpty()
        timestamp = domainModel.timestamp?.inWholeSeconds ?: 0L
        url = domainModel.url.orEmpty()
    }
}

fun List<BungieHelpPost>.toDao(): BungieHelpPostItems = bungieHelpPostItems {
    items.addAll(map { post -> post.toDao() })
}

fun BungieHelpPostItem.toDomainModel() = BungieHelpPost(
    id = id,
    createdAt = createdAt,
    text = text,
    timestamp = timestamp.seconds,
    url = url,
)

fun BungieHelpPostItems.toDomainModel(): List<BungieHelpPost> = itemsList.map { item -> item.toDomainModel() }
