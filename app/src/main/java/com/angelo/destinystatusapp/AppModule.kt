package com.angelo.destinystatusapp

import com.angelo.destinystatusapp.data.di.dataModule
import com.angelo.destinystatusapp.domain.di.domainModule
import com.angelo.destinystatusapp.presentation.di.presentationModule
import com.angelo.destinystatusapp.presentation.helper.datetime.clock.Clock
import com.angelo.destinystatusapp.presentation.helper.datetime.clock.SystemClock
import org.koin.dsl.module

val appModule = module {
    single<Clock> { SystemClock() }

    includes(dataModule, domainModule, presentationModule)
}
