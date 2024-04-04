package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.data.local.datastore.BungieHelpDao
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.mapper.toDao
import com.angelo.destinystatusapp.domain.mapper.toDomainModel
import com.angelo.destinystatusapp.domain.model.BungieHelpPost
import com.angelo.destinystatusapp.proto.BungieHelpPostItems
import timber.log.Timber

class BungieHelpDaoRepositoryImpl(private val dao: BungieHelpDao) : BungieHelpDaoRepository {
    override suspend fun saveBungieHelpPosts(posts: List<BungieHelpPost>): State<List<BungieHelpPost>> {
        return runCatching { dao.saveBungieHelpPostItems(posts.toDao()) }
            .fold(
                onSuccess = { State.Success(posts) },
                onFailure = { throwable ->
                    Timber.e("Failed to save Bungie Help posts to storage", throwable)
                    State.Error(State.ErrorType.Local.Write)
                }
            )
    }

    override suspend fun readBungieHelpPosts(): State<List<BungieHelpPost>> {
        return runCatching { dao.readBungieHelpPostItems().toState() }
            .getOrElse { throwable ->
                Timber.e("Failed to read Bungie Help posts from storage", throwable)
                State.Error(State.ErrorType.Local.Read)
            }
    }

    private fun BungieHelpPostItems.toState(): State<List<BungieHelpPost>> = State.Success(toDomainModel())
}
