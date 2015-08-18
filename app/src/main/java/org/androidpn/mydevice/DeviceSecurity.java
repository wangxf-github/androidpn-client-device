package org.androidpn.mydevice;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.MediaStore.Images.Media;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 2015/8/14.
 */
public class DeviceSecurity extends BaseDeviceActivity{

    PackageManager packageManager ;
    List<PackageInfo> pakageinfos;
    List<AppInfo> pakages = new ArrayList<AppInfo>();

    /**
     * 获取手机上的app
     */
public List getApp(Context context) {
    packageManager = context.getPackageManager();
    pakageinfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
    for (PackageInfo pi : pakageinfos) {
        if((pi.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0)
        {
            //非系统应用
            AppInfo tmpInfo =new AppInfo();
            String pi_packageName = pi.packageName;
            int i = 0;
            tmpInfo.setAppName(pi.applicationInfo.loadLabel(context.getPackageManager()).toString());
            tmpInfo.setPackageName(pi.packageName);
            tmpInfo.setVersionCode(pi.versionCode + "");
            tmpInfo.setAppIcon(pi.applicationInfo.loadIcon(context.getPackageManager()));
            Log.e("====",tmpInfo.toString());
            pakages.add(tmpInfo);
        }
        else
        {
//系统应用　　　　　　　　
        }

    }
    return pakages;
}
    /**
     * 静默卸载
     */
    public static boolean clientUninstall(String packageName){
        PrintWriter PrintWriter = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            PrintWriter = new PrintWriter(process.getOutputStream());
            PrintWriter.println("LD_LIBRARY_PATH=/vendor/lib:/system/lib ");
            PrintWriter.println("pm uninstall "+packageName);
            PrintWriter.flush();
            PrintWriter.close();
            int value = process.waitFor();
            return returnResult(value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(process!=null){
                process.destroy();
            }
        }
        return false;
    }

    /**
     * 销毁文件
     */
    public void deleteFile(final Context context, final File file) {
        new Thread() {
            @Override
            public void run() {
                context.getApplicationContext().getContentResolver().delete(Media.EXTERNAL_CONTENT_URI, Media.DATA + "=?", new String[]{file.getAbsolutePath()});
                if (file.exists() == false) {
                    return;
                } else {
                    if (file.isFile()) {
                        file.delete();
                        return;
                    }
                    if (file.isDirectory()) {
                        File[] childFile = file.listFiles();
                        if (childFile == null || childFile.length == 0) {
                            file.delete();
                            return;
                        }
                        for (File f : childFile) {
                            deleteFile(context, f);
                        }
                        file.delete();
                    }
                }
            }
        }.start();
    }



    private static boolean returnResult(int value){
        // 代表成功
        if (value == 0) {
            return true;
        } else if (value == 1) { // 失败
            return false;
        } else { // 未知情况
            return false;
        }
    }

}
