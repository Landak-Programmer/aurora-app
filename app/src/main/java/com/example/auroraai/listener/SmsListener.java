package com.example.auroraai.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.auroraai.api.BaseService;
import com.example.auroraai.api.ServiceHolder;

import org.json.JSONException;
import org.json.JSONObject;

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (final SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                final BaseService baseService = ServiceHolder.getSmsInboxService();
                final JSONObject request = new JSONObject();
                try {
                    request.put("message", smsMessage.getMessageBody());
                    baseService.create(context, request);
                } catch (JSONException e) {
                    Log.e("TAG", "Error when fetching message from sms listener", e);
                }
            }
        }
    }
}
