package com.nl.treadmilllauncher;

import android.app.Application;

import com.nl.treadmilllauncher.application.ActivityLifecycleHelper;
import com.nl.treadmilllauncher.application.UnCeHandler;

/**
 * Created by dingo on 2018/3/14.
 */

public class MainApplication extends Application {

    private ActivityLifecycleHelper mActivityLifecycleHelper;

    public ActivityLifecycleHelper getActivityLifecycleHelper() {
        return mActivityLifecycleHelper;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);

        registerActivityLifecycleCallbacks(
                mActivityLifecycleHelper = new ActivityLifecycleHelper());
    }

    public void finishActivity() {
        mActivityLifecycleHelper.finishAllActivity();

        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
