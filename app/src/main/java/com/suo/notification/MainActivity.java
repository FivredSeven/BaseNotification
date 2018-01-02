package com.suo.notification;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                BaseNotificationBuilder builder = new BaseNotificationBuilder(MainActivity.this);
                builder.setTag("btn" + count)
                        .setId(1 + count)
                        .setTitle("普通通知")
                        .setContent("内容不多。就随便写两句" + count)
                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setOnClickNotificationListener(NormalNotificationClickListener.class, MainActivity.class);
                NotificationHelper.getInstance().showNotification(builder);
            }
        });

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        if (TextUtils.equals(intent.getStringExtra(NotificationHelper.INTENT_NOTIFICATION_CLICK), NotificationHelper.TAG)) {
            String clickClass = intent.getStringExtra(NotificationHelper.INTENT_NOTIFICATION_CLICK_CLASS);
            String tag = intent.getStringExtra(NotificationHelper.INTENT_NOTIFICATION_TAG);
            int id = intent.getIntExtra(NotificationHelper.INTENT_NOTIFICATION_ID, -1);
            Log.e("suo", "tag:" + tag);
            Log.e("suo", "id:" + id);
            if (TextUtils.isEmpty(clickClass)) {
                return;
            }
            Class cls = null;
            try {
                cls = Class.forName(clickClass);
                BaseNotificationClickListener listener = (BaseNotificationClickListener) cls.newInstance();
                if (listener != null) {
                    listener.onClick(MainActivity.this, intent.getStringExtra(NotificationHelper.INTENT_NOTIFICATION_CONTENT));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

//    Intent intent = new Intent(MainActivity.this, NotificationBroadcastReceiver.class);
//    intent.setAction(NotificationBroadcastReceiver.REPLY_ACTION);
//    intent.putExtra(NotificationBroadcastReceiver.KEY_NOTIFICATION_ID, 1 + count);
//    intent.putExtra(NotificationBroadcastReceiver.KEY_NOTIFICATION_TAG, "btn" + count);
//    intent.putExtra(NotificationBroadcastReceiver.KEY_MESSAGE_ID, 1 + count);
//    builder.setReplyMode("suo", "NBA",
//            new BaseNotificationBuilder.Message("今晚去吃什么", System.currentTimeMillis(), "sunny"),
//            "回复", NotificationBroadcastReceiver.KEY_REPLY, intent);
}
