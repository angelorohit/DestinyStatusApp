package com.angelo.destinystatusapp.presentation.widget

import android.content.res.Configuration
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun ZoomableImage(photoUrl: String, photoAspectRatio: Float, modifier: Modifier = Modifier) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val aspectRatio = minOf(
        photoAspectRatio,
        calculateScreenAspectRatio(LocalConfiguration.current),
    )

    BoxWithConstraints(
        modifier = modifier.aspectRatio(aspectRatio),
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
                .data(photoUrl)
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

private fun calculateScreenAspectRatio(configuration: Configuration): Float {
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp

    return if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        screenWidthDp.toFloat() / screenHeightDp
    } else {
        screenHeightDp.toFloat() / screenWidthDp
    }
}
