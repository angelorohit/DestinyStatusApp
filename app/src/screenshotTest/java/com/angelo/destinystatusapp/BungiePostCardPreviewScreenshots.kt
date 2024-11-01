package com.angelo.destinystatusapp

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.angelo.destinystatusapp.domain.helper.datetime.clock.testing.FakeClock
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widget.BungiePostCard
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Stable
@Suppress("unused")
class BungiePostCardPreviewScreenshots {
    @PreviewLightDark
    @PreviewScreenSizes
    @Composable
    fun BungiePostCardPreview(modifier: Modifier = Modifier) {
        val fakeClock = FakeClock(1711999999.milliseconds)

        DestinyStatusAppTheme {
            Surface {
                BungiePostCard(
                    bungiePost = BungiePost(
                        id = "0",
                        createdAt = "March 21, 2024, 21:55:02 UTC",
                        userName = "LoremIpsumDolorSitAmet",
                        text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                        timestamp = 1711058102.seconds,
                        url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                        media = listOf(
                            BungiePostMedia(
                                id = "",
                                imageUrl = "https://pbs.twimg.com/media/GLEtYt7WgAAWojF.jpg",
                                type = BungiePostMediaType.Photo,
                                sizes = null,
                                videoInfo = null,
                            ),
                            BungiePostMedia(
                                id = "",
                                imageUrl = "https://pbs.twimg.com/media/GLEtYt7WgAAWojF.jpg",
                                type = BungiePostMediaType.Photo,
                                sizes = null,
                                videoInfo = null,
                            ),
                        ),
                        isRepost = true,
                    ),
                    channelType = BungieChannelType.BungieHelp,
                    modifier = modifier,
                    clock = fakeClock,
                )
            }
        }
    }
}
