package com.angelo.destinystatusapp.domain.di

import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsCacheRepositoryImpl
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepository
import com.angelo.destinystatusapp.domain.repository.BungieChannelPostsDaoRepositoryImpl
import com.angelo.destinystatusapp.domain.repository.RemoteBungieChannelPostsRepository
import com.angelo.destinystatusapp.domain.repository.RemoteBungieChannelPostsRepositoryImpl
import com.angelo.destinystatusapp.domain.usecase.FetchPostsUseCase
import com.angelo.destinystatusapp.domain.usecase.FetchPostsUseCaseImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

val domainModule = module {
    single<Duration>(named("bungieChannelPostsFreshness")) { 1.5.minutes }
    single<BungieChannelPostsCacheRepository> { BungieChannelPostsCacheRepositoryImpl() }

    single<BungieChannelPostsDaoRepository> { BungieChannelPostsDaoRepositoryImpl() }
    single<RemoteBungieChannelPostsRepository> { RemoteBungieChannelPostsRepositoryImpl() }

    single<FetchPostsUseCase> { FetchPostsUseCaseImpl() }
}
