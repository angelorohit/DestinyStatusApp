package com.angelo.destinystatusapp.presentation.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.angelo.destinystatusapp.domain.model.BungiePostMedia

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
