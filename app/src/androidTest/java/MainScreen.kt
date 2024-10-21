import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.angelo.destinystatusapp.R

class MainScreen(private val device: UiDevice) {
    val settingsButton: UiObject2? by lazy {
        device.findObject(By.desc(stringResource(R.string.settings_icon_content_description)))
    }
}
