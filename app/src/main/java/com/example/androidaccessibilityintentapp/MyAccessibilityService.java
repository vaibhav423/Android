package com.example.androidaccessibilityintentapp;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "MyAccessibilityService";
    private AccessibilityButtonController mAccessibilityButtonController;

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "Service connected");

        // Initialize the accessibility button controller
        mAccessibilityButtonController = getAccessibilityButtonController();

        if (mAccessibilityButtonController != null && 
            mAccessibilityButtonController.isAccessibilityButtonAvailable()) {
            Log.d(TAG, "Accessibility button is available");

            // Set up the accessibility service to request the accessibility button
            AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
            serviceInfo.flags = AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON;
            setServiceInfo(serviceInfo);

            // Register callback for when the accessibility button is clicked
            mAccessibilityButtonController.registerAccessibilityButtonCallback(new AccessibilityButtonController.AccessibilityButtonCallback() {
                @Override
                public void onClicked(AccessibilityButtonController controller) {
                    Log.d(TAG, "Accessibility button clicked");
                    showToast("Accessibility button pressed!");
                }
            }, null);
        } else {
            Log.d(TAG, "Accessibility button is not available");
        }
    }

    // Method to show a Toast message
    private void showToast(String message) {
        // Using a Handler to show Toast from the main thread
        Handler handler = new Handler(getMainLooper());
        handler.post(() -> Toast.makeText(MyAccessibilityService.this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "Service interrupted");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "Accessibility event received: " + event.toString());
    }
}