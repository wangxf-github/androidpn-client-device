package org.androidpn.mydevice;

import android.graphics.drawable.Drawable;

/**
 * Created by S on 2015/8/15.
 */
public class AppInfo {
    private String appName;
    private String packageName;
    private String versionCode;
    private Drawable appIcon;

    public AppInfo(){}

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
                '}';
    }
}
