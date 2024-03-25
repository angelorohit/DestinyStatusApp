package com.angelo.destinystatusapp.data.remote

import com.angelo.destinystatusapp.data.State
import com.angelo.destinystatusapp.data.remote.model.DestinyStatusUpdate
import kotlinx.collections.immutable.ImmutableList

interface RemoteDataSource {
    suspend fun fetchDestinyStatusUpdates(): State<ImmutableList<DestinyStatusUpdate>>
}
