import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.angelo.destinystatusapp.R

class MainScreen(private val device: UiDevice) {
    private val settingsButtonSelector = By.desc(stringResource(R.string.settings_icon_content_description))
    val settingsButton: UiObject2?
        get() = device.findObject(settingsButtonSelector)

    fun isDisplayed(): Boolean {
        device.waitForObject(settingsButtonSelector)

        return settingsButton != null
    }
}
