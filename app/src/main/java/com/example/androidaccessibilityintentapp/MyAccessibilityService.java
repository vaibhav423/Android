package com.example.androidaccessibilityintentapp;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.content.Intent;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Detect specific event type (adjust depending on your actual needs)
        if (event.getEventType() == AccessibilityEvent.TYPE_GESTURE_DETECTION_START) {
            // Trigger the Intent when gesture detection starts
            sendIntent();
        }
    }

    @Override
    public void onInterrupt() {
        // Handle interruptions (if necessary)
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        // Initialization when service is connected (optional)
    }

    private void sendIntent() {
        // Broadcast the custom intent when accessibility event is triggered
        Intent intent = new Intent();
        intent.setAction("abcd");  // Set the custom action
        sendBroadcast(intent);     // Broadcast the intent
    }
}
