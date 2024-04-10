package com.angelo.destinystatusapp.domain.di

import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepositoryImpl
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepositoryImpl
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepositoryImpl
import com.angelo.destinystatusapp.domain.usecase.FetchBungieHelpPostsUseCase
import com.angelo.destinystatusapp.domain.usecase.FetchBungieHelpPostsUseCaseImpl
import org.koin.dsl.module

val domainModule = module {
    single<BungieChannelPostsDaoRepository> { BungieChannelPostsDaoRepositoryImpl() }
    single<DestinyStatusRepository> { DestinyStatusRepositoryImpl() }
    single<BungieChannelPostsCacheRepository> { BungieChannelPostsCacheRepositoryImpl() }

    single<FetchBungieHelpPostsUseCase> { FetchBungieHelpPostsUseCaseImpl() }
}
