package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieHelpPost
import kotlinx.collections.immutable.ImmutableList

interface FetchBungieHelpUpdatesUseCase {
    suspend operator fun invoke(): State<ImmutableList<BungieHelpPost>>
}
