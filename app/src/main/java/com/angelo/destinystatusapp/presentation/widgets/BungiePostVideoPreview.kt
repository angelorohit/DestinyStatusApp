package com.angelo.destinystatusapp.presentation.widgets

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.angelo.destinystatusapp.domain.model.BungiePostMedia

@Composable
fun BungiePostVideoPreview(bungiePostMedia: BungiePostMedia, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val exoPlayer = ExoPlayer.Builder(context).build()
    exoPlayer.playWhenReady = true

    val videoUri = bungiePostMedia.videoInfo?.getLowestQualityUrl().orEmpty()
    val mediaSource = remember(videoUri) {
        MediaItem.fromUri(videoUri)
    }

    LaunchedEffect(mediaSource) {
        exoPlayer.setMediaItem(mediaSource)
        exoPlayer.prepare()
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
                exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
                useController = false
            }
        },
        modifier = modifier.height(200.dp)
    )
}
