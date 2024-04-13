package com.angelo.destinystatusapp.domain.cache

import com.angelo.destinystatusapp.domain.helper.datetime.clock.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration

class MemoryCacheImpl<T>(
    private val freshnessDuration: Duration,
    emptyData: T,
) : MemoryCache<T>, KoinComponent {
    private val clock: Clock by inject()

    private var lastUpdateTime: Duration? = null
    private var data: T = emptyData

    override fun getData(): T = data

    override fun saveData(data: T, updateTime: Duration?) {
        this.data = data
        lastUpdateTime = updateTime ?: clock.now()
    }

    override fun isExpired(): Boolean {
        return lastUpdateTime?.let { clock.exceedsThreshold(it, freshnessDuration) } ?: true
    }
}
