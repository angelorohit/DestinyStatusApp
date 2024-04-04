package com.angelo.destinystatusapp.domain.di

import com.angelo.destinystatusapp.domain.repository.BungieHelpDaoRepository
import com.angelo.destinystatusapp.domain.repository.BungieHelpDaoRepositoryImpl
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<BungieHelpDaoRepository> { BungieHelpDaoRepositoryImpl(dao = get()) }
    single<DestinyStatusRepository> { DestinyStatusRepositoryImpl(remoteDataSource = get()) }
}
