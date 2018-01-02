package com.suo.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;

/**
 * Created by wuhongqi on 17/12/29.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    public static final String REPLY_ACTION = "reply_action";
    public static String KEY_NOTIFICATION_ID = "key_notification_id";
    public static String KEY_NOTIFICATION_TAG = "key_notification_tag";
    public static String KEY_MESSAGE_ID = "key_message_id";
    public static String KEY_REPLY = "reply";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (REPLY_ACTION.equals(intent.getAction())) {
            CharSequence message = getReplyMessage(intent);
            int messageId = intent.getIntExtra(KEY_MESSAGE_ID, 0);
            int notifyId = intent.getIntExtra(KEY_NOTIFICATION_ID, 0);
            String tag = intent.getStringExtra(KEY_NOTIFICATION_TAG);
//            Toast.makeText(context, "Message ID: " + messageId + "\nMessage: " + message,
//                    Toast.LENGTH_SHORT).show();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.mipmap.ic_launcher);
            builder.setContentTitle("我是自带回复功能的通知标题");
            builder.setContentText("Hello")
                    .setColor(Color.BLUE)
                    .setAutoCancel(true)//点击后即移除
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
            builder.setStyle(new NotificationCompat.MessagingStyle("Me")
                    .addMessage("Do you want to go see a movie tonight?", System.currentTimeMillis(), "kobe")
                    .addMessage(message, System.currentTimeMillis(), null));

            notificationManager.notify(tag, notifyId, builder.build());
        }

    }

    private CharSequence getReplyMessage(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(KEY_REPLY);
        }
        return null;
    }
}
