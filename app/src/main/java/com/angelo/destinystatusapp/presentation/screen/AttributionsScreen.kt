package com.angelo.destinystatusapp.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.presentation.helper.customtabs.launchCustomTabs
import com.angelo.destinystatusapp.presentation.widgets.StandardTopAppBar

@Composable
fun AttributionsScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            StandardTopAppBar(
                navController = navController,
                title = { Text(text = stringResource(R.string.attributions_screen_title)) },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                ListItem(
                    headlineContent = { Text(stringResource(R.string.service_attribution)) },
                    modifier = Modifier.clickable {
                        context.launchCustomTabs(AttributionLinks.BUNGIE_HELP_SERVICE)
                    },
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text(stringResource(R.string.app_icon_attribution)) },
                    modifier = Modifier.clickable {
                        context.launchCustomTabs(AttributionLinks.APP_ICON)
                    },
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text(stringResource(R.string.lottie_animation_attribution)) },
                    modifier = Modifier.clickable {
                        context.launchCustomTabs(AttributionLinks.ANIMATION)
                    },
                )
                HorizontalDivider()
                ListItem(
                    headlineContent = { Text(stringResource(R.string.ip_attribution)) },
                    modifier = Modifier.clickable {
                        context.launchCustomTabs(AttributionLinks.INTELLECTUAL_PROPERTY)
                    },
                )
            }
        }
    )
}

private object AttributionLinks {
    const val BUNGIE_HELP_SERVICE = "https://www.bungiehelp.org/"
    const val APP_ICON = "https://github.com/justrealmilk/destiny-icons"
    const val ANIMATION = "https://lottiefiles.com/pierreblavette"
    const val INTELLECTUAL_PROPERTY = "https://www.bungie.net/7/en/Legal/intellectualpropertytrademarks"
}
