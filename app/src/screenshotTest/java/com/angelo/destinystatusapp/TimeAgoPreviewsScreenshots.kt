package com.angelo.destinystatusapp

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.angelo.destinystatusapp.domain.helper.datetime.clock.testing.FakeClock
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widget.TimeAgoText
import kotlin.time.Duration.Companion.seconds

@Stable
@Suppress("unused")
class TimeAgoPreviewsScreenshots {

    @PreviewLightDark
    @Composable
    fun TimeAgoTextMomentsAgoPreview(modifier: Modifier = Modifier) {
        val fakeClock = FakeClock(1730455131.seconds)
        DestinyStatusAppTheme {
            Surface {
                TimeAgoText(
                    bungiePost = BungiePost(
                        id = "0",
                        createdAt = "March 21, 2024, 21:55:02 UTC",
                        userName = "BungieHelp",
                        text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                        timestamp = 1730455131.seconds,
                        url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                        media = emptyList(),
                        isRepost = false,
                    ),
                    clock = fakeClock,
                    modifier = modifier,
                )
            }
        }
    }

    @PreviewLightDark
    @Composable
    fun TimeAgoTextMinutesAgoPreview(modifier: Modifier = Modifier) {
        val fakeClock = FakeClock((1730455131 + 120).seconds)
        DestinyStatusAppTheme {
            Surface {
                TimeAgoText(
                    bungiePost = BungiePost(
                        id = "0",
                        createdAt = "March 21, 2024, 21:55:02 UTC",
                        userName = "BungieHelp",
                        text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                        timestamp = 1730455131.seconds,
                        url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                        media = emptyList(),
                        isRepost = false,
                    ),
                    clock = fakeClock,
                    modifier = modifier,
                )
            }
        }
    }

    @PreviewLightDark
    @Composable
    fun TimeAgoTextMissingTimestampPreview(modifier: Modifier = Modifier) {
        val fakeClock = FakeClock(1730455131.seconds)
        DestinyStatusAppTheme {
            Surface {
                TimeAgoText(
                    bungiePost = BungiePost(
                        id = "0",
                        createdAt = "March 21, 2024, 21:55:02 UTC",
                        userName = "BungieHelp",
                        text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                        timestamp = null,
                        url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                        media = emptyList(),
                        isRepost = false,
                    ),
                    clock = fakeClock,
                    modifier = modifier,
                )
            }
        }
    }
}
