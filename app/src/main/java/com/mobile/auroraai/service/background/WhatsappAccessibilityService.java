package com.mobile.auroraai.service.background;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.mobile.auroraai.GlobalHolder;
import com.mobile.auroraai.R;
import com.mobile.auroraai.core.TagAble;

import java.util.List;

public class WhatsappAccessibilityService extends AccessibilityService implements TagAble {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

        if (getRootInActiveWindow() == null) {
            return;
        }

        final AccessibilityNodeInfoCompat rootInActiveWindow = AccessibilityNodeInfoCompat.wrap(getRootInActiveWindow());

        // Whatsapp Message EditText id
        final List<AccessibilityNodeInfoCompat> messageNodeList = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.whatsapp:id/entry");
        if (messageNodeList == null || messageNodeList.isEmpty()) {
            return;
        }

        // check if the whatsapp message EditText field is filled with text and ending with your suffix (explanation above)
        final AccessibilityNodeInfoCompat messageField = messageNodeList.get(0);
        if (messageField.getText() == null || messageField.getText().length() == 0
                || !messageField.getText().toString().endsWith(getApplicationContext().getString(R.string.whatsapp_suffix))) { // So your service doesn't process any message, but the ones ending your apps suffix
            return;
        }

        // Whatsapp send button id
        final List<AccessibilityNodeInfoCompat> sendMessageNodeInfoList = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.whatsapp:id/send");
        if (sendMessageNodeInfoList == null || sendMessageNodeInfoList.isEmpty()) {
            return;
        }

        final AccessibilityNodeInfoCompat sendMessageButton = sendMessageNodeInfoList.get(0);
        if (!sendMessageButton.isVisibleToUser()) {
            return;
        }

        // Now fire a click on the send button
        sendMessageButton.performAction(AccessibilityNodeInfo.ACTION_CLICK);

        goBack();
    }

    /**
     * Now go back to your app by clicking on the Android back button twice:
     * First one to leave the conversation screen
     * Second one to leave whatsapp
     */
    private void goBack() {
        try {
            for (int i = 0; i < 2; i++) {
                Thread.sleep(500); // hack for certain devices in which the immediate back click is too fast to handle
                performGlobalAction(GLOBAL_ACTION_BACK);
            }
        } catch (InterruptedException e) {
            GlobalHolder.getStaticLog().logError(getClassTag(), "Wait interrupted. Msg: " + e.getMessage(), e);
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public String getClassTag() {
        return "WhatsappAccessibilityService";
    }
}
