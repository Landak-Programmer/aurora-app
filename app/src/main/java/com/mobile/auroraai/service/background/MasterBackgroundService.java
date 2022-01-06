package com.mobile.auroraai.service.background;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.mobile.auroraai.R;
import com.mobile.auroraai.core.AppConfig;
import com.mobile.auroraai.listener.SmsListener;

public class MasterBackgroundService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final String CHANNELID = "Background service notification";
        final NotificationChannel channel = new NotificationChannel(CHANNELID, CHANNELID, NotificationManager.IMPORTANCE_HIGH);
        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        final Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setSmallIcon(R.mipmap.aurora_icon);

        if (AppConfig.getGlobalServiceEnabled()) {

            String message = "";

            if (AppConfig.getSmsListenerServiceEnabled()) {
                initSmsListener();
                message += "Sms Listener\n";
            }

            if (AppConfig.getAccessibilityListenerServiceEnabled()) {
                accessibilityPrompt();
                message += "Accessibility Listener\n";
            }

            notification.setContentTitle("[SERVICE STARTED]")
                    .setContentText(message);
        } else {
            notification.setContentTitle("[SERVICE DISABLED]");
        }
        startForeground(1001, notification.build());
    }

    private void initSmsListener() {
        final SmsListener smsListener = new SmsListener();
        final IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsListener, mIntentFilter);
    }

    private void accessibilityPrompt() {
        if (!isAccessibilityOn(this)) {
            final Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            this.startActivity(intent);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        final Intent intent = new Intent(getApplicationContext(), this.getClass());
        final PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // ########################################################### UTILS ###########################################################

    private boolean isAccessibilityOn(Context context) {
        int accessibilityEnabled = 0;
        final String service = context.getPackageName() + "/" + WhatsappAccessibilityService.class.getCanonicalName();
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getApplicationContext().getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException ignored) {
        }

        TextUtils.SimpleStringSplitter colonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            String settingValue = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                colonSplitter.setString(settingValue);
                while (colonSplitter.hasNext()) {
                    String accessibilityService = colonSplitter.next();

                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
