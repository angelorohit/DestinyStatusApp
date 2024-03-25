package com.angelo.destinystatusapp.presentation.helper.datetime.clock

import kotlin.time.DurationUnit
import kotlin.time.toDuration

/**
 * An implementation of [Clock] that returns the current time.
 *
 * This class should be provided by the dependency injection framework.
 */
class SystemClock : Clock {
    override fun now() = System.currentTimeMillis().toDuration(DurationUnit.MILLISECONDS)
}