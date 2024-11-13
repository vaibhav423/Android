import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityButtonController
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.util.Log

class MyAccessibilityService : AccessibilityService() {

    private var mAccessibilityButtonController: AccessibilityButtonController? = null
    private var mIsAccessibilityButtonAvailable: Boolean = false

    override fun onServiceConnected() {
        super.onServiceConnected()

        mAccessibilityButtonController = accessibilityButtonController
        mIsAccessibilityButtonAvailable = mAccessibilityButtonController?.isAccessibilityButtonAvailable ?: false

        // Only proceed if the accessibility button is available
        if (mIsAccessibilityButtonAvailable) {
            // Set up the accessibility service to request the accessibility button
            val serviceInfo = AccessibilityServiceInfo().apply {
                flags = AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON
            }
            serviceInfo = serviceInfo.apply {
                flags = flags or AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON
            }

            // Register callback for when accessibility button is clicked
            val accessibilityButtonCallback = object : AccessibilityButtonController.AccessibilityButtonCallback() {
                override fun onClicked(controller: AccessibilityButtonController) {
                    // Handle the button click event
                    Log.d("MY_APP_TAG", "Accessibility button pressed!")

                    // Send a broadcast when the accessibility button is pressed
                    sendBroadcastWithAction()
                }

                override fun onAvailabilityChanged(controller: AccessibilityButtonController, available: Boolean) {
                    mIsAccessibilityButtonAvailable = available
                }
            }

            // Register callback
            mAccessibilityButtonController?.registerAccessibilityButtonCallback(accessibilityButtonCallback, null)
        }
    }

    // Method to send the broadcast when the accessibility button is clicked
    private fun sendBroadcastWithAction() {
        // Create an Intent with a custom action 'abcd'
        val intent = Intent("abcd")

        // Optionally, add some extras
        intent.putExtra("extra_key", "extra_value")

        // Send the broadcast
        sendBroadcast(intent)

        Log.d("MY_APP_TAG", "Broadcast intent with action 'abcd' sent!")
    }

    override fun onInterrupt() {
        // Handle interrupt if needed (e.g., stop service)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Handle any other accessibility events
    }
}
