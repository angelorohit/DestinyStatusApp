package com.angelo.destinystatusapp.presentation.screen

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.angelo.destinystatusapp.domain.model.BungieChannelType
import com.angelo.destinystatusapp.presentation.MediaDetailsArgs
import com.angelo.destinystatusapp.presentation.viewmodel.VideoDetailsViewModel
import com.angelo.destinystatusapp.presentation.widget.StandardTopAppBar
import com.angelo.destinystatusapp.presentation.widget.VideoPlayer
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideoDetailsScreen(
    navController: NavController,
    args: MediaDetailsArgs,
    modifier: Modifier = Modifier,
    viewModel: VideoDetailsViewModel = getViewModel {
        parametersOf(
            BungieChannelType.fromName(args.channelTypeName),
            args.postId,
            args.mediaId,
        )
    },
) {
    val configuration = LocalConfiguration.current

    Scaffold(
        modifier = modifier,
        topBar = {
            VideoPlayer(
                videoUri = viewModel.bungiePostMedia?.videoInfo?.getHighestQualityUrl().orEmpty(),
                aspectRatio = viewModel.bungiePostMedia?.videoInfo?.aspectRatio ?: 1f,
                modifier = Modifier.fillMaxSize(),
            )
            AnimatedContent(
                configuration.orientation == ORIENTATION_PORTRAIT,
                label = "TopAppBar",
            ) { isPortrait ->
                if (isPortrait) {
                    StandardTopAppBar(
                        navController = navController,
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        ),
                    )
                }
            }
        },
        content = {
            Spacer(modifier = Modifier.padding(it))
        },
    )
}
