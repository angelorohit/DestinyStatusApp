package com.angelo.destinystatusapp.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.data.remote.model.DestinyStatusUpdate
import com.angelo.destinystatusapp.presentation.helper.datetime.TimeAgoFormattingConfig
import com.angelo.destinystatusapp.presentation.helper.datetime.ago
import com.angelo.destinystatusapp.presentation.helper.datetime.clock.Clock
import com.angelo.destinystatusapp.presentation.helper.datetime.clock.testing.FakeClock
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import org.koin.androidx.compose.get
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun DestinyStatusUpdateCard(
    destinyStatusUpdate: DestinyStatusUpdate,
    modifier: Modifier = Modifier,
    clock: Clock = get(),
) {
    Card(
        modifier = modifier.padding(top = 8.dp, bottom = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            TimeAgoText(destinyStatusUpdate = destinyStatusUpdate, clock = clock)

            Spacer(modifier = Modifier.height(16.dp))

            LinkifyText(
                text = destinyStatusUpdate.text.orEmpty(),
                linkColor = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Composable
private fun TimeAgoText(destinyStatusUpdate: DestinyStatusUpdate, clock: Clock, modifier: Modifier = Modifier) {
    val timeAgoTextValue = destinyStatusUpdate.timestamp?.seconds?.ago(
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
    ) ?: destinyStatusUpdate.createdAt

    timeAgoTextValue?.let {
        Box(modifier = modifier.clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.onSecondary)) {
            Text(
                text = it,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun DestinyStatusUpdateCardPreview(modifier: Modifier = Modifier) {
    val fakeClock = FakeClock(1711999999.milliseconds)

    DestinyStatusAppTheme {
        Surface {
            DestinyStatusUpdateCard(
                destinyStatusUpdate = DestinyStatusUpdate(
                    id = "0",
                    createdAt = "March 21, 2024, 21:55:02 UTC",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = 1711058102,
                    url = "https://twitter.com/BungieHelp/status/1770932153981050919",
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
                destinyStatusUpdate = DestinyStatusUpdate(
                    id = "0",
                    createdAt = "March 21, 2024, 21:55:02 UTC",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = 1711058102,
                    url = "https://twitter.com/BungieHelp/status/1770932153981050919",
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
                destinyStatusUpdate = DestinyStatusUpdate(
                    id = "0",
                    createdAt = "March 21, 2024, 21:55:02 UTC",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = 1711999999,
                    url = "https://twitter.com/BungieHelp/status/1770932153981050919",
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
                destinyStatusUpdate = DestinyStatusUpdate(
                    id = "0",
                    createdAt = "March 21, 2024, 21:55:02 UTC",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    url = "https://twitter.com/BungieHelp/status/1770932153981050919",
                ),
                clock = fakeClock,
                modifier = modifier,
            )
        }
    }
}
