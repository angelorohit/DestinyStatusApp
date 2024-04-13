package com.angelo.destinystatusapp.domain.cache

import kotlin.time.Duration

interface MemoryCache<T> {
    fun getData(): T

    fun saveData(data: T, updateTime: Duration? = null)

    fun isExpired(): Boolean
}
