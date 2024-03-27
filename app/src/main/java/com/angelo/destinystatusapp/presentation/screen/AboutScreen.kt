package com.angelo.destinystatusapp.presentation.screen

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.presentation.widgets.StandardTopAppBar
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

@Composable
fun AboutScreen(navController: NavController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = {
            StandardTopAppBar(
                title = { Text(text = stringResource(id = R.string.about_screen_title)) },
                navController = navController,
            )
        },
        content = { innerPadding ->
            Row(modifier = Modifier.padding(innerPadding)) {
                ListItem(
                    headlineContent = { Text(stringResource(R.string.open_source_licenses)) },
                    modifier = Modifier.clickable {
                        context.startActivity(Intent(context, OssLicensesMenuActivity::class.java))
                    },
                )
            }
        }
    )
}
