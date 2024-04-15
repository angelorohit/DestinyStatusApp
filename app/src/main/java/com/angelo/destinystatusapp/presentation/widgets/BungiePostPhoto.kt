package com.angelo.destinystatusapp.presentation.widgets

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme

@Composable
fun BungiePostPhoto(bungiePostMedia: BungiePostMedia, modifier: Modifier = Modifier) {
    SubcomposeAsyncImage(
        modifier = modifier,
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
}

@PreviewLightDark
@Composable
private fun BungiePostPhotoPreview(modifier: Modifier = Modifier) {
    DestinyStatusAppTheme {
        Surface {
            BungiePostPhoto(
                bungiePostMedia = BungiePostMedia(
                    id = "1",
                    imageUrl = "https://pbs.twimg.com/media/GLEtYt7WgAAWojF.jpg",
                    type = BungiePostMediaType.Photo,
                    sizes = null,
                ),
                modifier = modifier,
            )
        }
    }
}
