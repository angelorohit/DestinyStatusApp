package com.angelo.destinystatusapp.presentation.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.helper.datetime.TimeAgoFormattingConfig
import com.angelo.destinystatusapp.domain.helper.datetime.ago
import com.angelo.destinystatusapp.domain.helper.datetime.clock.Clock
import com.angelo.destinystatusapp.domain.helper.datetime.clock.testing.FakeClock
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.model.BungiePostMedia
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import com.angelo.destinystatusapp.presentation.helper.customtabs.launchCustomTabs
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import org.koin.androidx.compose.get
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

@Composable
fun BungiePostCard(
    bungiePost: BungiePost,
    channelType: BungieChannelType,
    modifier: Modifier = Modifier,
    onPhotoClick: (mediaId: String) -> Unit = { },
    onVideoClick: (mediaId: String) -> Unit = { },
    clock: Clock = get(),
) {
    ElevatedCard(
        modifier = modifier,
    ) {
        Column {
            Row {
                Row(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                    TimeAgoText(bungiePost = bungiePost, clock = clock, modifier = Modifier.padding(top = 16.dp))
                    val userName = bungiePost.userName
                    if (bungiePost.isRepost == true || (!userName.isNullOrBlank() && userName != channelType.name)) {
                        Spacer(modifier = Modifier.width(16.dp))
                        RepostLabel(
                            userName = userName.orEmpty(),
                            modifier = Modifier
                                .padding(top = 16.dp),
                        )
                    }
                }

                bungiePost.url?.let {
                    Box(
                        Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 8.dp)
                    ) {
                        OriginalPostButton(originalPostUrl = it)
                    }
                }
            }

            LinkifyText(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = bungiePost.text.orEmpty(),
                linkColor = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.bodyLarge,
            )

            bungiePost.getValidMedia().forEach { bungiePostMedia ->
                Spacer(modifier = Modifier.height(2.dp))

                when (bungiePostMedia.type) {
                    BungiePostMediaType.Photo -> {
                        BungiePostPhoto(
                            bungiePostMedia = bungiePostMedia,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onPhotoClick(bungiePostMedia.id.orEmpty()) },
                        )
                    }

                    BungiePostMediaType.Video -> {
                        BungiePostVideoCover(
                            bungiePostMedia = bungiePostMedia,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onVideoClick(bungiePostMedia.id.orEmpty()) },
                        )
                    }

                    null -> Unit
                }
            }
        }
    }
}

@Composable
private fun OriginalPostButton(originalPostUrl: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    IconButton(onClick = { context.launchCustomTabs(originalPostUrl) }) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_post_24),
            contentDescription = null,
            modifier = modifier,
        )
    }
}

@Composable
fun RepostLabel(userName: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.onSecondary),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_repeat_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(start = 8.dp),
        )
        SingleLineText(
            text = "@$userName",
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.labelMedium,
        )
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
            SingleLineText(
                text = it,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun BungiePostCardPreview(modifier: Modifier = Modifier) {
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
                    userName = "BungieHelp",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = 1711058102.seconds,
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
                    userName = "BungieHelp",
                    text = "The quick brown fox jumps over the lazy dog\n\nThis is a test\n7.3.5.2\nhttps://t.co/",
                    timestamp = 1711999999.seconds,
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

