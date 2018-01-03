package com.suo.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.app.RemoteInput;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.List;

/**
 * Created by wuhongqi on 17/12/25.
 */

public class BaseNotificationBuilder{

    private String mTag;
    private int mId;
    private String mContent;
    private Context mContext;
    private NotificationCompat.Builder mBuilder;
    private PendingIntent mPendingIntent;
    private BaseNotificationClickListener mClickListener;
    private static final int REQUESTCODE = 1;
    private int mRequestCode = REQUESTCODE;

    /**
     * @param context
     * @inheritDoc
     */
    public BaseNotificationBuilder(Context context) {
        mContext = context;
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setAutoCancel(true);
        mRequestCode++;
    }

    public Notification build() {
        return mBuilder.build();
    }



    /********************************  required parameters  ********************************/
    public BaseNotificationBuilder setTitle(String title) {
        mBuilder.setContentTitle(title);
        return this;
    }

    public BaseNotificationBuilder setContent(String content) {
        mBuilder.setContentText(content);
        mContent = content;
        return this;
    }

    public BaseNotificationBuilder setSmallIcon(@DrawableRes int resource) {
        mBuilder.setSmallIcon(resource);
        return this;
    }

    /**
     * @param tag the string identifier for a notification. Can be {@code null}.
     * @return
     */
    public BaseNotificationBuilder setTag(String tag) {
        mTag = tag;
        return this;
    }

    /**
     * @param id the ID of the notification. The pair (tag, id) must be unique within your app.
     * @return
     */
    public BaseNotificationBuilder setId(int id) {
        mId = id;
        return this;
    }

    /**
     * 替换掉下面setOnClickNotificationListener方法里构造的intent
     * 调用方可灵活使用通知的点击数据
     * @param intent
     * @return
     */
    public BaseNotificationBuilder setClickIntent(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, mRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        setContentIntent(pendingIntent);
        return this;
    }

    /**
     *
     * @param clickClass
     * @param pendingClass 指定跳转到的Activity
     * @return
     */
    public BaseNotificationBuilder setOnClickNotificationListener(Class<? extends BaseNotificationClickListener> clickClass, Class<?> pendingClass) {
        Intent intent = new Intent(mContext, pendingClass);
        intent.putExtra(NotificationHelper.INTENT_NOTIFICATION_CLICK_CLASS, clickClass.getName());
        intent.putExtra(NotificationHelper.INTENT_NOTIFICATION_TAG, mTag);
        intent.putExtra(NotificationHelper.INTENT_NOTIFICATION_ID, mId);
        intent.putExtra(NotificationHelper.INTENT_NOTIFICATION_CONTENT, mContent);
        intent.putExtra(NotificationHelper.INTENT_NOTIFICATION_CLICK, NotificationHelper.TAG);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, mRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        setContentIntent(pendingIntent);
        return this;
    }

    private BaseNotificationBuilder setContentIntent(PendingIntent pendingIntent) {
        mPendingIntent = pendingIntent;
        mBuilder.setContentIntent(mPendingIntent);
        return this;
    }

    public String getTag() {
        return mTag;
    }

    public int getId() {
        return mId;
    }

    /**
     * 基本上此Builder类包含了大部分通知所使用的场景
     * 如果还有需要扩展的属性,可谨慎使用此方法,获取到原始builder
     * @return
     */
    public NotificationCompat.Builder getBuilder() {
        return mBuilder;
    }

    /********************************  Not required parameters  ********************************/

    /**
     * Set the text that is displayed in the status bar when the notification first
     * arrives.
     * 这个是显示在状态栏的内容,如果还想自定义view可以使用带remoteViews参数的方法
     * @param ticker
     * @return
     */
    public BaseNotificationBuilder setTicker(String ticker) {
        mBuilder.setTicker(ticker);
        return this;
    }

    public BaseNotificationBuilder setTicker(String ticker, RemoteViews view) {
        mBuilder.setTicker(ticker, view);
        return this;
    }

    /**
     * Set the third line of text in the platform notification template.
     * Don't use if you're also using {@link #setProgress(int, int, boolean)};
     * they occupy the same location in the standard template.
     * <br>
     * If the platform does not provide large-format notifications, this method has no effect.
     * The third line of text only appears in expanded view.
     * <br>
     * 解释一下 这个subText可以理解为辅助内容,首先不能和带进度条的通知里同时使用
     * 需要使用的话必须是支持多行文本内容的通知,不然的话设置了也没有任何影响
     * @param subText
     * @return
     */
    public BaseNotificationBuilder setSubText(String subText) {
        mBuilder.setSubText(subText);
        return this;
    }

    /**
     * 这个color 目前在7.0版本上发现才生效。
     * small icon、app name、扩展按钮等位置会有颜色变化
     * 通知内容颜色不会变化
     * @param color
     * @return
     */
    public BaseNotificationBuilder setColor(int color) {
        mBuilder.setColor(color);
        return this;
    }

    /**
     * 这个number数字显示在通知右下角 颜色大小位置其实都不可控,随系统变化
     * 目前发现在7.0的系统上没有效果
     * 5.0、6.0有效
     * 可能适用场景有同一类型消息发送了多条,比如同一用户给你发了多条聊天消息,这时候只显示一个通知,旁边再显示一个数量
     *
     * @param number
     * @return
     */
    public BaseNotificationBuilder setNumber(int number) {
        mBuilder.setNumber(number);
        return this;
    }

    /**
     * 设置通知显示的时间
     * 这个可用性不大,通常通知的时间我们希望随系统显示,而不是自己主动设置
     *
     * @param time
     * @return
     */
    public BaseNotificationBuilder setWhen(long time) {
        mBuilder.setWhen(time);
        return this;
    }

    /**
     * 显示通知的时候 设置是否需要显示时间 默认是显示
     *
     * 这个可用性好像也不大。
     *
     * @param showWhen
     * @return
     */
    public BaseNotificationBuilder setShowWhen(boolean showWhen) {
        mBuilder.setShowWhen(showWhen);
        return this;
    }

    /**
     * Set the text that is displayed in the status bar when the notification first
     * arrives.
     * 这个大图也是随系统,机型都不一样的。目前发现7.0会显示在通知的右侧
     *
     * @param icon  如果是网络url，可以先加载出来，再传进来。 bitmap尽量小，防止oom。
     * @return
     */
    public BaseNotificationBuilder setLargeIcon(Bitmap icon) {
        mBuilder.setLargeIcon(icon);
        return this;
    }

    /**
     * 用uri来播放通知声音
     * Ex:
     * Uri.fromFile(new File("/system/media/audio/ringtones/Orange.ogg"))
     * @param uri
     * @return
     */
    public BaseNotificationBuilder setSound(Uri uri) {
        mBuilder.setSound(uri);
        return this;
    }

    public BaseNotificationBuilder setSound(Uri uri, int streamType) {
        mBuilder.setSound(uri, streamType);
        return this;
    }

    /**
     * long[] vibrate = {0, 1000, 1000, 1000};
     * 静止时长，震动时长，静止时长，震动时长，类推
     * @param pattern
     * @return
     */
    public BaseNotificationBuilder setVibrate(long[] pattern) {
        mBuilder.setVibrate(pattern);
        return this;
    }

    /**
     * Set the argb value that you would like the LED on the device to blnk, as well as the
     * rate.  The rate is specified in terms of the number of milliseconds to be on
     * and then the number of milliseconds to be off.
     * 呼吸灯
     * .setLights(Color.GREEN, 2000, 2000);
     * @param argb
     * @param onMs
     * @param offMs
     * @return
     */
    public BaseNotificationBuilder setLights(@ColorInt int argb, int onMs, int offMs) {
        mBuilder.setLights(argb, onMs, offMs);
        return this;
    }

    /**
     * 设置声音、震动、呼吸灯
     * @return
     */
    public BaseNotificationBuilder setSoundVibrateLights(boolean sound, boolean vibrate, boolean lights) {
//        mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        int flags = 0;
        if (sound) {
            flags |= Notification.DEFAULT_SOUND;
        }
        if (vibrate) {
            flags |= Notification.DEFAULT_VIBRATE;
        }
        if (lights) {
            flags |= Notification.DEFAULT_LIGHTS;
        }

        mBuilder.setDefaults(flags);
        return this;
    }

    /**
     *
     * @param max
     * @param progress
     * @param indeterminate 模糊的进度     设置为true 那进度条是一直在规律变化
     *                                   设置为false 那就是需要用户主动刷新进度条 持续发通知
     * @return
     */
    public BaseNotificationBuilder setProgress(int max, int progress, boolean indeterminate) {
        mBuilder.setProgress(max, progress, indeterminate);
        return this;
    }


    /**
     * ongoing notifications do not have an 'X' close button, and are not affected
     *   by the "Clear all" button.
     *
     * 处于进行中的通知 有些系统在移除时会给提示(华为) 有些则移除不了(小米/三星)
     * 常驻通知,一般适用于音乐类app而已在通知栏控制歌曲播放暂停等。
     *
     * @param onGoing
     * @return
     */
    public BaseNotificationBuilder setOnGoing(boolean onGoing) {
        mBuilder.setOngoing(onGoing);
        return this;
    }

    /**
     * 目前可选的优先级如下
     * PRIORITY_DEFAULT = 0
     * PRIORITY_LOW = -1
     * PRIORITY_MIN = -2
     * PRIORITY_HIGH = 1
     * PRIORITY_MAX = 2
     *
     * 通知会根据优先级来显示位置顺序,有些系统还是会依据时间+优先级
     *
     * 这个设置优先级其实很微妙的,app都希望自己的通知能放在最上面,但是不同app谁的在上面,谁的在下面,不是那么好控制的。
     * 同类app为了抢占更高优先级展示,值得深究不同厂商系统的展示逻辑,这里就暂不研究。
     *
     * @param priority
     * @return
     */
    public BaseNotificationBuilder setPriority(int priority) {
        mBuilder.setPriority(priority);
        return this;
    }

    /**
     * An intent to launch instead of posting the notification to the status bar.
     * Only for use with extremely high-priority notifications demanding the user's
     * <strong>immediate</strong> attention, such as an incoming phone call or
     * alarm clock that the user has explicitly set to a particular time.
     * If this facility is used for something else, please give the user an option
     * to turn it off and use a normal notification, as this can be extremely
     * disruptive.
     *
     * <p>
     * On some platforms, the system UI may choose to display a heads-up notification,
     * instead of launching this intent, while the user is using the device.
     * </p>
     *
     * 应该可以理解为，当这个通知非常重要的时候，需要主动打扰或者说影响到用户的时候，可以尝试使用这个高优先级的intent
     * 例如来电提醒，新消息提醒等
     *
     * 也可以称呼它为横幅通知，从顶部掉落下来的一个通知样式。
     * 不过highPriority需要传true才行。
     *
     * @param intent The pending intent to launch.
     * @param highPriority Passing true will cause this notification to be sent
     *          even if other notifications are suppressed.
     */
    public BaseNotificationBuilder setFullScreenIntent(PendingIntent intent, boolean highPriority) {
        mBuilder.setFullScreenIntent(intent, highPriority);
        return this;
    }



    public BaseNotificationBuilder setGroup(String groupKey) {
        mBuilder.setGroup(groupKey);
        return this;
    }

    /**
     * Android N 7.0新特性
     * 可以将同类的消息进行收起和展开操作。
     * 需要和setGroup配合使用   同类消息通过groupKey来匹配、
     *
     * 有一点要说明的是在使用的时候可能会遇到一点小问题
     * 网上有人也遇到了https://stackoverflow.com/questions/38097442/notification-with-setgroupsummarytrue-is-not-visible-in-android-n
     * 意思就是如果你给第一条通知设置了true后面两条设置了false 通知栏只会显示后两条
     * 原因我猜想是这样的 设置true相当于是一个组长,但是组长不参与显示group消息,
     * 所以你要想有group的合并消息,那么第一条设置true紧接着再发一条false,并且显示你真正想显示的第一条内容
     *
     * 还有一点需要注意的是，目前在7.0中，即便不设置group属性，系统也不允许同一app显示超过三条的通知展示，超过三条会
     * 自动放置在一起折叠，所以setGroup显得有点多余了。
     *
     *
     * @param isGroupSummary
     * @return
     */
    public BaseNotificationBuilder setGroupSummary(boolean isGroupSummary) {
        mBuilder.setGroupSummary(isGroupSummary);
        return this;
    }


    /**
     * Android N 7.0新特性
     *
     * 值得注意的是给通知设置style，在7.0上都是展开后显示的内容，展开前依然是之前setTitle和setContent.
     * 目前发送消息后是默认展开最新一条消息。
     *
     * 多种样式的通知 InboxStyle 多行文本
     * @param lines
     * @return
     */
    public BaseNotificationBuilder setInboxStyle(List<String> lines) {
        if (lines != null && lines.size() > 0) {
            NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
            for (String str : lines) {
                style.addLine(str);
            }
            mBuilder.setStyle(style);
        }
        return this;
    }

    /**
     * Android N 7.0新特性
     * 可收缩文本的style
     * 默认展示一行的文本,用户可主动展开更多文本
     *
     * setContent() 设置的是通知没展开时显示的内容
     * @param summary 显示在app名称与时间中间的文案
     * @param bigTitle 标题
     * @param bigText 用户展开后显示的全部内容
     *
     * @return
     */
    public BaseNotificationBuilder setBigTextStyle(String summary, String bigTitle, String bigText) {
        mBuilder.setStyle(new NotificationCompat.BigTextStyle().setSummaryText(summary)
                .setBigContentTitle(bigTitle)
                .bigText(bigText));
        return this;
    }

    /**
     * Android N 7.0新特性
     * 可收缩展示大图片的style
     * 默认展示一行文本,用户可主动展开,显示额外的文本+大图片
     *
     * setContent() 设置的是通知没展开时显示的内容
     * @param summary 通知展开后显示的文本
     * @param bigTitle 通知展开前的标题
     * @param bitmap 通知展开后显示的大图片
     * @return
     */
    public BaseNotificationBuilder setBigPictureStyle(String summary, String bigTitle, Bitmap bitmap) {
        mBuilder.setStyle(new NotificationCompat.BigPictureStyle().setSummaryText(summary)
                .setBigContentTitle(bigTitle)
                .bigPicture(bitmap));
        return this;
    }

    /**
     * Android N 7.0新特性
     * 媒体通知
     *
     * NotificationCompat.Action
     * addAction(int icon, CharSequence title, PendingIntent intent)
     *
     * @param actions
     * @param context
     * @return
     */
    public BaseNotificationBuilder setMediaStyle(List<NotificationCompat.Action> actions, Context context) {
        if (actions != null && actions.size() > 0) {
            for (NotificationCompat.Action action : actions) {
                mBuilder.addAction(action);
            }
            mBuilder.setStyle(new NotificationCompat.MediaStyle().setMediaSession(
                    new MediaSessionCompat(context, "MediaSession",
                            new ComponentName(context, Intent.ACTION_MEDIA_BUTTON), null)
                            .getSessionToken()).setShowCancelButton(true).setShowActionsInCompactView(2));
        }
        return this;
    }

    /**
     *
     * builder.setStyle(new NotificationCompat.MessagingStyle("Me")
     * .setConversationTitle("Team lunch")
     * .addMessage("Hi", System.currentTimeMillis(), null) // Pass in null for user.
     * .addMessage("What's up?", System.currentTimeMillis(), "Coworker")
     * .addMessage("Do you want to go see a movie tonight?", System.currentTimeMillis(), null)
     * .addMessage("that sounds great", System.currentTimeMillis(), "Coworker"));
     *
     * 这个MessageStyle其实最适用的场景应该是聊天应用里的群聊，群聊中多个人发了多条消息，可以利用通知把消息组装在一起展示。
     *
     * @param userDisplayName 自己名称
     * @param conversationTitle 对话标题
     * @param messages 具体对话
     * @return
     */
    public BaseNotificationBuilder setMessageStyle(String userDisplayName, String conversationTitle,
                                                   List<Message> messages) {
        NotificationCompat.MessagingStyle messageStyle = new NotificationCompat.MessagingStyle(userDisplayName)
                .setConversationTitle(conversationTitle);
        for (Message message : messages) {
            messageStyle.addMessage(message.mText, message.mTimestamp, message.mSender);
        }
        mBuilder.setStyle(messageStyle);
        return this;
    }

    public static final class Message {
        private final CharSequence mText;
        private final long mTimestamp;
        private final CharSequence mSender;

        /**
         *
         * @param text 文案内容
         * @param timestamp 时间
         * @param sender 发送者名称
         */
        public Message(CharSequence text, long timestamp, CharSequence sender){
            mText = text;
            mTimestamp = timestamp;
            mSender = sender;
        }
    }

    /**
     * Android 7.0 N 新特性
     *
     * @param userDisplayName 自己名称
     * @param conversationTitle 对话标题
     * @param message 对话内容
     * @param replyLabel 一般文案为"回复",可以自己定制
     * @param replyKey 回复的KEY  通过这个KEY才能准确定位回复内容对应的通知
     * @param intent 这个intent可以是一个activity 也可以是一个broadcastReceiver
     *        如果要
     *               可以参考 NotificationBroadcasrReceiver
     *
     * @return
     */
    public BaseNotificationBuilder setReplyMode(String userDisplayName, String conversationTitle,
                                                Message message, String replyLabel, String replyKey,
                                                Intent intent) {
        mBuilder.setStyle(new NotificationCompat.MessagingStyle(userDisplayName)
                .setConversationTitle(conversationTitle)
                .addMessage(message.mText, message.mTimestamp, message.mSender));
        RemoteInput remoteInput = new RemoteInput.Builder(replyKey)
                .setLabel(replyLabel)
                .build();

        NotificationCompat.Action action = new NotificationCompat.Action.Builder(0, replyLabel, getReplyPendingIntent(intent))
                .addRemoteInput(remoteInput)
                .setAllowGeneratedReplies(true)
                .build();
        mBuilder.addAction(action);
        return this;
    }

    /**
     *  Intent intent;
     *  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
     *      intent = new Intent(this, NotificationBroadcastReceiver.class);
     *      intent.setAction(NotificationBroadcastReceiver.REPLY_ACTION);
     *      intent.putExtra(NotificationBroadcastReceiver.KEY_NOTIFICATION_ID, NOTIFICATION_ID);
     *      intent.putExtra(NotificationBroadcastReceiver.KEY_NOTIFICATION_TAG, mNotificationTag);
     *      intent.putExtra(NotificationBroadcastReceiver.KEY_MESSAGE_ID, number);
     *      return PendingIntent.getBroadcast(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
     *  } else {
     *      // start your activity for Android M and below
     *      intent = new Intent(this, CustomNotificationActivity.class);
     *      return PendingIntent.getActivity(this, 100, intent,
     *      PendingIntent.FLAG_UPDATE_CURRENT);
     *  }
     * @param intent
     * @return
     */
    public PendingIntent getReplyPendingIntent(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return PendingIntent.getBroadcast(BaseApplication.getContext(), 100, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            // start your activity for Android M and below
            return PendingIntent.getActivity(BaseApplication.getContext(), 100, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    /**
     * PendingIntent.getActivity(mContext, mRequestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
     * requestCode is used with last flag parameters
     * Example:
     * if two pendingIntent with same requestCode , and the last flag is FLAG_UPDATE_CURRENT, then the
     * first pendingIntent carry data will be replaced by the second pendingIntent.
     * Mostly we want different notification has different response, so we should set different requestCode
     *
     * requestCode与最后一个flag参数配合使用
     * 例如:
     * 如果两个pendingIntent有着相同的requestCode,而且最后的flag都是 FLAG_UPDATE_CURRENT,
     * 那前一个pendingIntent所携带的数据将会被最后一个pendingIntent所替换掉
     * 通常我们不同的通知需要不同的响应操作,所以我们需要设置不同的requestCode
     *
     * @param requestCode  sometimes we used (int)System.currentTimeMillis();
     *                     有的时候,我们用当前时间戳来设置requestCode
     * @return
     */
    public BaseNotificationBuilder setRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    /**
     * usually we want click notification then it disappeared
     * if you do not set it true , the notification will not disappear even though you click it
     * 通常我们希望点击通知后,通知会自动消失
     * 如果你不设置它为true,通知将不会自动消失,尽管你点击了它。
     * @param auto
     * @return
     */
    public BaseNotificationBuilder setAutoCancel(boolean auto) {
        mBuilder.setAutoCancel(auto);
        return this;
    }

    /**
     * Supply a {@link PendingIntent} to send when the notification is cleared by the user
     * directly from the notification panel.  For example, this intent is sent when the user
     * clicks the "Clear all" button, or the individual "X" buttons on notifications.  This
     * intent is not sent when the application calls
     *
     * 当用户清除通知的时候,能收到这个intent
     * @param intent
     * @return
     */
    public BaseNotificationBuilder setOnNotificationCancelListener(Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setDeleteIntent(pendingIntent);
        return this;
    }

    /**
     * Sets {@link Notification#visibility}.
     *
     * Notification的显示等级
     * android5.0加入了一种新的模式Notification的显示等级，共有三种：
     *
     * VISIBILITY_PUBLIC： 任何情况都会显示通知
     * VISIBILITY_PRIVATE ： 只有在没有锁屏时会显示通知
     * VISIBILITY_SECRET ： 在pin、password等安全锁和没有锁屏的情况下才能够显示
     *
     * @param visibility One of {@link Notification#VISIBILITY_PRIVATE} (the default),
     *                   {@link Notification#VISIBILITY_PUBLIC}, or
     *                   {@link Notification#VISIBILITY_SECRET}.
     */
    public BaseNotificationBuilder setVisibility (int visibility) {
        mBuilder.setVisibility(visibility);
        return this;
    }
}
