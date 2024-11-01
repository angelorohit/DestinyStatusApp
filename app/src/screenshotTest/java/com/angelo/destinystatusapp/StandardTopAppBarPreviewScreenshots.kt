package com.angelo.destinystatusapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.compose.rememberNavController
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme
import com.angelo.destinystatusapp.presentation.widget.StandardTopAppBar

@Stable
@Suppress("unused")
class StandardTopAppBarPreviewScreenshots {

    @OptIn(ExperimentalMaterial3Api::class)
    @PreviewLightDark
    @Composable
    private fun StandardTopAppBarPreview() {
        DestinyStatusAppTheme {
            Surface {
                StandardTopAppBar(
                    navController = rememberNavController(),
                    title = { Text("Hello world!") },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Back",
                            )
                        }
                    },
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @PreviewLightDark
    @Composable
    private fun StandardTopAppBarWithCustomColorPreview() {
        DestinyStatusAppTheme {
            Surface {
                StandardTopAppBar(
                    navController = rememberNavController(),
                    title = { Text("Hello world!") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Back",
                            )
                        }
                    },
                )
            }
        }
    }
}