package com.example.calendarapp;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1 = "ch1";

    @Override
    public void onCreate () {
        super.onCreate();
        
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(
                    CHANNEL_1,
                    "channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            nc.setDescription("channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(nc);
        }
    }

}
