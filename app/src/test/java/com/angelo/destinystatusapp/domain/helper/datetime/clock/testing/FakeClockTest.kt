package com.angelo.destinystatusapp.domain.helper.datetime.clock.testing

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class FakeClockTest {
    private val fakeClock = FakeClock()

    @Test
    fun `When setCurrentTime() is called, then currentTime() returns the time that was set`() {
        fakeClock.setCurrentTime(1800.milliseconds)

        assertEquals(1800.milliseconds, fakeClock.now())
    }

    @Suppress("unused")
    enum class ExceedsThresholdTestCase(
        val currentTime: Duration,
        val startTime: Duration,
        val threshold: Duration,
        val expected: Boolean,
    ) {
        ELAPSED_TIME_IS_GREATER_THAN_THRESHOLD(
            currentTime = 1800.milliseconds,
            startTime = 1000.milliseconds,
            threshold = 500.milliseconds,
            expected = true,
        ),
        ELAPSED_TIME_IS_LESS_THAN_THRESHOLD(
            currentTime = 1800.milliseconds,
            startTime = 1000.milliseconds,
            threshold = 1000.milliseconds,
            expected = false,
        ),
        ELAPSED_TIME_IS_EQUAL_TO_THRESHOLD(
            currentTime = 1800.milliseconds,
            startTime = 1000.milliseconds,
            threshold = 800.milliseconds,
            expected = false,
        ),
    }

    @ParameterizedTest
    @EnumSource(ExceedsThresholdTestCase::class)
    fun `When exceedsThreshold is called, then the expected result in returned`(testCase: ExceedsThresholdTestCase) {
        fakeClock.setCurrentTime(testCase.currentTime)

        val actual = fakeClock.exceedsThreshold(
            testCase.startTime,
            testCase.threshold,
        )

        assertEquals(testCase.expected, actual)
    }
}
