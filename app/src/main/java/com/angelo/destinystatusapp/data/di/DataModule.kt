package com.angelo.destinystatusapp.data.di

import com.angelo.destinystatusapp.data.local.datastore.BungieChannelPostsDao
import com.angelo.destinystatusapp.data.local.datastore.BungieChannelPostsDaoImpl
import com.angelo.destinystatusapp.data.remote.RemoteDataSource
import com.angelo.destinystatusapp.data.remote.RemoteDataSourceImpl
import com.angelo.destinystatusapp.data.remote.api.DestinyStatusService
import com.angelo.destinystatusapp.data.remote.api.ServiceClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single<DestinyStatusService> {
        ServiceClient.createDestinyStatusService(androidContext())
    }

    single<RemoteDataSource> { RemoteDataSourceImpl() }

    single<BungieChannelPostsDao> { BungieChannelPostsDaoImpl(androidContext()) }
}
