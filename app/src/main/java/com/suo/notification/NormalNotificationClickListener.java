package com.suo.notification;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wuhongqi on 17/12/26.
 */

public class NormalNotificationClickListener implements BaseNotificationClickListener {
    @Override
    public void onClick(Context context, String content) {
        Toast.makeText(context, "click : "+content, Toast.LENGTH_LONG).show();
    }
}
