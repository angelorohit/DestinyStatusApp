package com.angelo.destinystatusapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.State
import com.angelo.destinystatusapp.domain.map
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepository
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import com.angelo.destinystatusapp.presentation.helper.datetime.clock.Clock
import com.angelo.destinystatusapp.presentation.viewmodel.UiString.StringResource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

typealias UiDataType = ImmutableList<BungiePost>
typealias DestinyStatusUiState = UiState<UiDataType, UiString>

class MainViewModel(
    private val destinyStatusRepository: DestinyStatusRepository,
    private val bungieHelpDaoRepository: BungieChannelPostsDaoRepository,
) : ViewModel(), KoinComponent {
    private companion object {
        // Only allow refresh to happen every one and a half minutes.
        val UPDATE_INTERVAL = 1.5.minutes
    }

    private val clock: Clock by inject()

    private val _uiState = MutableStateFlow<DestinyStatusUiState>(UiState.Zero)
    val uiState = _uiState.asStateFlow()

    private var lastUpdateTime: Duration = Duration.ZERO
    private var existingData: UiDataType = persistentListOf()

    fun fetchDestinyStatusUpdates() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading(existingData) }

            if (clock.exceedsThreshold(lastUpdateTime, UPDATE_INTERVAL)) {
                if (existingData.isEmpty()) {
                    bungieHelpDaoRepository.readBungieHelpPosts()
                        .map { it.toImmutableList() }
                        .also { state ->
                            Timber.d("Updated UI state from persistent storage.")
                            _uiState.update { state.toUiState() }
                        }
                }
                val currentExistingData = existingData

                destinyStatusRepository.fetchBungieHelpPosts()
                    .map { it.toImmutableList() }
                    .also { state -> _uiState.update { state.toUiState() } }

                if (currentExistingData != existingData) {
                    bungieHelpDaoRepository.saveBungieHelpPosts(existingData.toList())
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
                val errorUiString = when (errorType) {
                    is State.ErrorType.Remote.NoConnectivity -> StringResource(R.string.no_connectivity_error)
                    is State.ErrorType.Remote.Timeout -> StringResource(R.string.timeout_error)
                    is State.ErrorType.Remote.Request -> StringResource(R.string.request_error, errorType.message)
                    is State.ErrorType.Local.Read -> StringResource(R.string.local_persistence_read_error)
                    is State.ErrorType.Local.Write -> StringResource(R.string.local_persistence_write_error)
                    else -> StringResource(R.string.generic_request_error)
                }

                return UiState.Error(existingData, errorUiString)
            }
        }
    }
}
