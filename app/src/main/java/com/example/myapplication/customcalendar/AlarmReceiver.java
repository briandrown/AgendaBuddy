package com.example.myapplication.customcalendar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.R;

/**
 * Part of the open source code
 *
 * Something to do with Alarm/Notification of an event
 *
 * ask me (Brian) for more info and I'll try to explain. I do not fully understand either
 * If your wanna change or have change (i.e commit to gitHub) please tell me so I know (or I guess tell the group as well)
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String event = intent.getStringExtra("event");
        String time = intent.getStringExtra("time");
        int notId = intent.getIntExtra("id", 0);
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "channel_id";
        CharSequence name = "channel_name";
        String description = "description";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, name, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentTitle(event)
                .setContentText(time)
                .setDeleteIntent(pendingIntent)
                .setGroup("Group_calendar_view")
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(notId, notification);
    }
}
