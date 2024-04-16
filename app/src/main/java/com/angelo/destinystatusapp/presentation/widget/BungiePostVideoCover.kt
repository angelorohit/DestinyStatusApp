package com.angelo.destinystatusapp.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme

@Composable
fun BungiePostVideoCover(bungiePostMedia: BungiePostMedia, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.2f),
                blendMode = BlendMode.Darken,
            ),
            model = ImageRequest.Builder(LocalContext.current)
                .data(bungiePostMedia.imageUrl)
                .crossfade(true)
                .build(),
            loading = {
                ImageLoadingPlaceholder()
            },
            error = {
                ImageErrorPlaceholder()
            },
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
        )

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.8f)),
        )
    }
}

@PreviewLightDark
@Composable
private fun BungiePostVideoCoverPreview(modifier: Modifier = Modifier) {
    DestinyStatusAppTheme {
        Surface {
            BungiePostVideoCover(
                bungiePostMedia = BungiePostMedia(
                    id = "1",
                    imageUrl = "https://pbs.twimg.com/media/GLEtYt7WgAAWojF.jpg",
                    type = BungiePostMediaType.Photo,
                    sizes = null,
                    videoInfo = null,
                ),
                modifier = modifier,
            )
        }
    }
}
