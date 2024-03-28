package com.angelo.destinystatusapp.domain.di

import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepositoryImpl
import com.angelo.destinystatusapp.domain.usecase.FetchBungieHelpUpdatesUseCase
import com.angelo.destinystatusapp.domain.usecase.FetchBungieHelpUpdatesUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    single<DestinyStatusRepository> { DestinyStatusRepositoryImpl(remoteDataSource = get()) }
    single<FetchBungieHelpUpdatesUseCase> { FetchBungieHelpUpdatesUseCaseImpl(repository = get()) }
}
