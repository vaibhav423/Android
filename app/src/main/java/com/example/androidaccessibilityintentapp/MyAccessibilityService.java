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
        Log.d(TAG, "Service connected.");

        mAccessibilityButtonController = getAccessibilityButtonController();

        if (mAccessibilityButtonController != null) {
            if (mAccessibilityButtonController.isAccessibilityButtonAvailable()) {
                mAccessibilityButtonController.registerAccessibilityButtonCallback(
                    new AccessibilityButtonController.AccessibilityButtonCallback() {
                        @Override
                        public void onClicked(AccessibilityButtonController controller) {
                            Log.d(TAG, "Accessibility button clicked!");
                            showToast("Accessibility button clicked!");
                        }
                    }, null
                );
                Log.d(TAG, "AccessibilityButtonCallback registered.");
            } else {
                Log.d(TAG, "Accessibility button is not available.");
            }
        }

        AccessibilityServiceInfo info = getServiceInfo();
        if (info == null) {
            info = new AccessibilityServiceInfo();
        }
        info.flags |= AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON;
        setServiceInfo(info);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
            case AccessibilityEvent.TYPE_ACCESSIBILITY_BUTTON_CLICKED:
                Log.d(TAG, "Relevant accessibility event received: " + event.toString());
                break;

            default:
                // Ignore irrelevant events
                break;
        }
    }

    private void showToast(String message) {
        Handler handler = new Handler(getMainLooper());
        handler.post(() -> Toast.makeText(MyAccessibilityService.this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onInterrupt() {
        Log.d(TAG, "Service interrupted.");
    }
}