package com.angelo.destinystatusapp

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widget.RepostLabel

@Stable
@Suppress("unused")
class RepostLabelPreviewsScreenshots {
    @PreviewLightDark
    @Composable
    fun RepostLabelPreview(modifier: Modifier = Modifier) {
        DestinyStatusAppTheme {
            Surface {
                RepostLabel(
                    userName = "LoremIpsumDolorSitAmet",
                    modifier = modifier,
                )
            }
        }
    }
}
