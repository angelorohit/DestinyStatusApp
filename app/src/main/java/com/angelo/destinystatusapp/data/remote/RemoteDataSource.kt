package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.remote.model.RemoteBungiePost

interface RemoteDataSource {
    suspend fun fetchBungieHelpPosts(): List<RemoteBungiePost>
}
