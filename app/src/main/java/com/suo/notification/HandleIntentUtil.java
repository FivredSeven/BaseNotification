package com.suo.notification;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by wuhongqi on 18/1/2.
 */

public class HandleIntentUtil {
    //    private void handleIntent(Intent intent) {
//        if (intent == null) {
//            return;
//        }
//        if (TextUtils.equals(intent.getStringExtra(NotificationHelper.INTENT_NOTIFICATION_CLICK), NotificationHelper.TAG)) {
//            String clickClass = intent.getStringExtra(NotificationHelper.INTENT_NOTIFICATION_CLICK_CLASS);
//            String tag = intent.getStringExtra(NotificationHelper.INTENT_NOTIFICATION_TAG);
//            int id = intent.getIntExtra(NotificationHelper.INTENT_NOTIFICATION_ID, -1);
//            Log.e("suo", "tag:" + tag);
//            Log.e("suo", "id:" + id);
//            if (TextUtils.isEmpty(clickClass)) {
//                return;
//            }
//            Class cls = null;
//            try {
//                cls = Class.forName(clickClass);
//                BaseNotificationClickListener listener = (BaseNotificationClickListener) cls.newInstance();
//                if (listener != null) {
//                    listener.onClick(MainActivity.this, intent.getStringExtra(NotificationHelper.INTENT_NOTIFICATION_CONTENT));
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    Intent intent = new Intent(MainActivity.this, NotificationBroadcastReceiver.class);
//    intent.setAction(NotificationBroadcastReceiver.REPLY_ACTION);
//    intent.putExtra(NotificationBroadcastReceiver.KEY_NOTIFICATION_ID, 1 + count);
//    intent.putExtra(NotificationBroadcastReceiver.KEY_NOTIFICATION_TAG, "btn" + count);
//    intent.putExtra(NotificationBroadcastReceiver.KEY_MESSAGE_ID, 1 + count);
//    builder.setReplyMode("suo", "NBA",
//            new BaseNotificationBuilder.Message("今晚去吃什么", System.currentTimeMillis(), "sunny"),
//            "回复", NotificationBroadcastReceiver.KEY_REPLY, intent);

    public static void handleIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        String tag = intent.getStringExtra("wuhq_tag");
        if (!TextUtils.isEmpty(tag)) {
            Toast.makeText(BaseApplication.getContext(), "delete:"+tag, Toast.LENGTH_SHORT).show();
        }
    }
}
