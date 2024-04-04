package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungiePost

interface BungieHelpDaoRepository {
    suspend fun saveBungieHelpPosts(posts: List<BungiePost>): State<List<BungiePost>>

    suspend fun readBungieHelpPosts(): State<List<BungiePost>>
}
