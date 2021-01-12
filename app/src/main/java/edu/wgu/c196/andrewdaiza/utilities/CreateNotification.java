package edu.wgu.c196.andrewdaiza.utilities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import edu.wgu.c196.andrewdaiza.R;


public class CreateNotification extends ContextWrapper {


    public static final String START_DATE_ALERT_CHAN = "1";
    public static final String END_DATE_ALERT_CHAN = "2";


    NotificationManager notificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public CreateNotification(Context base) {
        super(base);
        notificationChannelMethod();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notificationChannelMethod() {
            NotificationChannel channel1 = new NotificationChannel(
                    START_DATE_ALERT_CHAN,
                    "Start Alert Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channel2 = new NotificationChannel(
                    END_DATE_ALERT_CHAN,
                    "End Alert Channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );

            getNotificationManager().createNotificationChannel(channel1);
            getNotificationManager().createNotificationChannel(channel2);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public NotificationManager getNotificationManager() {
        if (notificationManager == null) {
                notificationManager = getSystemService(NotificationManager.class);
        }
        return notificationManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message, String chanId) {
        return new NotificationCompat.Builder(getApplicationContext(), chanId)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setStyle(new NotificationCompat.BigTextStyle())
                ;
    }
}
