package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieHelpUpdate
import kotlinx.collections.immutable.ImmutableList

interface FetchBungieHelpUpdatesUseCase {
    suspend operator fun invoke(): State<ImmutableList<BungieHelpUpdate>>
}
