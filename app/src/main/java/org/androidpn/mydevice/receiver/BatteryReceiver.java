package org.androidpn.mydevice.receiver;

/**
 * Created by S on 2015/8/20.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.client.LogUtil;
import org.androidpn.mydevice.Contacts;
import org.androidpn.mydevice.DeviceHandler;
import org.androidpn.mydevice.DeviceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 电池信息广播
 */
public class BatteryReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences ;
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            int level = intent.getIntExtra("level", 0);
            int temperature = intent.getIntExtra("temperature", 0);
            Contacts.batteryInfo = (level + ":" + temperature);
//            Message message = new Message();
//            Map map = new HashMap();
//            map.put("level", level+"");
//            map.put("temperature", temperature+"");
//            message.obj =map;
//            message.what= DeviceManager.BATTERY_INFO;
//            handler.sendMessage(message);
            Log.i(LogUtil.makeLogTag(BatteryReceiver.class), "i am comming ....");
        }
    }
};