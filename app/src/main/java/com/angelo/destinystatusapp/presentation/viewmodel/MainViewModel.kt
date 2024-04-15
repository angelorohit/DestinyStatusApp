package com.angelo.destinystatusapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import com.angelo.destinystatusapp.domain.usecase.FetchPostsUseCase
import com.angelo.destinystatusapp.presentation.viewmodel.UiString.StringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

typealias FeedUiDataType = ImmutableList<BungiePost>
typealias FeedUiState = UiState<FeedUiDataType, UiString>

class MainViewModel : ViewModel(), KoinComponent {
    private val fetchPostsUseCase: FetchPostsUseCase by inject()
    private val cacheRepository: BungieChannelPostsCacheRepository by inject()

    private val _uiState = MutableStateFlow<FeedUiState>(UiState.Zero)
    val uiState = _uiState.asStateFlow()

    private var job: Job? = null
    private var selectedChannelType: BungieChannelType? = null

    fun fetchPosts(channelType: BungieChannelType, isForceRefresh: Boolean = false) {
        if (selectedChannelType != channelType) {
            selectedChannelType = channelType
            job?.cancel()
        }

        if (job?.isActive == true) {
            return
        }

        job = viewModelScope.launch {
            _uiState.update { UiState.Loading(cacheRepository.getPosts(channelType)) }

            fetchPostsUseCase(channelType, isForceRefresh).collect { state ->
                _uiState.update { state.toUiState(channelType) }
            }
        }
    }

    private fun State<FeedUiDataType>.toUiState(channelType: BungieChannelType): FeedUiState {
        return when (this) {
            is State.Success<FeedUiDataType> -> {
                UiState.Success(data)
            }

            is State.Error -> {
                val errorUiString = when (errorType) {
                    is State.ErrorType.Remote.NoConnectivity -> StringResource(R.string.no_connectivity_error)
                    is State.ErrorType.Remote.Timeout -> StringResource(R.string.timeout_error)
                    is State.ErrorType.Remote.Request -> StringResource(R.string.request_error, errorType.message)
                    is State.ErrorType.Local.Read -> StringResource(R.string.local_persistence_read_error)
                    is State.ErrorType.Local.Write -> StringResource(R.string.local_persistence_write_error)
                    else -> StringResource(R.string.generic_request_error)
                }

                return UiState.Error(cacheRepository.getPosts(channelType), errorUiString)
            }
        }
    }
}
