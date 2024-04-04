package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungiePost

interface DestinyStatusRepository {
    suspend fun fetchBungieHelpPosts(): State<List<BungiePost>>
}
