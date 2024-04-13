package com.angelo.destinystatusapp.domain.helper.datetime.clock.testing

import com.angelo.destinystatusapp.domain.helper.datetime.clock.Clock
import kotlin.time.Duration

/**
 * A fake implementation of [Clock] that allows you to set the current time.
 *
 * This class is useful for testing.
 */
class FakeClock(private var currentTime: Duration = Duration.ZERO) : Clock {
    override fun now() = currentTime
}
