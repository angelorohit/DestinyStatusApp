package com.angelo.destinystatusapp.domain.di

import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepositoryImpl
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<BungieChannelPostsDaoRepository> { BungieChannelPostsDaoRepositoryImpl(dao = get()) }
    single<DestinyStatusRepository> { DestinyStatusRepositoryImpl(remoteDataSource = get()) }
}
