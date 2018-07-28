package com.nl.treadmilllauncher.model;

import android.graphics.drawable.Drawable;

/**
 * Created by dingo on 2018/3/21.
 */

public class AppInfo {

    private String appName;
    private String packageName;
    private Drawable appIcon;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }
}
