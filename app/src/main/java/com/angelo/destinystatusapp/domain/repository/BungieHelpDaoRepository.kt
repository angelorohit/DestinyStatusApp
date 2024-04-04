package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieHelpPost

interface BungieHelpDaoRepository {
    suspend fun saveBungieHelpPosts(posts: List<BungieHelpPost>): State<List<BungieHelpPost>>

    suspend fun readBungieHelpPosts(): State<List<BungieHelpPost>>
}
