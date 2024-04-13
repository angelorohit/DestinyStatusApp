package com.angelo.destinystatusapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.presentation.NavigationRoute
import com.angelo.destinystatusapp.presentation.navigateTo
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.viewmodel.DestinyStatusUiState
import com.angelo.destinystatusapp.presentation.viewmodel.MainViewModel
import com.angelo.destinystatusapp.presentation.viewmodel.UiDataType
import com.angelo.destinystatusapp.presentation.viewmodel.UiState
import com.angelo.destinystatusapp.presentation.widgets.BungiePostCard
import com.angelo.destinystatusapp.presentation.widgets.StandardTopAppBar
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun MainScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = getViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    fun showSnackbar(message: String) {
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(true) {
        viewModel.fetchPosts(BungieChannelType.BungieHelp)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            StandardTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.main_screen_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navController = navController,
                actions = {
                    RefreshIconButton(
                        uiState = uiState,
                        onClickAction = {
                            viewModel.fetchPosts(BungieChannelType.BungieHelp)
                        },
                    )
                    SettingsIconButton(onClickAction = { navController.navigateTo(NavigationRoute.Settings) })
                }
            )
        },
        content = { innerPadding ->
            MainContent(
                uiState = uiState,
                modifier = Modifier.padding(innerPadding),
                onErrorAction = ::showSnackbar,
            )
        }
    )
}

@Composable
private fun SettingsIconButton(onClickAction: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = onClickAction,
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = stringResource(R.string.settings_icon_content_description),
        )
    }
}

@Composable
private fun RefreshIconButton(
    uiState: DestinyStatusUiState,
    onClickAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        modifier = modifier,
        onClick = onClickAction,
        enabled = uiState !is UiState.Loading,
    ) {
        if (uiState is UiState.Loading && uiState.existingData.isNotEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.padding(12.dp),
                strokeWidth = 1.8.dp,
            )
        } else {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(R.string.refresh_icon_content_description),
            )
        }
    }
}

@Composable
private fun MainContent(
    uiState: DestinyStatusUiState,
    modifier: Modifier = Modifier,
    onErrorAction: (String) -> Unit = {},
) {
    when (uiState) {
        is UiState.Loading -> {
            if (uiState.existingData.isEmpty()) {
                LoadingContent(modifier)
            }
        }

        is UiState.Zero -> ZeroContent(modifier)

        is UiState.Success -> {
            if (uiState.data.isEmpty()) {
                EmptyContent(modifier)
            } else {
                DataContent(uiState.data, modifier)
            }
        }

        is UiState.Error -> {
            if (uiState.existingData.isEmpty()) {
                EmptyContent(modifier)
            } else {
                DataContent(uiState.existingData, modifier)
            }
            onErrorAction(uiState.errorData.asString())
        }
    }
}

@Composable
private fun DataContent(destinyStatusUpdates: UiDataType, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(destinyStatusUpdates) {
            BungiePostCard(
                bungiePost = it,
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
            )
        }
        // Empty space below the last item in the list.
        item { Spacer(modifier = Modifier.height(8.dp)) }
    }
}

@Composable
private fun EmptyContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.empty_content),
        )
    }
}

@Composable
private fun ZeroContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.zero_content),
        )
    }
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.Center),
        )
    }
}

@PreviewLightDark
@Composable
private fun EmptyContentPreview() {
    DestinyStatusAppTheme {
        Surface {
            EmptyContent()
        }
    }
}

@PreviewLightDark
@Composable
private fun ZeroContentPreview() {
    DestinyStatusAppTheme {
        Surface {
            ZeroContent()
        }
    }
}

@PreviewLightDark
@Composable
private fun LoadingContentPreview() {
    DestinyStatusAppTheme {
        Surface {
            LoadingContent()
        }
    }
}
