package com.example.androidaccessibilityintentapp;

import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "MyAccessibilityService";

    // Accessibility Button variables
    private AccessibilityButtonController mAccessibilityButtonController;
    private AccessibilityButtonController.AccessibilityButtonCallback mAccessibilityButtonCallback;
    private boolean mIsAccessibilityButtonAvailable;

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // ... (Other event handling if needed) ...
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "Accessibility Service Interrupted"); 
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo info = getServiceInfo();
        // Set flags and event types as before...

        // Request the Accessibility Button:
        info.flags = info.flags | AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON;

        // ... other service configuration ...

        // Initialize Accessibility Button Callback:
        initializeAccessibilityButtonCallback();
    }

    private void initializeAccessibilityButtonCallback() {
        mAccessibilityButtonController = getAccessibilityButtonController(); // Corrected line
        mIsAccessibilityButtonAvailable = mAccessibilityButtonController.isAccessibilityButtonAvailable();

        if (!mIsAccessibilityButtonAvailable) {
            return; // If the button isn't available, do nothing
        }

        mAccessibilityButtonCallback = new AccessibilityButtonController.AccessibilityButtonCallback() {
            @Override
            public void onClicked(AccessibilityButtonController controller) {
                Log.d(TAG, "Accessibility Shortcut button pressed!");

                // Action to perform when the Accessibility Shortcut button is clicked:
                Toast.makeText(getApplicationContext(), "Accessibility Shortcut Clicked!", Toast.LENGTH_SHORT).show();

                // Navigate to Home Screen
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onAvailabilityChanged(AccessibilityButtonController controller, boolean available) {
                mIsAccessibilityButtonAvailable = available;
                Log.d(TAG, "Accessibility button availability changed: " + available);
            }
        };

        mAccessibilityButtonController.registerAccessibilityButtonCallback(mAccessibilityButtonCallback);
    }
}
