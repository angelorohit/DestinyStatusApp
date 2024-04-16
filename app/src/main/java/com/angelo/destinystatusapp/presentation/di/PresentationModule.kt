package com.angelo.destinystatusapp.presentation.di

import com.angelo.destinystatusapp.presentation.viewmodel.MainViewModel
import com.angelo.destinystatusapp.presentation.viewmodel.PhotoDetailsViewModel
import com.angelo.destinystatusapp.presentation.viewmodel.VideoDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { MainViewModel() }
    viewModel { parameters ->
        PhotoDetailsViewModel(channelType = parameters[0], postId = parameters[1], mediaId = parameters[2])
    }
    viewModel { parameters ->
        VideoDetailsViewModel(channelType = parameters[0], postId = parameters[1], mediaId = parameters[2])
    }
}
