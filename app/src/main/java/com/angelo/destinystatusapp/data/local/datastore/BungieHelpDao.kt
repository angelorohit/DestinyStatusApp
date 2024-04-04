package com.angelo.destinystatusapp.data.local.datastore

import com.angelo.destinystatusapp.proto.BungieHelpPostItems

interface BungieHelpDao {
    suspend fun readBungieHelpPostItems(): BungieHelpPostItems

    suspend fun saveBungieHelpPostItems(items: BungieHelpPostItems)
}
