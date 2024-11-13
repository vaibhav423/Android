import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;

public class MyAccessibilityService extends AccessibilityService {

    private AccessibilityButtonController mAccessibilityButtonController;
    private boolean mIsAccessibilityButtonAvailable = false;

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();

        // Initialize the accessibility button controller
        mAccessibilityButtonController = getAccessibilityButtonController();
        if (mAccessibilityButtonController != null) {
            mIsAccessibilityButtonAvailable = mAccessibilityButtonController.isAccessibilityButtonAvailable();

            // Only proceed if the accessibility button is available
            if (mIsAccessibilityButtonAvailable) {
                // Set up the accessibility service to request the accessibility button
                AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
                serviceInfo.flags = AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON;
                setServiceInfo(serviceInfo);

                // Register callback for when the accessibility button is clicked
                AccessibilityButtonController.AccessibilityButtonCallback accessibilityButtonCallback = new AccessibilityButtonController.AccessibilityButtonCallback() {
                    @Override
                    public void onClicked(AccessibilityButtonController controller) {
                        // Handle the button click event
                        Log.d("MY_APP_TAG", "Accessibility button pressed!");

                        // Send a broadcast when the accessibility button is pressed
                        sendBroadcastWithAction();
                    }

                    @Override
                    public void onAvailabilityChanged(AccessibilityButtonController controller, boolean available) {
                        mIsAccessibilityButtonAvailable = available;
                    }
                };

                // Register callback
                mAccessibilityButtonController.registerAccessibilityButtonCallback(accessibilityButtonCallback, null);
            } else {
                Log.e("MY_APP_TAG", "Accessibility button not available on this device.");
            }
        } else {
            Log.e("MY_APP_TAG", "Failed to get AccessibilityButtonController.");
        }
    }

    // Method to send the broadcast when the accessibility button is clicked
    private void sendBroadcastWithAction() {
        // Create an Intent with a custom action 'abcd'
        Intent intent = new Intent("abcd");

        // Optionally, add some extras
        intent.putExtra("extra_key", "extra_value");

        // Send the broadcast
        sendBroadcast(intent);

        Log.d("MY_APP_TAG", "Broadcast intent with action 'abcd' sent!");
    }

    @Override
    public void onInterrupt() {
        // Handle interrupt if needed (e.g., stop service)
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Handle any other accessibility events
    }
}
