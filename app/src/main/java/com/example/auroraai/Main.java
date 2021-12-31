package com.example.auroraai;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.auroraai.api.ServiceHolder;
import com.example.auroraai.core.PropertiesHelper;
import com.example.auroraai.service.ServiceCommunicator;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // todo: move to more appropriate place
        PropertiesHelper.init(this);
        ServiceHolder.init();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(Main.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, PackageManager.PERMISSION_GRANTED);
        startForegroundService(new Intent(Main.this, ServiceCommunicator.class));
    }

    /*public void Read_SMS(View view) {

        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null, null, null, null);
        cursor.moveToFirst();

        myTextView.setText(cursor.getString(0));

        cursor.close();
    }*/
}