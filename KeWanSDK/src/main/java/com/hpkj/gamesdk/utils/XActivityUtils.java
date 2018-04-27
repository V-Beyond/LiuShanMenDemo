package com.hpkj.gamesdk.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import java.util.Stack;

/**
 * @ClassNname：XActivityUtils.java
 * @Describe Activity 管理类
 * @author huanglei
 * @time 2018/3/12 16:04
 */
public class XActivityUtils {
    public static Context context;

    public static XActivityUtils activityManager;
    public static Stack<Activity> activityList = new Stack<Activity>();

    public static XActivityUtils getInstance() {
        if (activityManager == null) {
            activityManager = new XActivityUtils();
        }
        return activityManager;
    }

    public XActivityUtils() {
        super();
    }

    /**
     * 把当前Activity放入栈中
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        context = activity;
        activityList.push(activity);
    }

    /**
     * 把所有Activity移除
     */
    public void popAllActivity() {
        while (!activityList.isEmpty()) {
            popActivity(activityList.pop());
        }
    }

    /**
     * 把当前Activity移除
     *
     * @param activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
            activityList.remove(activity);
            activity = null;
        }
    }

    /**
     * 重启应用
     */
    @SuppressLint("NewApi")
    public void resetApp(Activity activity) {
        while (!activityList.isEmpty()) {
            popActivity(activityList.pop());
        }
        Intent i = activity
                .getBaseContext()
                .getPackageManager()
                .getLaunchIntentForPackage(
                        activity.getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
    }

    /**
     * 退出系统
     */
    public void exit() {
        while (!activityList.isEmpty()) {
            popActivity(activityList.pop());
        }
        System.exit(0);
        Process.killProcess(Process.myPid());
    }
}
