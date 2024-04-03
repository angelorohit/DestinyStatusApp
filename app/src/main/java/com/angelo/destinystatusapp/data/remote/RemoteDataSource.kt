package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.remote.model.RemoteBungieHelpPost

interface RemoteDataSource {
    suspend fun fetchBungieHelpPosts(): List<RemoteBungieHelpPost>
}
