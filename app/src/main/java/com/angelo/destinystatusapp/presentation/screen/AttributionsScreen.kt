package com.angelo.destinystatusapp.presentation.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.presentation.helper.customtabs.launchCustomTabs
import com.angelo.destinystatusapp.presentation.widget.StandardTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
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
                AttributionItem.entries.forEachIndexed { index, attributionItem ->
                    val labelText = stringResource(attributionItem.stringRes, attributionItem.highlightText)
                    ListItem(
                        headlineContent = {
                            Text(
                                buildAnnotatedString {
                                    val startIndex = labelText.indexOf(attributionItem.highlightText)
                                    val endIndex = startIndex + attributionItem.highlightText.length

                                    append(labelText)
                                    addStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            fontWeight = FontWeight.Bold,
                                        ),
                                        start = startIndex,
                                        end = endIndex,
                                    )
                                },
                            )
                        },
                        modifier = Modifier.clickable {
                            context.launchCustomTabs(attributionItem.url)
                        },
                    )
                    if (index < AttributionItem.entries.size - 1) {
                        HorizontalDivider()
                    }
                }
            }
        }
    )
}

private enum class AttributionItem(@StringRes val stringRes: Int, val highlightText: String, val url: String) {
    BUNGIE_HELP_SERVICE(
        stringRes = R.string.service_attribution,
        highlightText = "bungiehelp.org",
        url = "https://www.bungiehelp.org/",
    ),
    APP_ICON(
        stringRes = R.string.app_icon_attribution,
        highlightText = "Tom Chapman's destiny-icons",
        url = "https://github.com/justrealmilk/destiny-icons",
    ),
    ANIMATION(
        stringRes = R.string.lottie_animation_attribution,
        highlightText = "Pierre Blavette / LottieFiles",
        url = "https://lottiefiles.com/pierreblavette",
    ),
    INTELLECTUAL_PROPERTY(
        stringRes = R.string.ip_attribution,
        highlightText = "Bungie, Inc.",
        url = "https://www.bungie.net/7/en/Legal/intellectualpropertytrademarks",
    ),
}
