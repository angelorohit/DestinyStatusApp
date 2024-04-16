package com.angelo.destinystatusapp.presentation.screen

import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.presentation.MediaDetailsArgs
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.viewmodel.PhotoDetailsViewModel
import com.angelo.destinystatusapp.presentation.widget.ImageErrorPlaceholder
import com.angelo.destinystatusapp.presentation.widget.ImageLoadingPlaceholder
import com.angelo.destinystatusapp.presentation.widget.StandardTopAppBar
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
    val interactionSource = remember { MutableInteractionSource() }

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val aspectRatio = minOf(
        viewModel.photoAspectRatio,
        calculateScreenAspectRatio(LocalConfiguration.current),
    )

    Scaffold(
        modifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = { showAppBar = !showAppBar },
        ),
        topBar = {
            // We put the image inside the topBar content, so that it can go edge to edge.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .aspectRatio(aspectRatio),
                ) {
                    val state = rememberTransformableState { zoomChange, panChange, _ ->
                        scale = (scale * zoomChange).coerceIn(1f, 2f)

                        val extraWidth = (scale - 1) * constraints.maxWidth
                        val extraHeight = (scale - 1) * constraints.maxHeight

                        val maxX = extraWidth / 2
                        val maxY = extraHeight / 2

                        offset = Offset(
                            x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                            y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
                        )
                    }
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                                translationX = offset.x
                                translationY = offset.y
                            }
                            .transformable(state = state),
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(viewModel.photoUrl)
                            .crossfade(true)
                            .build(),
                        loading = {
                            ImageLoadingPlaceholder(modifier = Modifier.fillMaxWidth().fillMaxHeight())
                        },
                        error = {
                            ImageErrorPlaceholder(modifier = Modifier.fillMaxWidth().fillMaxHeight())
                        },
                        contentDescription = null,
                    )
                }
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

private fun calculateScreenAspectRatio(configuration: Configuration): Float {
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    return if (configuration.orientation == ORIENTATION_LANDSCAPE) {
        screenWidthDp.toFloat() / screenHeightDp
    } else {
        screenHeightDp.toFloat() / screenWidthDp
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
