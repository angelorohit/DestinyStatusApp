package com.angelo.destinystatusapp.domain.usecase

import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieHelpUpdate
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class FetchBungieHelpUpdatesUseCaseImpl(
    private val repository: DestinyStatusRepository,
) : FetchBungieHelpUpdatesUseCase {
    override suspend fun execute(): State<ImmutableList<BungieHelpUpdate>> {
        return repository.fetchBungieHelpUpdates().toImmutableListState()
    }

    private fun State<List<BungieHelpUpdate>>.toImmutableListState(): State<ImmutableList<BungieHelpUpdate>> {
        return when (this) {
            is State.Success -> State.Success(data = data.toImmutableList())
            is State.Error -> this
        }
    }
}
