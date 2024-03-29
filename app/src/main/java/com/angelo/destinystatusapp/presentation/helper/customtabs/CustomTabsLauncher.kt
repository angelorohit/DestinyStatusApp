package com.angelo.destinystatusapp.presentation.helper.customtabs

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.launchCustomTabs(url: String) {
    CustomTabsIntent.Builder()
        .setShareState(CustomTabsIntent.SHARE_STATE_ON)
        .setShowTitle(true)
        .build().launchUrl(this, Uri.parse(url))
}
