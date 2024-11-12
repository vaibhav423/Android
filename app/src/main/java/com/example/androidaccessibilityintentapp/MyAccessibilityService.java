import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // No specific events handled here.
    }

    override fun onInterrupt() {
        // Handle interruptions, if necessary.
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        // Perform any initial setup here, if needed.
    }

    override fun onAccessibilityButtonClicked() {
        // Send a broadcast intent when the accessibility button is pressed
        val intent = Intent().apply {
            action = "abcd" // Custom action name
        }
        sendBroadcast(intent)
    }
}
