package com.angelo.destinystatusapp.data.remote.api

import android.content.Context
import com.angelo.destinystatusapp.BuildConfig
import com.angelo.destinystatusapp.data.remote.interceptor.NetworkAvailabilityInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class ServiceClient private constructor() {
    companion object {
        private const val DESTINY_SERVICE_BASE_URL = "https://bungiehelp.org"

        fun createDestinyStatusService(context: Context): DestinyStatusService {
            val json = Json {
                ignoreUnknownKeys = true
            }
            val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(NetworkAvailabilityInterceptor(context))
            if (BuildConfig.DEBUG) {
                clientBuilder.addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    },
                )
            }

            return Retrofit.Builder()
                .baseUrl(DESTINY_SERVICE_BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(
                    json.asConverterFactory(
                        "application/json; charset=UTF8".toMediaType(),
                    ),
                )
                .build()
                .create(DestinyStatusService::class.java)
        }
    }
}
