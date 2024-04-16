package com.angelo.destinystatusapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class VideoDetailsViewModel(
    channelType: BungieChannelType,
    postId: String,
    mediaId: String,
) : ViewModel(), KoinComponent {
    private val cacheRepository: BungieChannelPostsCacheRepository by inject()

    var bungiePostMedia: BungiePostMedia? private set

    init {
        val bungiePost = cacheRepository.getPosts(channelType).find { bungiePost -> bungiePost.id == postId }
        bungiePostMedia = bungiePost?.media?.find { bungiePostMedia -> bungiePostMedia.id == mediaId }
    }
}
