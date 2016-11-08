package com.userchecking.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.userchecking.R;
import com.userchecking.activity.MainActivity;

/**
 * Created by GURUKRUPA on 10/20/2016.
 */

public class ProximityIntentReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1000;


    @Override
    public void onReceive(Context context, Intent intent) {

        String key = LocationManager.KEY_PROXIMITY_ENTERING;

        Boolean entering = intent.getBooleanExtra(key, false);

        if (entering) {
            Log.d(getClass().getSimpleName(), "entering");
            generateNotification(context,"Proximity Alert!, You are entering near your point of interest.");
        }
        else {
            Log.d(getClass().getSimpleName(), "exiting");
            generateNotification(context,"Proximity Alert!, You are exiting near your point of interest.");
        }
    }


    private static void generateNotification(Context mContext, String message) {

        long when = System.currentTimeMillis();

        String title = mContext.getString(R.string.app_name);

        int icon = R.mipmap.ic_launcher;

        int mNotificationId = NOTIFICATION_ID;

        Intent notificationIntent = new Intent(mContext, MainActivity.class);
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );
        long[] vibrate = { 0, 100, 200, 300 };
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);

        Notification notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(when)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setVibrate(vibrate)
                .setContentText(message).build();

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);

    }

    private Notification createNotification() {
        Notification notification = new Notification();

        notification.icon = R.mipmap.ic_launcher;
        notification.when = System.currentTimeMillis();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        notification.ledARGB = Color.WHITE;
        notification.ledOnMS = 1500;
        notification.ledOffMS = 1500;

        return notification;
    }


}
