package com.suo.notification;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * 自定义布局的通知
 *
 * 说明一下，通过构造自定义布局 跨进程传递给系统通知栏进程 使用到的关键是RemoteViews
 *
 * 随着通知栏系统样式越来越丰富，自定义布局显得有些.. （尤其是当你要考虑各种各样厂商系统适配问题的时候）
 *
 *
 * Created by wuhongqi on 17/12/30.
 */

public class CustomNotificationBuilder extends BaseNotificationBuilder {

    private Context mContext;
    private RemoteViews mView;

    /**
     * @param context
     * @inheritDoc
     */
    public CustomNotificationBuilder(Context context, @IdRes int layout) {
        super(context);
        mContext = context;
        mView = new RemoteViews(context.getPackageName(), layout);
    }

    /**
     * RemoteViews有个特点就是给布局设置样式或者监听都需要传递资源id 因为跨进程调用
     * @param viewId
     * @param text
     * @return
     */
    public CustomNotificationBuilder setTextViewText(@IdRes int viewId, String text) {
        mView.setTextViewText(viewId, text);
        return this;
    }

    public CustomNotificationBuilder setImageViewResource(@IdRes int viewId, @DrawableRes int drawable) {
        mView.setImageViewResource(viewId, drawable);
        return this;
    }

    public CustomNotificationBuilder setTextColor(@IdRes int viewId, @ColorInt int color) {
        mView.setTextColor(viewId, color);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public CustomNotificationBuilder setTextViewTextSize(@IdRes int viewId, int units, float size) {
        mView.setTextViewTextSize(viewId, units, size);
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public CustomNotificationBuilder setTextViewCompoundDrawables(int viewId, int left, int top, int right, int bottom) {
        mView.setTextViewCompoundDrawables(viewId, left, top, right, bottom);
        return this;
    }

    public CustomNotificationBuilder setOnClickPendingIntent(int viewId, PendingIntent pendingIntent) {
        mView.setOnClickPendingIntent(viewId, pendingIntent);
        return this;
    }

    /**
     * 获取系统的通知栏View的一些属性  类似字体颜色大小等等
     *
     * 原理就是先构造一个通知，但不真正发到通知栏，仅仅使用它的apply方法
     * 循环遍历通知的所有view，获取到每个对应的view属性。
     */
    public void getSystemText() {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
            builder.setContentTitle("NOTIFICATION_TITLE")
                    .setContentText("NOTIFICATION_TEXT")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();

            LinearLayout group = new LinearLayout(mContext);
            RemoteViews tempView = builder.getNotification().contentView;
            ViewGroup event = (ViewGroup) tempView.apply(mContext, group);
            recurseGroup(event);
            group.removeAllViews();
        } catch (Exception e) {
            // 当无法获取的时候 给定一些默认值
        }
    }

    private boolean recurseGroup(ViewGroup gp) {
        for (int i = 0; i < gp.getChildCount(); i++) {
            View v = gp.getChildAt(i);
            if (v instanceof TextView) {
                final TextView text = (TextView) v;
                final String szText = text.getText().toString();
                if ("NOTIFICATION_TITLE".equals(szText)) {
                    int titleColor = text.getTextColors().getDefaultColor();
                    float titleSize = text.getTextSize();
                }
                if ("NOTIFICATION_TEXT".equals(szText)) {
                    int contentColor = text.getTextColors().getDefaultColor();
                    float contentSize = text.getTextSize();
                }
            }
            if (v instanceof ImageView) {
                final ImageView image = (ImageView) v;
                if (image.getBackground().getConstantState().equals(mContext.getResources().getDrawable(R.mipmap.ic_launcher))) {
                    int iconWidth = image.getWidth();
                    int iconHeight = image.getHeight();
                }
            }
            if (v instanceof ViewGroup) {// 如果是ViewGroup 遍历搜索
                recurseGroup((ViewGroup) gp.getChildAt(i));
            }
        }
        return false;
    }

}
