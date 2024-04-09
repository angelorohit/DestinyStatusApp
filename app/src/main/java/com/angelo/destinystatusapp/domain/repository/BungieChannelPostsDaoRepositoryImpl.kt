package com.angelo.destinystatusapp.domain.repository

import com.angelo.destinystatusapp.data.local.datastore.BungieChannelPostsDao
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.mapper.toDao
import com.angelo.destinystatusapp.domain.mapper.toDomainModel
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.proto.BungiePostItem
import timber.log.Timber

class BungieChannelPostsDaoRepositoryImpl(private val dao: BungieChannelPostsDao) : BungieChannelPostsDaoRepository {
    override suspend fun saveBungieHelpPosts(posts: List<BungiePost>): State<List<BungiePost>> {
        return runCatching { dao.saveBungieHelpPostItems(posts.map { it.toDao() }) }
            .fold(
                onSuccess = { State.Success(posts) },
                onFailure = { throwable ->
                    Timber.e(throwable, "Failed to save Bungie Help posts to storage")
                    State.Error(State.ErrorType.Local.Write)
                }
            )
    }

    override suspend fun readBungieHelpPosts(): State<List<BungiePost>> {
        return runCatching { dao.readBungieHelpPostItems().toState() }
            .getOrElse { throwable ->
                Timber.e(throwable, "Failed to read Bungie Help posts from storage")
                State.Error(State.ErrorType.Local.Read)
            }
    }

    private fun List<BungiePostItem>.toState(): State<List<BungiePost>> = State.Success(map { it.toDomainModel() })
}
