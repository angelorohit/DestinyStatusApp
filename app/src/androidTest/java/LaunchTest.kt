import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 24)
class LaunchTest {
    private lateinit var device: UiDevice

    @Before
    fun launchApp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Start from Home screen
        device.pressHome()

        // Wait for launcher
        val launcherPackage: String = device.launcherPackageName
        assertThat(launcherPackage, notNullValue())
        device.waitForObject(By.pkg(launcherPackage).depth(0))

        // Launch the app
        val context = InstrumentationRegistry.getInstrumentation().context
        val packageName = InstrumentationRegistry.getInstrumentation().targetContext.packageName
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)

        // Wait for the app to appear
        device.waitForObject(By.pkg(packageName).depth(0))
    }

    @Test
    fun testClickOnSettingsButtonNavigatesToSettingsScreen() {
        val mainScreen = MainScreen(device)
        val settingsScreen = SettingsScreen(device)

        mainScreen.settingsButton?.click()

        assertThat(
            "One or more items in the Settings Screen could not be found.",
            settingsScreen.isDisplayed(),
        )

        device.pressBack()

        assertThat(
            "The Main Screen could not be found after pressing the back button.",
            mainScreen.isDisplayed(),
        )
    }
}
