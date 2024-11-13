package com.example.androidaccessibilityintentapp;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class MyAccessibilityService extends AccessibilityService {

    private AccessibilityButtonController mAccessibilityButtonController;
    private boolean mIsAccessibilityButtonAvailable = false;

@Override
public void onServiceConnected() {
    super.onServiceConnected();
    
    Log.d("MY_APP_TAG", "Service Connected");
    
    // Initialize Accessibility Button Controller
    mAccessibilityButtonController = getAccessibilityButtonController();

    if (mAccessibilityButtonController != null) {
        mIsAccessibilityButtonAvailable = mAccessibilityButtonController.isAccessibilityButtonAvailable();
        
        Log.d("MY_APP_TAG", "Accessibility button available: " + mIsAccessibilityButtonAvailable);

        // Proceed only if the accessibility button is available
        if (mIsAccessibilityButtonAvailable) {
            AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
            serviceInfo.flags = AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON | AccessibilityServiceInfo.FLAG_DEFAULT;
            setServiceInfo(serviceInfo);

            // Register callback for accessibility button clicks
            mAccessibilityButtonController.registerAccessibilityButtonCallback(new AccessibilityButtonController.AccessibilityButtonCallback() {
                @Override
                public void onClicked(AccessibilityButtonController controller) {
                    Log.d("MY_APP_TAG", "Accessibility button pressed!");
                    sendBroadcastWithAction(); // Send broadcast
                    showToast("Intent sent!"); // Show toast
                }

                @Override
                public void onAvailabilityChanged(AccessibilityButtonController controller, boolean available) {
                    mIsAccessibilityButtonAvailable = available;
                    Log.d("MY_APP_TAG", "Accessibility button availability changed: " + available);
                }
            }, null);
        } else {
            Log.e("MY_APP_TAG", "Accessibility button not available on this device.");
        }
    } else {
        Log.e("MY_APP_TAG", "Failed to get AccessibilityButtonController.");
    }
}

// Method to send broadcast when the accessibility button is clicked
private void sendBroadcastWithAction() {
    Intent intent = new Intent("abcd"); // Custom action
    sendBroadcast(intent);
    Log.d("MY_APP_TAG", "Broadcast intent with action 'abcd' sent!");
}

// Method to show a Toast message
private void showToast(String message) {
    Handler handler = new Handler(getMainLooper());
    handler.post(() -> {
        Toast.makeText(MyAccessibilityService.this, message, Toast.LENGTH_SHORT).show();
        Log.d("MY_APP_TAG", "Toast displayed: " + message);
    });
}

@Override
public void onInterrupt() {
    Log.d("MY_APP_TAG", "Service interrupted");
}

@Override
public void onAccessibilityEvent(AccessibilityEvent event) {
    Log.d("MY_APP_TAG", "Accessibility event received: " + event.toString());
}
        // If event type is related to window content, log it for clarity
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED) {
            Log.d("MY_APP_TAG", "Window content changed: " + event.getPackageName());
        }
    }
}
