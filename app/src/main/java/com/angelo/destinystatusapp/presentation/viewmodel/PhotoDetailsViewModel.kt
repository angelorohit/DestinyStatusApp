package com.angelo.destinystatusapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PhotoDetailsViewModel(
    channelType: BungieChannelType,
    postId: String,
    mediaId: String,
) : ViewModel(), KoinComponent {
    private val cacheRepository: BungieChannelPostsCacheRepository by inject()

    var title: String private set
    var photoUrl: String private set
    var photoAspectRatio: Float private set

    init {
        val bungiePost = cacheRepository.getPosts(channelType).find { bungiePost -> bungiePost.id == postId }
        val bungiePostMedia = bungiePost?.media?.find { bungiePostMedia -> bungiePostMedia.id == mediaId }

        title = bungiePost?.text.orEmpty()
        photoUrl = bungiePostMedia?.sizes?.large?.imageUrl.orEmpty()
        photoAspectRatio = bungiePostMedia?.sizes?.large?.aspectRatio ?: 1f
    }
}
