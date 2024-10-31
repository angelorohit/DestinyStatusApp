package com.angelo.destinystatusapp.domain.helper.datetime

import android.content.Context
import com.angelo.destinystatusapp.R
import com.angelo.destinystatusapp.domain.helper.datetime.clock.testing.FakeClock
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import java.util.TimeZone
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class DateTimeFormatExtensionsKtTest {
    private val fakeClock = FakeClock()
    private val mockContext: Context = mockk()

    private val timeAgoFormattingConfig = TimeAgoFormattingConfig(
        momentsAgoStringRes = R.string.moments_ago,
        minsAgoStringRes = R.string.minutes_ago,
        hourAgoStringRes = R.string.hour_ago,
        hoursAgoStringRes = R.string.hours_ago,
        todayStringRes = R.string.today,
        yesterdayStringRes = R.string.yesterday,
    )

    @BeforeEach
    fun setUp() {
        every { mockContext.getString(R.string.moments_ago) } returns "Moments ago"
        every { mockContext.getString(R.string.minutes_ago, 1L) } returns "1 minute ago"
        every { mockContext.getString(R.string.hour_ago) } returns "An hour ago"
        every { mockContext.getString(R.string.hours_ago, 5L) } returns "5 hours ago"
        every { mockContext.getString(R.string.today) } returns "Today"
        every { mockContext.getString(R.string.yesterday) } returns "Yesterday"
    }

    @Suppress("unused")
    enum class TimeAgoTestCase(
        val timeElapsed: Duration,
        val expected: String,
    ) {
        FIFTY_NINE_SECONDS_ELAPSED(59.seconds, "Moments ago"),
        ONE_MINUTE_ELAPSED(1.minutes, "1 minute ago"),
        ONE_HOUR_ELAPSED(1.hours, "An hour ago"),
        FIVE_HOURS_ELAPSED(5.hours, "5 hours ago"),
        SIX_HOURS_ELAPSED(6.hours, "Today"),
        TWENTY_THREE_HOURS_ELAPSED(23.hours, "Today"),
        ONE_DAY_ELAPSED(1.days, "Yesterday"),
        TWO_DAYS_ELAPSED(2.days, "Oct 31, 2024 | 12:00 AM"),
        NOW(Duration.ZERO, "Moments ago"),
        FUTURE((-1).days, "Oct 31, 2024 | 12:00 AM"),
    }

    @ParameterizedTest
    @EnumSource(TimeAgoTestCase::class)
    fun `Given time elapsed, when ago is called, then the expected string is returned`(testCase: TimeAgoTestCase) {
        // 31 Oct 2024 - 00:00:00 UTC
        val testTime = 1730332800.seconds
        fakeClock.setCurrentTime(testTime + testCase.timeElapsed)

        val actual = testTime.ago(
            context = mockContext,
            clock = fakeClock,
            timeAgoFormattingConfig = timeAgoFormattingConfig,
            timeZone = TimeZone.getTimeZone("UTC")
        )

        assertEquals(testCase.expected, actual)
    }
}
