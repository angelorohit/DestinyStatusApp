package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieHelpPost

interface DestinyStatusRepository {
    suspend fun fetchBungieHelpPosts(): State<List<BungieHelpPost>>
}
