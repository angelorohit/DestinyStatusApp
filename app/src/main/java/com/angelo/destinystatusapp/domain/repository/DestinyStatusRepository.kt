package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieHelpUpdate

interface DestinyStatusRepository {
    suspend fun fetchBungieHelpUpdates(): State<List<BungieHelpUpdate>>
}
