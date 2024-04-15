package com.angelo.destinystatusapp.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.angelo.destinystatusapp.presentation.PhotoDetailsArgs
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widgets.ImageErrorPlaceholder
import com.angelo.destinystatusapp.presentation.widgets.ImageLoadingPlaceholder
import com.angelo.destinystatusapp.presentation.widgets.StandardTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailsScreen(navController: NavController, args: PhotoDetailsArgs, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            // We put the image inside the topBar content, so that it can go edge to edge.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(args.photoUrl)
                        .crossfade(true)
                        .build(),
                    loading = {
                        ImageLoadingPlaceholder()
                    },
                    error = {
                        ImageErrorPlaceholder()
                    },
                    contentDescription = null,
                )
            }

            StandardTopAppBar(
                navController = navController,
                title = { Text(text = args.title, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                ),
            )
        },
    ) {
        Spacer(modifier = Modifier.padding(it))
    }
}

@PreviewLightDark
@Composable
private fun PhotoDetailsScreenPreview() {
    DestinyStatusAppTheme {
        Surface {
            PhotoDetailsScreen(
                navController = rememberNavController(),
                args = PhotoDetailsArgs(
                    title = "Photo Title",
                    photoUrl = "https://pbs.twimg.com/media/GLEtYt7WgAAWojF.jpg",
                ),
            )
        }
    }
}
