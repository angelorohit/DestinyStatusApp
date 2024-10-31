import android.content.Context
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import kotlin.time.Duration.Companion.seconds

fun stringResource(
    @StringRes resId: Int,
    context: Context = InstrumentationRegistry.getInstrumentation().targetContext,
): String = context.getString(resId)

private val screenWaitTimeout = 5.seconds
fun UiDevice.waitForObject(bySelector: BySelector) {
    wait(Until.hasObject(bySelector), screenWaitTimeout.inWholeMilliseconds)
}
