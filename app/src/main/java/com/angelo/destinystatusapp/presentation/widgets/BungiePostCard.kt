package com.angelo.destinystatusapp.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.helper.datetime.TimeAgoFormattingConfig
import com.angelo.destinystatusapp.domain.helper.datetime.ago
import com.angelo.destinystatusapp.domain.helper.datetime.clock.Clock
import com.angelo.destinystatusapp.domain.helper.datetime.clock.testing.FakeClock
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import org.koin.androidx.compose.get
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun BungiePostCard(bungiePost: BungiePost, modifier: Modifier = Modifier, clock: Clock = get()) {
    ElevatedCard(
        modifier = modifier,
    ) {
        Column {
            TimeAgoText(modifier = Modifier.padding(16.dp), bungiePost = bungiePost, clock = clock)

            LinkifyText(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = bungiePost.text.orEmpty(),
                linkColor = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyLarge,
            )

            if (bungiePost.media?.isNotEmpty() == true) {
                bungiePost.media.forEach { bungiePostMedia ->
                    Spacer(modifier = Modifier.height(2.dp))
                    BungiePostPhoto(
                        bungiePostMedia = bungiePostMedia,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

@Composable
private fun TimeAgoText(bungiePost: BungiePost, clock: Clock, modifier: Modifier = Modifier) {
    val timeAgoTextValue = bungiePost.timestamp?.ago(
        LocalContext.current,
        clock,
        TimeAgoFormattingConfig(
            momentsAgoStringRes = R.string.moments_ago,
            minsAgoStringRes = R.string.minutes_ago,
            hourAgoStringRes = R.string.hour_ago,
            hoursAgoStringRes = R.string.hours_ago,
            todayStringRes = R.string.today,
            yesterdayStringRes = R.string.yesterday,
        ),
    ) ?: bungiePost.createdAt

    timeAgoTextValue?.let {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.onSecondary)
        ) {
            Text(
                text = it,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun BungiePostCardPreview(modifier: Modifier = Modifier) {
    val fakeClock = FakeClock(1711999999.milliseconds)

    DestinyStatusAppTheme {
        Surface {
            BungiePostCard(
                bungiePost = BungiePost(
                    id = "0",
                    createdAt = "March 21, 2024, 21:55:02 UTC",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = 1711058102.seconds,
                    url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                    media = listOf(
                        BungiePostMedia(
                            imageUrl = "https://pbs.twimg.com/media/GLEtYt7WgAAWojF.jpg",
                            type = BungiePostMediaType.Photo,
                            sizes = null,
                        ),
                        BungiePostMedia(
                            imageUrl = "https://pbs.twimg.com/media/GLEtYt7WgAAWojF.jpg",
                            type = BungiePostMediaType.Photo,
                            sizes = null,
                        ),
                    ),
                ),
                modifier = modifier,
                clock = fakeClock,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TimeAgoTextMinsAgoPreview(modifier: Modifier = Modifier) {
    val fakeClock = FakeClock(1711999999.milliseconds)
    DestinyStatusAppTheme {
        Surface {
            TimeAgoText(
                bungiePost = BungiePost(
                    id = "0",
                    createdAt = "March 21, 2024, 21:55:02 UTC",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = 1711058102.seconds,
                    url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                    media = emptyList(),
                ),
                clock = fakeClock,
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
private fun TimeAgoTextMomentsAgoPreview(modifier: Modifier = Modifier) {
    val fakeClock = FakeClock(1711999999.milliseconds)
    DestinyStatusAppTheme {
        Surface {
            TimeAgoText(
                bungiePost = BungiePost(
                    id = "0",
                    createdAt = "March 21, 2024, 21:55:02 UTC",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = 1711999999.seconds,
                    url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                    media = emptyList(),
                ),
                clock = fakeClock,
                modifier = modifier,
            )
        }
    }
}

@Preview
@Composable
private fun TimeAgoTextMissingTimestampPreview(modifier: Modifier = Modifier) {
    val fakeClock = FakeClock(1711999999.milliseconds)
    DestinyStatusAppTheme {
        Surface {
            TimeAgoText(
                bungiePost = BungiePost(
                    id = "0",
                    createdAt = "March 21, 2024, 21:55:02 UTC",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = null,
                    url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                    media = emptyList(),
                ),
                clock = fakeClock,
                modifier = modifier,
            )
        }
    }
}
