package com.angelo.destinystatusapp.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var channelFilterDropdownExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedChannelDropdownIndex by rememberSaveable { mutableIntStateOf(0) }

    fun showSnackbar(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    fun selectedChannel(): BungieChannelType = BungieChannelType.entries[selectedChannelDropdownIndex]

    fun selectedChannelName(): String = selectedChannel().toDisplayHandle()

    LaunchedEffect(true) {
        if (uiState is UiState.Zero) {
            viewModel.fetchPosts(selectedChannel())
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            StandardTopAppBar(
                title = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                },
                navController = navController,
                actions = {
                    ChannelFilterText(
                        text = selectedChannelName(),
                        onClickAction = { channelFilterDropdownExpanded = true },
                    )
                    RefreshIconButton(
                        uiState = uiState,
                        onClickAction = {
                            viewModel.fetchPosts(channelType = selectedChannel(), isForceRefresh = true)
                        },
                    )
                    SettingsIconButton(onClickAction = { navController.navigateTo(NavigationRoute.Settings) })

                    ChannelFilterDropdownMenu(
                        modifier = Modifier.fillMaxWidth(),
                        selectedIndex = selectedChannelDropdownIndex,
                        expanded = channelFilterDropdownExpanded,
                        onDismiss = { channelFilterDropdownExpanded = false },
                        onItemSelected = { index ->
                            selectedChannelDropdownIndex = index
                            channelFilterDropdownExpanded = false
                            viewModel.fetchPosts(selectedChannel())
                        },
                    )
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
private fun ChannelFilterText(text: String, onClickAction: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.onSecondary)
            .clickable { onClickAction() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
        )
    }
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
private fun ChannelFilterDropdownMenu(
    selectedIndex: Int,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismiss,
        modifier = modifier,
    ) {
        BungieChannelType.entries.forEachIndexed { index, channelType ->
            DropdownMenuItem(
                text = {
                    if (index == selectedIndex) {
                        Row {
                            Text(text = channelType.toDisplayHandle(), color = MaterialTheme.colorScheme.onSurface)
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                            )
                        }
                    } else {
                        Text(text = channelType.toDisplayHandle(), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                onClick = { onItemSelected(index) },
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
            AnimatedContent(targetState = uiState, label = "LoadingStateConent") {
                if (it.existingData.isEmpty()) {
                    LoadingContent(modifier)
                } else {
                    DataContent(it.existingData, modifier)
                }
            }
        }

        is UiState.Zero -> ZeroContent(modifier)

        is UiState.Success -> {
            AnimatedContent(targetState = uiState, label = "SuccessStateContent") {
                if (it.data.isEmpty()) {
                    EmptyContent(modifier)
                } else {
                    DataContent(it.data, modifier)
                }
            }
        }

        is UiState.Error -> {
            AnimatedContent(targetState = uiState, label = "ErrorStateContent") {
                if (it.existingData.isEmpty()) {
                    EmptyContent(modifier)
                } else {
                    DataContent(it.existingData, modifier)
                }
            }
            onErrorAction(uiState.errorData.asString())
        }
    }
}

@Composable
private fun DataContent(posts: UiDataType, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(posts) {
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
