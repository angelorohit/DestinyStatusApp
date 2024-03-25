package com.angelo.destinystatusapp.presentation.helper.datetime

import android.content.Context
import androidx.annotation.StringRes
import com.angelo.destinystatusapp.presentation.helper.datetime.clock.Clock
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

private fun makeDateFormatter(
    pattern: String,
    timeZone: TimeZone = TimeZone.getDefault(),
): SimpleDateFormat {
    val dateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
    dateFormat.timeZone = timeZone
    return dateFormat
}

private fun Duration.format(
    pattern: String,
    timeZone: TimeZone = TimeZone.getDefault(),
    customAmPm: Pair<String, String>? = null,
): String {
    val dateFormatter = makeDateFormatter(pattern, timeZone)
    customAmPm?.let {
        val dateFormatSymbols = DateFormatSymbols(Locale.ENGLISH)
        dateFormatSymbols.amPmStrings = arrayOf(it.first, it.second)
        dateFormatter.dateFormatSymbols = dateFormatSymbols
    }

    return dateFormatter.format(inWholeMilliseconds)
}

private enum class FormatPattern(val pattern: String) {
    // Dec 25, 2020 | 12:59 PM
    Default("MMM dd, yyyy | hh:mm a"),
}

data class TimeAgoFormattingConfig(
    @StringRes var momentsAgoStringRes: Int,
    @StringRes var minsAgoStringRes: Int,
    @StringRes var hourAgoStringRes: Int,
    @StringRes var hoursAgoStringRes: Int,
    @StringRes var todayStringRes: Int,
    @StringRes var yesterdayStringRes: Int,
) {
    fun momentsAgo(context: Context): String = context.getString(momentsAgoStringRes)
    fun minsAgo(context: Context, mins: Long): String = context.getString(minsAgoStringRes, mins)
    fun hourAgo(context: Context): String = context.getString(hourAgoStringRes)
    fun hoursAgo(context: Context, hours: Long): String = context.getString(hoursAgoStringRes, hours)
    fun today(context: Context): String = context.getString(todayStringRes)
    fun yesterday(context: Context): String = context.getString(yesterdayStringRes)
}

/**
 * Formats a [Duration] to indicate the time elapsed since the current time.
 * The current time is determined by the given [Clock].
 */
fun Duration.ago(
    context: Context,
    clock: Clock,
    timeAgoFormattingConfig: TimeAgoFormattingConfig,
    defaultPattern: String = FormatPattern.Default.pattern,
    timeZone: TimeZone = TimeZone.getDefault(),
): String {
    val diff = clock.now() - this
    val defaultFormat = format(defaultPattern, timeZone)
    return when {
        diff < Duration.ZERO -> defaultFormat
        diff < 1.minutes -> timeAgoFormattingConfig.momentsAgo(context)
        diff < 1.hours -> timeAgoFormattingConfig.minsAgo(context, diff.inWholeMinutes)
        diff < 2.hours -> timeAgoFormattingConfig.hourAgo(context)
        diff < 6.hours -> timeAgoFormattingConfig.hoursAgo(context, diff.inWholeHours)
        isToday(clock, timeZone) -> timeAgoFormattingConfig.today(context)
        isYesterday(clock, timeZone) -> timeAgoFormattingConfig.yesterday(context)
        else -> defaultFormat
    }
}

private fun Duration.isToday(
    clock: Clock,
    timeZone: TimeZone = TimeZone.getDefault(),
): Boolean =
    isWithinDays(clock = clock, timeZone = timeZone, daysAgo = 0)

private fun Duration.isYesterday(
    clock: Clock,
    timeZone: TimeZone = TimeZone.getDefault(),
): Boolean =
    isWithinDays(clock = clock, timeZone = timeZone, daysAgo = 1)

private fun Duration.isWithinDays(
    clock: Clock,
    timeZone: TimeZone = TimeZone.getDefault(),
    daysAgo: Int = 0,
): Boolean {
    // Sanity checks.
    if (daysAgo < 0 || this > clock.now()) {
        return false
    }

    val end = Calendar.getInstance(timeZone)
    end.timeInMillis = clock.now().inWholeMilliseconds
    end.add(Calendar.DAY_OF_YEAR, -daysAgo)

    val start = Calendar.getInstance(timeZone)
    start.timeInMillis = this.inWholeMilliseconds

    return end[Calendar.YEAR] == start[Calendar.YEAR] &&
        end[Calendar.DAY_OF_YEAR] == start[Calendar.DAY_OF_YEAR]
}
