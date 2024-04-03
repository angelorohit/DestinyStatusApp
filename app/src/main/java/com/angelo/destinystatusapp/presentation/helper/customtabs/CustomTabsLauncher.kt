package com.angelo.destinystatusapp.presentation.helper.customtabs

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

fun Context.launchCustomTabs(url: String) {
    val customTabsIntent = CustomTabsIntent.Builder()
        .setShareState(CustomTabsIntent.SHARE_STATE_ON)
        .setShowTitle(true)
        .build()

    customTabsIntent.intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse("android-app://$packageName"))
    customTabsIntent.launchUrl(this, Uri.parse(url))
}
