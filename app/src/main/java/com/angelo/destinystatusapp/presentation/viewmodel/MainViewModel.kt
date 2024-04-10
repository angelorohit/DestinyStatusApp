package com.angelo.destinystatusapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import com.angelo.destinystatusapp.domain.usecase.FetchBungieHelpPostsUseCase
import com.angelo.destinystatusapp.presentation.viewmodel.UiString.StringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

typealias UiDataType = ImmutableList<BungiePost>
typealias DestinyStatusUiState = UiState<UiDataType, UiString>

class MainViewModel : ViewModel(), KoinComponent {
    private val fetchBungieHelpPostsUseCase: FetchBungieHelpPostsUseCase by inject()
    private val bungieChannelPostsCacheRepository: BungieChannelPostsCacheRepository by inject()

    private val _uiState = MutableStateFlow<DestinyStatusUiState>(UiState.Zero)
    val uiState = _uiState.asStateFlow()

    fun fetchBungieHelpPosts() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading(bungieChannelPostsCacheRepository.readBungieHelpPosts()) }

            fetchBungieHelpPostsUseCase()
                .collect { state ->
                    _uiState.update { state.toUiState() }
                }
        }
    }

    private fun State<UiDataType>.toUiState(): DestinyStatusUiState {
        return when (this) {
            is State.Success<UiDataType> -> {
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

                return UiState.Error(bungieChannelPostsCacheRepository.readBungieHelpPosts(), errorUiString)
            }
        }
    }
}
