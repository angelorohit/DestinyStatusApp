package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungiePost
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface FetchBungieHelpPostsUseCase {
    suspend operator fun invoke(): Flow<State<ImmutableList<BungiePost>>>
}
