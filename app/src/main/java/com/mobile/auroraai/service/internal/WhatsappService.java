package com.mobile.auroraai.service.internal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mobile.auroraai.core.LogAble;

import java.net.URLEncoder;

public class WhatsappService extends LogAble {

    public void sendWhatsappToGroup(final Context context, final String message) {
        final String localizeMessage = appendSuffix(message);
        final Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.setPackage("com.whatsapp");
        i.putExtra(Intent.EXTRA_TEXT, localizeMessage);
        context.startActivity(Intent.createChooser(i, localizeMessage));
    }

    public void sendWhatsappPersonal(final Context context, final String phone, final String message) {
        final Intent i = new Intent(Intent.ACTION_VIEW);
        try {
            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + URLEncoder.encode(appendSuffix(message), "UTF-8");
            i.setPackage("com.whatsapp");
            i.setData(Uri.parse(url));
            context.startActivity(i);
        } catch (Exception e) {
            logError("Unable to send whatsapp personally due to " + e.getMessage(), e);
        }
    }

    private String appendSuffix(final String message) {
        return String.format("%s\n\n[Send using Aurora]", message);
    }

    @Override
    public String getClassTag() {
        return "WhatsappService";
    }
}
