import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.angelo.destinystatusapp.R

class SettingsScreen(private val device: UiDevice) {
    val communityDrivenBlurb: UiObject2? by lazy {
        device.findObject(By.text(stringResource(R.string.community_driven)))
    }
}
