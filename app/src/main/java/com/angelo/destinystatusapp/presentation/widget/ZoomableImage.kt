package com.angelo.destinystatusapp.presentation.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

private const val ZOOM_ANIMATION_MILLIS = 300

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZoomableImage(
    photoUrl: String,
    photoAspectRatio: Float,
    modifier: Modifier = Modifier,
    maxZoomMultiplier: Float = 3f,
    onTap: () -> Unit = {},
    onDoubleTapZoomChange: (Float, Float) -> Unit = { _, _ -> },
    onPinchZoomChange: (Float, Float) -> Unit = { _, _ -> },
) {
    val scope = rememberCoroutineScope()
    var offset by remember { mutableStateOf(Offset.Zero) }
    val scaleAnimation = remember { Animatable(1f) }
    val scale by remember(scaleAnimation.value) { derivedStateOf { scaleAnimation.value } }

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(photoAspectRatio)
            .combinedClickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onTap() },
                onDoubleClick = {
                    val oldScale = scale
                    val newScale = if (scale == 1f) maxZoomMultiplier else 1f

                    scope.launch {
                        scaleAnimation.animateTo(newScale, tween(ZOOM_ANIMATION_MILLIS))
                    }
                    onDoubleTapZoomChange(newScale, oldScale)
                }
            ),
    ) {
        val state = rememberTransformableState { zoomChange, panChange, _ ->
            val oldScale = scale
            val newScale = (scale * zoomChange).coerceIn(1f, maxZoomMultiplier)

            scope.launch {
                scaleAnimation.snapTo(newScale)
            }
            onPinchZoomChange(newScale, oldScale)

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
                ImageLoadingPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                )
            },
            error = {
                ImageErrorPlaceholder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                )
            },
            contentDescription = null,
        )
    }
}
