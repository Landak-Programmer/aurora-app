package com.mobile.auroraai.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

import com.google.gson.JsonObject;
import com.mobile.auroraai.GlobalHolder;
import com.mobile.auroraai.api.ServerCallback;
import com.mobile.auroraai.core.TagAble;
import com.mobile.auroraai.service.ServiceHolder;
import com.mobile.auroraai.service.api.BaseAPIService;

import org.json.JSONException;
import org.json.JSONObject;

public class SmsListener extends BroadcastReceiver implements TagAble {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (final SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                final BaseAPIService baseAPIService = ServiceHolder.getSmsInboxService();
                final JSONObject request = new JSONObject();
                try {
                    request.put("message", smsMessage.getMessageBody());
                    baseAPIService.create(context, request, new ServerCallback<JsonObject>() {

                        @Override
                        public void onSuccess(JsonObject response) {
                            // do nothing for now
                        }

                        @Override
                        public String getClassTag() {
                            return SmsListener.this.getClassTag();
                        }
                    });
                } catch (JSONException e) {
                    GlobalHolder.getStaticLog().logError(
                            getClassTag(), "Unable to create inbox sms from sms listener due to: " + e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public String getClassTag() {
        return "SmsListener";
    }
}
