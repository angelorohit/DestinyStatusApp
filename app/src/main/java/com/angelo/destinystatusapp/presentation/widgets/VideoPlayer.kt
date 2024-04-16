package com.angelo.destinystatusapp.presentation.widgets

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    videoUri: String,
    aspectRatio: Float,
    modifier: Modifier = Modifier,
    showPlayerControls: Boolean = true,
    loopPlayback: Boolean = true,
) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(videoUri))
        if (loopPlayback) {
            repeatMode = Player.REPEAT_MODE_ONE
        }
        playWhenReady = true
        prepare()
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = showPlayerControls
            }
        },
        modifier = modifier.aspectRatio(aspectRatio),
    )
}
