package com.angelo.destinystatusapp.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.model.BungieHelpPost
import com.angelo.destinystatusapp.domain.usecase.FetchBungieHelpUpdatesUseCase
import com.angelo.destinystatusapp.presentation.helper.datetime.clock.Clock
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

typealias UiDataType = ImmutableList<BungieHelpPost>
typealias DestinyStatusUiState = UiState<UiDataType, String>

class MainViewModel(
    private val useCase: FetchBungieHelpUpdatesUseCase,
) : ViewModel(), KoinComponent {
    private companion object {
        // Only allow refresh to happen every minute
        val UPDATE_INTERVAL = 1.minutes
    }

    private val context: Context by inject()
    private val clock: Clock by inject()

    private val _uiState = MutableStateFlow<DestinyStatusUiState>(UiState.Zero)
    val uiState = _uiState.asStateFlow()

    private var lastUpdateTime: Duration = Duration.ZERO
    private var existingData: UiDataType = persistentListOf()

    suspend fun fetchDestinyStatusUpdates() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading(existingData) }

            if (clock.exceedsThreshold(lastUpdateTime, UPDATE_INTERVAL)) {
                useCase().also { state ->
                    _uiState.update { state.toUiState() }
                }
                lastUpdateTime = clock.now()
            } else {
                _uiState.update { UiState.Success(existingData) }
            }
        }
    }

    private fun State<UiDataType>.toUiState(): DestinyStatusUiState {
        return when (this) {
            is State.Success<UiDataType> -> {
                existingData = data
                UiState.Success(data)
            }

            is State.Error -> {
                val errorMessage = when (errorType) {
                    is State.ErrorType.Remote.NoConnectivity -> context.getString(R.string.no_connectivity_error)
                    is State.ErrorType.Remote.Timeout -> context.getString(R.string.timeout_error)
                    is State.ErrorType.Remote.Request -> context.getString(R.string.request_error, errorType.message)
                    else -> context.getString(R.string.generic_request_error)
                }

                return UiState.Error(existingData, errorMessage)
            }
        }
    }
}
