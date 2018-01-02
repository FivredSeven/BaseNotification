package com.suo.notification;

import android.app.Application;
import android.content.Context;

/**
 * Created by wuhongqi on 17/12/25.
 */

public class BaseApplication extends Application {

    private static BaseApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
