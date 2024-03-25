package com.angelo.destinystatusapp.data.remote.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.angelo.destinystatusapp.data.remote.exception.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkAvailabilityInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isNetworkAvailable()) {
            throw NoConnectivityException()
        }

        return chain.proceed(chain.request())
    }

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    private fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            ContextCompat.getSystemService(this, ConnectivityManager::class.java) ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) ?: return false

        return setOf(
            NetworkCapabilities.TRANSPORT_WIFI,
            NetworkCapabilities.TRANSPORT_CELLULAR,
        ).any { networkCapabilities.hasTransport(it) }
    }
}
