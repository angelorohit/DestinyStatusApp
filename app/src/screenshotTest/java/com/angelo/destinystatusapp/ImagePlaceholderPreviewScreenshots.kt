package com.angelo.destinystatusapp

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widget.ImageErrorPlaceholder
import com.angelo.destinystatusapp.presentation.widget.ImageLoadingPlaceholder

@Stable
@Suppress("unused")
class ImagePlaceholderPreviewScreenshots {

    @PreviewLightDark
    @Composable
    private fun ImageLoadingPlaceholderPreview(modifier: Modifier = Modifier) {
        DestinyStatusAppTheme {
            Surface {
                ImageLoadingPlaceholder(modifier = modifier)
            }
        }
    }

    @PreviewLightDark
    @Composable
    private fun ImageErrorPlaceholderPreview(modifier: Modifier = Modifier) {
        DestinyStatusAppTheme {
            Surface {
                ImageErrorPlaceholder(modifier = modifier)
            }
        }
    }
}
