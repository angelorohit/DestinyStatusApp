package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.map
import com.angelo.destinystatusapp.domain.model.BungieHelpPost
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class FetchBungieHelpUpdatesUseCaseImpl(
    private val repository: DestinyStatusRepository,
) : FetchBungieHelpUpdatesUseCase {
    override suspend operator fun invoke(): State<ImmutableList<BungieHelpPost>> {
        return repository.fetchBungieHelpPosts().map {
            it.toImmutableList()
        }
    }
}
