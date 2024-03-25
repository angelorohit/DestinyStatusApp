package com.angelo.destinystatusapp.presentation.helper.datetime.clock

import kotlin.time.Duration

/**
 * A clock that returns the current time.
 *
 * Inject this clock into your class instead of using [System.currentTimeMillis] directly.
 */
interface Clock {
    fun now(): Duration

    fun exceedsThreshold(startTime: Duration, threshold: Duration) = now() - startTime > threshold
}
