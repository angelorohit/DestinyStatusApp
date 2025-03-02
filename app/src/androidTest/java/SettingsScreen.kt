import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.angelo.destinystatusapp.BuildConfig
import com.angelo.destinystatusapp.R

class SettingsScreen(private val device: UiDevice) {
    private val backButton: UiObject2?
        get() = device.findObject(By.desc(stringResource(R.string.back_icon_content_description)))
    private val communityDrivenBlurbSelector = By.text(stringResource(R.string.community_driven))
    private val communityDrivenBlurb: UiObject2?
        get() = device.findObject(communityDrivenBlurbSelector)
    private val openSourceItem: UiObject2?
        get() = device.findObject(By.text(stringResource(R.string.open_source)))
    private val privacyPolicyItem: UiObject2?
        get() = device.findObject(By.text(stringResource(R.string.privacy_policy)))
    private val licensesItem: UiObject2?
        get() = device.findObject(By.text(stringResource(R.string.open_source_licenses)))
    private val attributionsItem: UiObject2?
        get() = device.findObject(By.text(stringResource(R.string.attributions)))
    private val versionItem: UiObject2?
        get() = device.findObject(By.text("${BuildConfig.BUILD_TYPE} - v${BuildConfig.VERSION_NAME}"))

    fun isDisplayed(): Boolean {
        device.waitForObject(communityDrivenBlurbSelector)

        return backButton != null &&
            communityDrivenBlurb != null &&
            openSourceItem != null &&
            privacyPolicyItem != null &&
            licensesItem != null &&
            attributionsItem != null &&
            versionItem != null
    }
}
