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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.angelo.destinystatusapp.BuildConfig
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.presentation.widgets.StandardTopAppBar
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

private object Links {
    const val BUNGIE_HELP_SERVICE = "https://www.bungiehelp.org/"
    const val ASSETS_ATTRIBUTION = "https://lottiefiles.com/pierreblavette"
    const val IP_ATTRIBUTION = "https://www.bungie.net/7/en/Legal/intellectualpropertytrademarks"
}

@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gaming_community_driven))

    Scaffold(
        modifier = modifier,
        topBar = {
            StandardTopAppBar(
                title = { Text(text = stringResource(id = R.string.about_screen_title)) },
                navController = navController,
            )
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
                    headlineContent = { Text(stringResource(R.string.service_attribution)) },
                    modifier = Modifier.clickable {
                        uriHandler.openUri(Links.BUNGIE_HELP_SERVICE)
                    },
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text(stringResource(R.string.assets_attribution)) },
                    modifier = Modifier.clickable {
                        uriHandler.openUri(Links.ASSETS_ATTRIBUTION)
                    },
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text(stringResource(R.string.ip_attribution)) },
                    modifier = Modifier.clickable {
                        uriHandler.openUri(Links.IP_ATTRIBUTION)
                    },
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Version ${BuildConfig.VERSION_NAME}", style = MaterialTheme.typography.labelLarge)
            }
        }
    )
}
