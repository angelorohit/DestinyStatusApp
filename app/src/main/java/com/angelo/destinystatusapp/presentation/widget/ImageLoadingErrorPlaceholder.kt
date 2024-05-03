package com.angelo.destinystatusapp.presentation.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.presentation.theme.DestinyStatusAppTheme

@Composable
fun ImageLoadingPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(48.dp)
                .padding(8.dp),
        )
    }
}

@Composable
fun ImageErrorPlaceholder(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Outlined.Warning,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            SingleLineText(
                text = stringResource(id = R.string.image_load_error),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ImageLoadingPlaceholderPreview(modifier: Modifier = Modifier) {
    DestinyStatusAppTheme {
        Surface {
            ImageLoadingPlaceholder(modifier = modifier)
        }
    }
}

@PreviewLightDark
@Composable
private fun ImageErrorPlaceholderPreview(modifier: Modifier = Modifier) {
    DestinyStatusAppTheme {
        Surface {
            ImageErrorPlaceholder(modifier = modifier)
        }
    }
}
