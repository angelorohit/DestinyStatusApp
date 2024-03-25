package com.angelo.destinystatusapp.presentation.helper.datetime.clock

import kotlin.time.Duration.Companion.milliseconds

/**
 * An implementation of [Clock] that returns the current time.
 *
 * This class should be provided by the dependency injection framework.
 */
class SystemClock : Clock {
    override fun now() = System.currentTimeMillis().milliseconds
}
