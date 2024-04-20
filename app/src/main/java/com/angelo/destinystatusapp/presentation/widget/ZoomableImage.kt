package com.angelo.destinystatusapp.presentation.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

private const val ZOOM_ANIMATION_MILLIS = 300

@Composable
fun ZoomableImage(
    photoUrl: String,
    photoAspectRatio: Float,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    maxZoomMultiplier: Float = 4f,
    onTap: () -> Unit = {},
    onZoomChange: (Float, Float) -> Unit = { _, _ -> },
    forceZoomOut: Boolean = false,
) {
    val scope = rememberCoroutineScope()
    val offsetAnimation = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    val offset by remember(offsetAnimation.value) { derivedStateOf { offsetAnimation.value } }
    val scaleAnimation = remember { Animatable(1f) }
    val scale by remember(scaleAnimation.value) { derivedStateOf { scaleAnimation.value } }

    LaunchedEffect(forceZoomOut) {
        if (forceZoomOut) {
            scope.launch { scaleAnimation.animateTo(1f, tween(ZOOM_ANIMATION_MILLIS)) }
            scope.launch { offsetAnimation.animateTo(Offset.Zero, tween(ZOOM_ANIMATION_MILLIS)) }
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .aspectRatio(photoAspectRatio)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onTap() },
                    onDoubleTap = { pos ->
                        val oldScale = scale
                        val newScale = if (scale == 1f) {
                            maxZoomMultiplier
                        } else {
                            1f
                        }

                        val newOffset = if (newScale == 1f) {
                            Offset(0f, 0f)
                        } else {
                            val halfWidth = size.width / 2
                            val halfHeight = size.height / 2

                            // Calculate new offset of the clicked point after scaling.
                            val newClickOffsetXFromCenter = (pos.x * newScale) - halfWidth
                            val newClickOffsetYFromCenter = (pos.y * newScale) - halfHeight

                            // Calculate the difference between original and new offsets.
                            val offsetDiffX = newClickOffsetXFromCenter - (pos.x - halfWidth)
                            val offsetDiffY = newClickOffsetYFromCenter - (pos.y - halfHeight)

                            // Translate the image by the calculated difference.
                            Offset(
                                x = offset.x - offsetDiffX + size.width + halfWidth,
                                y = offset.y - offsetDiffY + size.height + halfHeight,
                            )
                        }

                        scope.launch { scaleAnimation.animateTo(newScale, tween(ZOOM_ANIMATION_MILLIS)) }
                        scope.launch { offsetAnimation.animateTo(newOffset, tween(ZOOM_ANIMATION_MILLIS)) }

                        onZoomChange(newScale, oldScale)
                    },
                )
            },
    ) {
        val state = rememberTransformableState { zoomChange, panChange, _ ->
            val oldScale = scale
            val newScale = (scale * zoomChange).coerceIn(1f, maxZoomMultiplier)
            val extraWidth = (newScale - 1) * constraints.maxWidth
            val extraHeight = (newScale - 1) * constraints.maxHeight

            val maxX = extraWidth / 2
            val maxY = extraHeight / 2

            val newOffset = Offset(
                x = (offset.x + panChange.x * newScale).coerceIn(-maxX, maxX),
                y = (offset.y + panChange.y * newScale).coerceIn(-maxY, maxY),
            )

            scope.launch {
                scaleAnimation.snapTo(newScale)
                offsetAnimation.snapTo(newOffset)
            }

            onZoomChange(newScale, oldScale)
        }
        SubcomposeAsyncImage(
            contentDescription = contentDescription,
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
                    modifier = Modifier.fillMaxSize(),
                )
            },
            error = {
                ImageErrorPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}
