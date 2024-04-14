package com.angelo.destinystatusapp.presentation.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.angelo.destinystatusapp.BuildConfig
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.presentation.NavigationRoute
import com.angelo.destinystatusapp.presentation.navigateTo
import com.angelo.destinystatusapp.presentation.widgets.StandardTopAppBar
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gaming_community_driven))

    Scaffold(
        modifier = modifier,
        topBar = {
            StandardTopAppBar(navController = navController)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LottieAnimation(
                    composition = composition,
                    modifier = Modifier
                        .size(250.dp),
                    contentScale = ContentScale.Fit,
                )

                Card {
                    Text(
                        text = stringResource(R.string.community_driven),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ListItem(
                    headlineContent = { Text(stringResource(R.string.open_source_licenses)) },
                    modifier = Modifier.clickable {
                        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                    },
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text(stringResource(R.string.attributions)) },
                    modifier = Modifier.clickable {
                        navController.navigateTo(NavigationRoute.Attributions)
                    },
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "${BuildConfig.BUILD_TYPE} - v${BuildConfig.VERSION_NAME}",
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    )
}
