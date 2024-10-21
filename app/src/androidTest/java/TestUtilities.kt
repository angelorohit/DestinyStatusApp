import android.content.Context
import androidx.annotation.StringRes
import androidx.test.platform.app.InstrumentationRegistry

fun stringResource(
    @StringRes resId: Int,
    context: Context = InstrumentationRegistry.getInstrumentation().targetContext,
): String = context.getString(resId)
