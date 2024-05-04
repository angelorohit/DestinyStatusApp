package com.angelo.destinystatusapp.presentation.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.angelo.destinystatusapp.presentation.helper.customtabs.launchCustomTabs
import com.angelo.destinystatusapp.presentation.launchAttributionsScreen
import com.angelo.destinystatusapp.presentation.widget.StandardTopAppBar
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.gaming_community_driven))

    Scaffold(
        modifier = modifier,
        topBar = {
            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit,
            )
            StandardTopAppBar(
                navController = navController,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                ),
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Card(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.community_driven),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ListItem(
                    headlineContent = { Text(stringResource(R.string.open_source)) },
                    modifier = Modifier.clickable {
                        context.launchCustomTabs("https://github.com/angelorohit/DestinyStatusApp")
                    },
                )
                HorizontalDivider()
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
                        navController.launchAttributionsScreen()
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
