import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.angelo.destinystatusapp.test.BuildConfig
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 24)
class LaunchTest {
    companion object {
        private const val LAUNCH_TIMEOUT = 5000L
    }

    private lateinit var device: UiDevice

    @Before
    fun launchApp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from Home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.wait(
            Until.hasObject(By.pkg(launcherPackage).depth(0)),
            LAUNCH_TIMEOUT,
        )

        // Launch the app
        val context = InstrumentationRegistry.getInstrumentation().context
        val packageName = InstrumentationRegistry.getInstrumentation().targetContext.packageName
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.wait(
            Until.hasObject(By.pkg(BuildConfig.APPLICATION_ID).depth(0)),
            LAUNCH_TIMEOUT,
        )
    }

    @Test
    fun testClickOnSettingsButtonNavigatesToSettingsScreen() {
        val mainScreen = MainScreen(device)
        val settingsScreen = SettingsScreen(device)

        mainScreen.settingsButton?.click()

        assertThat(
            "Community Driven blurb not found",
            settingsScreen.communityDrivenBlurb != null,
        )
    }
}
