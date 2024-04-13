package com.angelo.destinystatusapp.domain.di

import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepositoryImpl
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepositoryImpl
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepository
import com.angelo.destinystatusapp.domain.repository.DestinyStatusRepositoryImpl
import com.angelo.destinystatusapp.domain.usecase.FetchBungieHelpPostsUseCase
import com.angelo.destinystatusapp.domain.usecase.FetchBungieHelpPostsUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

val domainModule = module {
    single<Duration>(named("bungieHelpPostsFreshness")) { 1.5.minutes }
    single<BungieChannelPostsCacheRepository> { BungieChannelPostsCacheRepositoryImpl() }

    single<BungieChannelPostsDaoRepository> { BungieChannelPostsDaoRepositoryImpl() }
    single<DestinyStatusRepository> { DestinyStatusRepositoryImpl() }

    single<FetchBungieHelpPostsUseCase> { FetchBungieHelpPostsUseCaseImpl() }
}
