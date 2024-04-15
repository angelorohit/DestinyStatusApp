package com.angelo.destinystatusapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widgets.StandardTopAppBar

@Composable
fun PhotoDetailsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val photoUrl = navBackStackEntry?.arguments?.getString("photoUrl").orEmpty()

    Scaffold(
        modifier = modifier,
        topBar = {
            StandardTopAppBar(navController = navController)
            // We put the image inside the topBar content, so that it can go edge to edge.
            // We could have also put the image outside the topBar, but then the topBar title would not be visible
            // when the image occupies the entire screen.
            // Currently, the topBar does not have a title, but we might want to add one in future.
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photoUrl)
                        .crossfade(true)
                        .build(),
                    loading = {
                        Box {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(48.dp)
                            )
                        }
                    },
                    error = {
                        Box {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(48.dp)
                            )
                        }
                    },
                    contentDescription = null,
                )
            }
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
            PhotoDetailsScreen(navController = rememberNavController())
        }
    }
}
