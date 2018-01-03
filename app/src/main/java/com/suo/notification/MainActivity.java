package com.suo.notification;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private int count;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = $(R.id.btn_send1);
        button2 = $(R.id.btn_send2);
        button3 = $(R.id.btn_send3);
        button4 = $(R.id.btn_send4);
        button5 = $(R.id.btn_send5);
        button6 = $(R.id.btn_send6);
        button7 = $(R.id.btn_send7);
        button8 = $(R.id.btn_send8);
        $K(this, button1);
        $K(this, button2);
        $K(this, button3);
        $K(this, button4);
        $K(this, button5);
        $K(this, button6);
        $K(this, button7);
        $K(this, button8);

        HandleIntentUtil.handleIntent(getIntent());
    }

    protected <T extends View> T $(@IdRes int resId) {
        return (T) findViewById(resId);
    }

    protected  void $K(View.OnClickListener listener, View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.setOnClickListener(listener);
                }
            }
        }
    }

    /**
     * demo为了更清晰一些  每个id对应的调用方法都单独写。
     * @param v
     */
    @Override
    public void onClick(View v) {
        count++;
        BaseNotificationBuilder builder = new BaseNotificationBuilder(MainActivity.this);
        switch (v.getId()) {
            case R.id.btn_send1:
                builder.setTag("tag" + count)
                        .setId(count)
                        .setTitle("我是一个普通通知的标题")
                        .setContent("我是一个普通通知的内容简介" + count)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setClickIntent(new Intent(MainActivity.this, MainActivity.class));
                NotificationHelper.getInstance().showNotification(builder);
                break;
            case R.id.btn_send2:
                Intent delIntent = new Intent(MainActivity.this, MainActivity.class);
                delIntent.putExtra("wuhq_tag", "tag" + count);
                delIntent.putExtra("wuhq_id", 1);
                builder = new BaseNotificationBuilder(MainActivity.this);
                builder.setTag("tag" + count)
                        .setId(count)
                        .setTitle("我是能监听自己被清除的通知标题")
                        .setContent("我是能监听自己被清除的通知内容简介" + count)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        // 设置清除消息监听
                        .setOnNotificationCancelListener(delIntent)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setClickIntent(new Intent(MainActivity.this, MainActivity.class));

                NotificationHelper.getInstance().showNotification(builder);
                break;
            case R.id.btn_send3:
                long[] vibrate = {0, 1000, 1000, 1000};
                builder = new BaseNotificationBuilder(MainActivity.this);
                builder.setTag("tag" + count)
                        .setId(count)
                        .setTitle("我是能自定义声音/震动/呼吸灯的通知标题")
                        .setContent("我是能自定义声音/震动/呼吸灯的通知内容简介" + count)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //声音
                        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Orange.ogg")))
                        //震动
                        .setVibrate(vibrate)
                        //呼吸灯
                        .setLights(Color.GREEN, 2000, 2000)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setClickIntent(new Intent(MainActivity.this, MainActivity.class));
                NotificationHelper.getInstance().showNotification(builder);
                break;
            case R.id.btn_send4:
                builder = new BaseNotificationBuilder(MainActivity.this);
                builder.setTag("tag" + count)
                        .setId(count)
                        .setTitle("我是能显示模糊进度条的通知标题")
                        .setContent("我是能显示模糊进度条的通知内容简介" + count)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //显示模糊进度条
                        .setProgress(1, 1, true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setClickIntent(new Intent(MainActivity.this, MainActivity.class));
                NotificationHelper.getInstance().showNotification(builder);
                break;
            case R.id.btn_send5:
                for (int i=0;i<3;i++) {
                    count++;
                    builder = new BaseNotificationBuilder(MainActivity.this);
                    builder.setTag("tag" + count)
                            .setId(count)
                            .setTitle("我是能展开收起的群组通知标题")
                            .setContent("我是能展开收起的群组通知内容简介" + count)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            //设置group消息
                            .setGroupSummary(i==0)
                            .setGroup("groupKey")
                            .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                            .setClickIntent(new Intent(MainActivity.this, MainActivity.class));
                    NotificationHelper.getInstance().showNotification(builder);
                }

                break;
            case R.id.btn_send6:
                builder = new BaseNotificationBuilder(MainActivity.this);
                builder.setTag("tag" + count)
                        .setId(count)
                        .setTitle("我是能显示大图片的通知标题")
                        .setContent("我是能显示大图片的通知内容简介" + count)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //显示大图片
                        .setBigPictureStyle("summary", "bigTitle", BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setClickIntent(new Intent(MainActivity.this, MainActivity.class));
                NotificationHelper.getInstance().showNotification(builder);
                break;
            case R.id.btn_send7:

                BaseNotificationBuilder.Message message1 = new BaseNotificationBuilder.Message("Hi", System.currentTimeMillis(), null);
                BaseNotificationBuilder.Message message2 = new BaseNotificationBuilder.Message("What's up?", System.currentTimeMillis(), "Coworker");
                BaseNotificationBuilder.Message message3 = new BaseNotificationBuilder.Message("Do you want to go see a movie tonight?", System.currentTimeMillis(), null);
                BaseNotificationBuilder.Message message4 = new BaseNotificationBuilder.Message("that sounds great", System.currentTimeMillis(), "Coworker");

                List<BaseNotificationBuilder.Message> messages = new ArrayList<>();
                messages.add(message1);
                messages.add(message2);
                messages.add(message3);
                messages.add(message4);
                builder = new BaseNotificationBuilder(MainActivity.this);
                builder.setTag("tag" + count)
                        .setId(count)
                        .setTitle("我是能显示多行文本的通知标题")
                        .setContent("我是能显示多行文本的通知内容简介" + count)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //显示多行文本
                        .setMessageStyle("Me", "title", messages)

                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setClickIntent(new Intent(MainActivity.this, MainActivity.class));
                NotificationHelper.getInstance().showNotification(builder);
                break;
            case R.id.btn_send8:
                builder = new BaseNotificationBuilder(MainActivity.this);
                builder.setTag("tag" + count)
                        .setId(count)
                        .setTitle("我是能直接在通知栏回复的通知标题")
                        .setContent("我是能直接在通知栏回复的通知内容简介" + count)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.notification_bigpic))
                        .setClickIntent(new Intent(MainActivity.this, MainActivity.class));
                Intent intent = new Intent(MainActivity.this, NotificationBroadcastReceiver.class);
                intent.setAction(NotificationBroadcastReceiver.REPLY_ACTION);
                intent.putExtra(NotificationBroadcastReceiver.KEY_NOTIFICATION_ID, 1 + count);
                intent.putExtra(NotificationBroadcastReceiver.KEY_NOTIFICATION_TAG, "btn" + count);
                intent.putExtra(NotificationBroadcastReceiver.KEY_MESSAGE_ID, 1 + count);
                builder.setReplyMode("suo", "dinner",
                        new BaseNotificationBuilder.Message("今晚去吃什么", System.currentTimeMillis(), "sunny"),
                        "回复", NotificationBroadcastReceiver.KEY_REPLY, intent);

                NotificationHelper.getInstance().showNotification(builder);
                break;
        }
    }
}
