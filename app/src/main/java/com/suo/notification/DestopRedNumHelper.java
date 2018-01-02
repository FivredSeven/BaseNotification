package com.suo.notification;

import android.app.Notification;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

/**
 * 给桌面的程序右上角加上红点和数字
 *
 * 辅助
 */
public class DestopRedNumHelper {
    private static volatile DestopRedNumHelper helper;

    private DestopRedNumHelper() {
    }
    public static DestopRedNumHelper getInstance() {
        if (helper == null) {
            synchronized (DestopRedNumHelper.class) {
                helper = new DestopRedNumHelper();
            }
        }
        return helper;
    }


    public void showXiaoMiRedNum(int num) {
        try {
            if (!isUPMIUI6()) {
                Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
                localIntent.putExtra("android.intent.extra.update_application_component_name", "com.kugou.android/.app.splash.SplashActivity");
                String str;
                if (num > 0) {
                    str = String.valueOf(num);
                } else {
                    str = "";
                }
                localIntent.putExtra("android.intent.extra.update_application_message_text", str);
                BaseApplication.getContext().sendBroadcast(localIntent);
            }
        } catch (Exception e) {
        }
    }

    public Notification showXiaoMiUI6RedNum(int num, Notification paramNotification) {
        if (isUPMIUI6()) {
            try {
                Object localObject = Class.forName("android.app.MiuiNotification").newInstance();
                Field localField = localObject.getClass().getDeclaredField("messageCount");
                localField.setAccessible(true);
                localField.set(localObject, num);
                paramNotification.getClass().getField("extraNotification").set(paramNotification, localObject);
            } catch (NoSuchFieldException localNoSuchFieldException) {
                localNoSuchFieldException.printStackTrace();
            } catch (IllegalArgumentException localIllegalArgumentException) {
                localIllegalArgumentException.printStackTrace();
            } catch (IllegalAccessException localIllegalAccessException) {
                localIllegalAccessException.printStackTrace();
            } catch (ClassNotFoundException localClassNotFoundException) {
                localClassNotFoundException.printStackTrace();
            } catch (InstantiationException localInstantiationException) {
                localInstantiationException.printStackTrace();
            } finally {
                return paramNotification;
            }
        }
        return paramNotification;
    }

    /**
     * MIUI6以上系统
     *
     * @return
     */
    public boolean isUPMIUI6() {
        if (!Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            return false;
        }
        String miuiVer = null;
        try {
            Class cls;
            cls = creatClassObject("android.os.SystemProperties");
            miuiVer = getSystemProperties(cls, "ro.miui.ui.version.name");
            if (!TextUtils.isEmpty(miuiVer)) {
                int version = Integer.valueOf(miuiVer.substring(1)); // parasoft-suppress BD.EXCEPT.NP-1
                if (version >= 6) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void showSonyRedNum(int num) {
        String activityName = getLaucherName(BaseApplication.getContext());
        if (activityName == null) {
            return;
        }
        Intent localIntent = new Intent();
        int numInt = Integer.valueOf(num);
        boolean isShow = true;
        if (numInt < 1) {
            isShow = false;
        }
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", activityName);
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", num);
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", BaseApplication.getContext().getPackageName());
        BaseApplication.getContext().sendBroadcast(localIntent);
    }

    public void showSamsungRedNum(int num) {
        try {
            String activityName = getLaucherName(BaseApplication.getContext());
            Intent localIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            localIntent.putExtra("badge_count", num);
            localIntent.putExtra("badge_count_package_name", BaseApplication.getContext().getPackageName());
            localIntent.putExtra("badge_count_class_name", activityName);
            BaseApplication.getContext().sendBroadcast(localIntent);
        } catch (Exception e) {
        }
    }

    public void showSamsungRedNum2(int num) {
        ComponentName componentName = BaseApplication.getContext().getPackageManager()
                .getLaunchIntentForPackage(BaseApplication.getContext()
                        .getPackageName()).getComponent();
        final String CONTENT_URI = "content://com.sec.badge/apps?notify=true";
        final String[] CONTENT_PROJECTION = new String[]{"_id", "class"};
        Uri mUri = Uri.parse(CONTENT_URI);
        ContentResolver contentResolver = BaseApplication.getContext().getContentResolver();
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(mUri, CONTENT_PROJECTION, "package=?", new String[]{componentName.getPackageName()}, null);
            if (cursor != null) {
                String entryActivityName = componentName.getClassName();
                boolean entryActivityExist = false;
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    ContentValues contentValues = getContentValues(componentName, num, false);
                    contentResolver.update(mUri, contentValues, "_id=?", new String[]{String.valueOf(id)});
                    if (entryActivityName.equals(cursor.getString(cursor.getColumnIndex("class")))) {
                        entryActivityExist = true;
                    }
                }

                if (!entryActivityExist) {
                    ContentValues contentValues = getContentValues(componentName, num, true);
                    contentResolver.insert(mUri, contentValues);
                }
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                try {
                    cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ContentValues getContentValues(ComponentName componentName, int badgeCount, boolean isInsert) {
        ContentValues contentValues = new ContentValues();
        if (isInsert) {
            contentValues.put("package", componentName.getPackageName());
            contentValues.put("class", componentName.getClassName());
        }
        contentValues.put("badgecount", badgeCount);
        return contentValues;
    }

    /**
     * 去掉桌面小红点
     *
     */
    public void deleteNumShortCut() {
        addNumShortCut(0);
    }

    /**
     * 在桌面图标上加数字
     *
     * @param num 显示的数字：整型
     */
    public void addNumShortCut(int num) {
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            showXiaoMiRedNum(num);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            showSamsungRedNum(num);
//            samsungShortCut2(context, num);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("huawei")) {
            showHuaWeiRedNum(num);
        } else if (Build.BRAND.equalsIgnoreCase("vivo")) {
            showVivoRedNum(num);
        }/* else if (Build.MANUFACTURER.equalsIgnoreCase("ZUK")) {
            zukShortCut(context, num);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
            sonyShortCut(context, num);
        }else if (Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {
            oppoShortCut(context, num);
        } */
    }

    public void showVivoRedNum(int num) {
        try {
            Intent localIntent1 = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            localIntent1.putExtra("packageName", BaseApplication.getContext().getPackageName());
//            localIntent1.putExtra("className", SplashActivity.class.getName());
            localIntent1.putExtra("className", getLaucherName(BaseApplication.getContext()));
            localIntent1.putExtra("notificationNum", num);
            BaseApplication.getContext().sendBroadcast(localIntent1);
        } catch (Exception e) {
        }
    }

    public void showZukRedNum(int num) {
        try {
            Bundle localBundle = new Bundle();
            localBundle.putStringArrayList("app_shortcut_custom_id", null);
            localBundle.putInt("app_badge_count", num);
            BaseApplication.getContext().getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, localBundle);
        } catch (Exception e) {
        }
    }

    public void showHuaWeiRedNum(int num) {
        try {
            Bundle localBundle = new Bundle();
            localBundle.putString("package", BaseApplication.getContext().getPackageName());
            localBundle.putString("class", getLaucherName(BaseApplication.getContext()));
            localBundle.putInt("badgenumber", num);
            BaseApplication.getContext().getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, localBundle);
        } catch (Exception e) {
        }
    }

    public String mLauncherClassName = "";

    public String getLaucherName(Context context) {
        String str;
        if (!TextUtils.isEmpty(mLauncherClassName)) {
            str = mLauncherClassName;
            return str;
        }
        try {
            PackageManager localPackageManager = context.getPackageManager();
            Intent localIntent = new Intent("android.intent.action.MAIN");
            localIntent.addCategory("android.intent.category.LAUNCHER");
            Iterator localIterator = localPackageManager.queryIntentActivities(localIntent, 0).iterator();
            while (localIterator.hasNext()) {
                ResolveInfo localResolveInfo = (ResolveInfo) localIterator.next();
                if (localResolveInfo.activityInfo.applicationInfo.packageName.equalsIgnoreCase(context.getPackageName())) {
                    str = localResolveInfo.activityInfo.name;
                    mLauncherClassName = str;
                    return str;
                }
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public void showOppoRedNum(Context context, int num) {
        try {
            Bundle localBundle = new Bundle();
            localBundle.putInt("app_badge_count", num);
            context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, localBundle);
        } catch (Exception localException) {
        }
    }


    /**
     * @param className 需要反射类的名字
     * @return
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("rawtypes")
    public static Class creatClassObject(String className) throws ClassNotFoundException {
        Class cls = Class.forName(className);
        return cls;
    }

    /**
     * 得到系统属性key值对应的value值
     *
     * @param cls 反射类
     * @param key 系统属性key值
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String getSystemProperties(Class cls, String key) {
        String value = null;
        try {
            Method hideMethod = cls.getMethod("get", String.class);
            Object object = cls.newInstance();
            value = (String) hideMethod.invoke(object, key);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

}
