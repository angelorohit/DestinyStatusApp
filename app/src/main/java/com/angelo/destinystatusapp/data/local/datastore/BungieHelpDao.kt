package com.angelo.destinystatusapp.data.local.datastore

import com.angelo.destinystatusapp.proto.BungiePostItems

interface BungieHelpDao {
    suspend fun readBungieHelpPostItems(): BungiePostItems

    suspend fun saveBungieHelpPostItems(items: BungiePostItems)
}
