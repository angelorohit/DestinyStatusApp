package com.angelo.destinystatusapp.data.local.datastore

import com.angelo.destinystatusapp.proto.BungieHelpPostItems

interface BungieChannelPostsDao {
    suspend fun readBungieHelpPostItems(): BungieHelpPostItems
    suspend fun saveBungieHelpPostItems(bungieHelpPostItems: BungieHelpPostItems)
}
