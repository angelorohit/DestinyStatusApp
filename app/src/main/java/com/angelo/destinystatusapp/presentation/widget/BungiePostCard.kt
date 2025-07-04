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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.helper.datetime.TimeAgoFormattingConfig
import com.angelo.destinystatusapp.domain.helper.datetime.ago
import com.angelo.destinystatusapp.domain.helper.datetime.clock.Clock
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.domain.model.BungiePost
import com.angelo.destinystatusapp.domain.model.BungiePostMediaType
import com.angelo.destinystatusapp.presentation.helper.customtabs.launchCustomTabs
import org.koin.androidx.compose.get

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
                linkColor = MaterialTheme.colorScheme.onSurface,
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

private val timeAgoFormattingConfig = TimeAgoFormattingConfig(
    momentsAgoStringRes = R.string.moments_ago,
    minsAgoStringRes = R.string.minutes_ago,
    hourAgoStringRes = R.string.hour_ago,
    hoursAgoStringRes = R.string.hours_ago,
    todayStringRes = R.string.today,
    yesterdayStringRes = R.string.yesterday,
)

@Composable
fun TimeAgoText(bungiePost: BungiePost, clock: Clock, modifier: Modifier = Modifier) {
    val timeAgoTextValue = bungiePost.timestamp?.ago(
        LocalContext.current,
        clock,
        timeAgoFormattingConfig,
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
