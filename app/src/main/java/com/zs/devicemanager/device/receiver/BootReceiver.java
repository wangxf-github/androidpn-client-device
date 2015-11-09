package com.zs.devicemanager.device.receiver;

/**
 * Created by S on 2015/8/20.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import com.zs.devicemanager.device.DeviceHandler;
import com.zs.devicemanager.device.DeviceManager;

/**
 * 卸载广播
 */
public class BootReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent){

        DeviceHandler handler = new DeviceHandler();
        Log.e("BootReceiver",intent.getAction().toString());
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            Message message = new Message();
            message.obj = packageName;
            message.what = DeviceManager.INSTALL_APK;
            handler.sendMessage(message);
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            Message message = new Message();
            message.obj = packageName;
            message.what =DeviceManager.UNINSTALL_APK;
            handler.sendMessage(message);
        }
    }
}