package com.angelo.destinystatusapp.data.di

import com.angelo.destinystatusapp.BuildConfig
import com.angelo.destinystatusapp.data.local.datastore.BungieChannelPostsDao
import com.angelo.destinystatusapp.data.local.datastore.BungieChannelPostsDaoImpl
import com.angelo.destinystatusapp.data.remote.RemoteDataSource
import com.angelo.destinystatusapp.data.remote.RemoteDataSourceImpl
import com.angelo.destinystatusapp.data.remote.api.ApiConstants
import com.angelo.destinystatusapp.data.remote.api.DestinyStatusService
import com.angelo.destinystatusapp.data.remote.interceptor.NetworkAvailabilityInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private val json = Json {
    ignoreUnknownKeys = true
}

val dataModule = module {
    single<DestinyStatusService> {
        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(NetworkAvailabilityInterceptor(androidContext()))
        if (BuildConfig.DEBUG) {
            clientBuilder.addNetworkInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
        }

        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(
                json.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType(),
                ),
            )
            .build()
            .create(DestinyStatusService::class.java)
    }

    single<RemoteDataSource> { RemoteDataSourceImpl() }

    single<BungieChannelPostsDao> { BungieChannelPostsDaoImpl(androidContext()) }
}
