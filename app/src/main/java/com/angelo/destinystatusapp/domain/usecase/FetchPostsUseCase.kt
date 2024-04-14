package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface FetchPostsUseCase {
    suspend operator fun invoke(
        channelType: BungieChannelType,
        isForceRefresh: Boolean,
    ): Flow<State<ImmutableList<BungiePost>>>
}
