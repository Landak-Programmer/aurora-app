package com.example.auroraai.service;

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

import androidx.annotation.Nullable;

import com.example.auroraai.R;
import com.example.auroraai.listener.SmsListener;

public class ServiceCommunicator extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        final SmsListener smsListener = new SmsListener();
        final IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsListener, mIntentFilter);

        final String CHANNELID = "Testing";
        final NotificationChannel channel = new NotificationChannel(CHANNELID, CHANNELID, NotificationManager.IMPORTANCE_LOW);

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        final Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setContentTitle("[SERVICE STARTED]")
                .setContentText("Starting sms service")
                .setSmallIcon(R.drawable.ic_launcher_background);
        startForeground(1001, notification.build());
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
}
