package com.angelo.destinystatusapp.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.presentation.widgets.StandardTopAppBar

@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            StandardTopAppBar(
                title = { Text(text = stringResource(id = R.string.about_screen_title)) },
                navController = navController,
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(64.dp)
                        .align(Alignment.Center),
                )
            }
        }
    )
}
