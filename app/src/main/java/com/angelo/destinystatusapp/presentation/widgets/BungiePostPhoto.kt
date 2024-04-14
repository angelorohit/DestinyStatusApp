package com.angelo.destinystatusapp.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun BungiePostPhoto(bungiePostMedia: BungiePostMedia, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        modifier = modifier
            .background(MaterialTheme.colorScheme.onSurfaceVariant),
        model = ImageRequest.Builder(LocalContext.current)
            .data(bungiePostMedia.imageUrl)
            .crossfade(true)
            .build(),
        loading = {
            LoadingErrorPlaceholder()
        },
        error = {
            LoadingErrorPlaceholder()
        },
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
    )
}

@Composable
fun LoadingErrorPlaceholder(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(100.dp)
            .background(MaterialTheme.colorScheme.onSurfaceVariant),
    )
}

@PreviewLightDark
@Composable
fun BungiePostPhotoPreview(modifier: Modifier = Modifier) {
    DestinyStatusAppTheme {
        Surface {
            BungiePostPhoto(
                bungiePostMedia = BungiePostMedia(
                    imageUrl = "https://pbs.twimg.com/media/GLEtYt7WgAAWojF.jpg",
                    type = BungiePostMediaType.Photo,
                    sizes = null,
                ),
                modifier = modifier,
            )
        }
    }
}

@PreviewLightDark
@Composable
fun LoadingErrorPlaceholderPreview(modifier: Modifier = Modifier) {
    DestinyStatusAppTheme {
        Surface {
            LoadingErrorPlaceholder(modifier = modifier)
        }
    }
}
