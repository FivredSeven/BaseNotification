package com.suo.notification;

import android.app.Notification;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;

/**
 * Created by wuhongqi on 17/12/25.
 */

public class NotificationHelper {

    private volatile static NotificationHelper mInstance;

    private NotificationManagerCompat mNotificationManager;
    private Context mContext;

    public static final String TAG = "NotificationHelper";
    public static final String INTENT_NOTIFICATION_CLICK = "intent_notification_click";
    public static final String INTENT_NOTIFICATION_CLICK_CLASS = "intent_notification_click_class";
    public static final String INTENT_NOTIFICATION_TAG = "intent_notification_tag";
    public static final String INTENT_NOTIFICATION_ID = "intent_notification_id";
    public static final String INTENT_NOTIFICATION_REQUESTCODE = "intent_notification_requestcode";
    public static final String INTENT_NOTIFICATION_CONTENT = "intent_notification_content";

    public NotificationHelper(Context context) {
        mContext = context;
        initNotificationManager();
    }

    private void initNotificationManager() {
        mNotificationManager = NotificationManagerCompat.from(mContext);
    }

    public static NotificationHelper getInstance() {
        if (mInstance == null) {
            synchronized (NotificationHelper.class) {
                if (mInstance == null) {
                    mInstance = new NotificationHelper(BaseApplication.getContext());
                }
            }
        }

        return mInstance;
    }

    /**
     * 发通知。
     * @param builder 详见 BaseNotificationBuilder
     */
    public void showNotification(@NonNull BaseNotificationBuilder builder) {
        if (mNotificationManager == null) {
            initNotificationManager();
        }

        if (builder == null) {
            BaseAssert.fail("builder can not be null");
        }
        String tag = builder.getTag();
        int id = builder.getId();
        Notification notification = builder.build();
        if (TextUtils.isEmpty(tag)) {
            BaseAssert.fail("tag can not be empty");
        }
        if (notification == null) {
            BaseAssert.fail("notification can not be null");
        }
        if (notification.icon <= 0) {
            BaseAssert.fail("small icon can not be null");
        }
        mNotificationManager.notify(tag, id, notification);
    }

    /**
     * 向系统获取是否获得通知栏权限
     *
     * @return
     */
    public boolean areNotificationsEnabled() {
        return mNotificationManager.areNotificationsEnabled();
    }

    /**
     * 清除单个通知
     * @param id
     */
    public void clearNotification(int id) {
        mNotificationManager.cancel(id);
    }

    /**
     * 清除单个通知
     * @param tag
     * @param id
     */
    public void clearNotification(String tag, int id) {
        mNotificationManager.cancel(tag, id);
    }

    /**
     * 清除所有通知
     */
    public void clearAllNotification() {
        mNotificationManager.cancelAll();
    }

}
