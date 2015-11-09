package com.zs.devicemanager.device.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by S on 2015/8/20.
 * wifi网络状态的广播
 */

public class WifiStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取网络服务
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取wifi网络服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //获取wifi状态信息-===================================================================================================================================================================================================================================================
        NetworkInfo wifi = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (!wifi.isConnected()) {
            // network closed
            Log.i("wifi status.....", "duan kai le ......");
        } else {
            // network opend
            Log.e("wifi status.....", "lian jie  le ......");
            wifiManager.setWifiEnabled(false);
        }
    }
}