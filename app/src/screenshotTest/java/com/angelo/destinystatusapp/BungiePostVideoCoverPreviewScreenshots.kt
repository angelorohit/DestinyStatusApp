package com.angelo.destinystatusapp

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widget.BungiePostVideoCover

@Stable
@Suppress("unused")
class BungiePostVideoCoverPreviewScreenshots {

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
}