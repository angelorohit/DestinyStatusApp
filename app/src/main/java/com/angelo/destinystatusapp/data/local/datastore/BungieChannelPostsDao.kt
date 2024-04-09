package com.angelo.destinystatusapp.data.local.datastore

import com.angelo.destinystatusapp.proto.BungiePostItem

interface BungieChannelPostsDao {
    suspend fun readBungieHelpPostItems(): List<BungiePostItem>
    suspend fun saveBungieHelpPostItems(items: List<BungiePostItem>)
}
