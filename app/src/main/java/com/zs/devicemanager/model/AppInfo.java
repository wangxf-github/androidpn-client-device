package com.zs.devicemanager.model;

import android.graphics.drawable.Drawable;

/**
 * Created by S on 2015/8/15.
 */
public class AppInfo {
    /*
    app名称
     */
    private String appName;
    /*
    app包名，卸载时使用
     */
    private String packageName;
    /*
    app版本
     */
    private String versionCode;
    /*
    app图标
     */
    private Drawable appIcon;
    /*
    app安装时间
     */
    private String firstInstallTime;

    public AppInfo(){}

    public String getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(String firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

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

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appIcon=" + appIcon +
                ", appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", firstInstallTime='" + firstInstallTime + '\'' +
                '}';
    }
}
