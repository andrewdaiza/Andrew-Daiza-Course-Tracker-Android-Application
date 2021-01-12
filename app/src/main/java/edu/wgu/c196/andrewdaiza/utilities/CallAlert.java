package edu.wgu.c196.andrewdaiza.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;


public class CallAlert extends BroadcastReceiver {

    public static final String ALARM_CHAN_ID =
            "edu.wgu.c196.andrewdaiza.utilities.notification.ALARM_CHAN_ID";


    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString("title");
        String message = bundle.getString("message");
        int key = bundle.getInt("id");
        String Id = bundle.getString(ALARM_CHAN_ID);
        CreateNotification notificationHelper = new CreateNotification(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(title, message, Id);
        notificationHelper.getNotificationManager().notify(key, nb.build());
    }


}
