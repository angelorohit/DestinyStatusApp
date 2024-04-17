package com.angelo.destinystatusapp.presentation.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.presentation.MediaDetailsArgs
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.viewmodel.PhotoDetailsViewModel
import com.angelo.destinystatusapp.presentation.widget.StandardTopAppBar
import com.angelo.destinystatusapp.presentation.widget.ZoomableImage
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailsScreen(
    navController: NavController,
    args: MediaDetailsArgs,
    modifier: Modifier = Modifier,
    viewModel: PhotoDetailsViewModel = getViewModel {
        parametersOf(
            BungieChannelType.fromName(args.channelTypeName),
            args.postId,
            args.mediaId,
        )
    },
) {
    var showAppBar by remember { mutableStateOf(true) }

    Scaffold(
        modifier = modifier,
        topBar = {
            // We put the image inside the topBar content, so that it can go edge to edge.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { showAppBar = !showAppBar },
                    ),
            ) {
                ZoomableImage(
                    photoAspectRatio = viewModel.photoAspectRatio,
                    photoUrl = viewModel.photoUrl,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                )
            }

            AnimatedContent(showAppBar, label = "TopAppBar") {
                if (it) {
                    StandardTopAppBar(
                        navController = navController,
                        title = {
                            Text(text = viewModel.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        ),
                    )
                }
            }
        },
    ) {
        Spacer(modifier = Modifier.padding(it))
    }
}

@PreviewLightDark
@Composable
private fun PhotoDetailsScreenPreview() {
    DestinyStatusAppTheme {
        Surface {
            PhotoDetailsScreen(
                navController = rememberNavController(),
                args = MediaDetailsArgs(),
            )
        }
    }
}
