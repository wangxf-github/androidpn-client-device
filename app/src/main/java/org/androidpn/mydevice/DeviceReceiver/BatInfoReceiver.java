package org.androidpn.mydevice.DeviceReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

import org.androidpn.client.ClientService;
import org.androidpn.mydevice.DeviceHandler;
import org.androidpn.mydevice.DeviceManager;

/**
 * Created by S on 2015/8/24.
 */
public class BatInfoReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DeviceHandler handler = new DeviceHandler();
        Log.e("screen","sssssssssssssssssssssss");
        String action = intent.getAction();
        Log.e("screen", action);
        if("android.intent.action.SCREEN_ON".equals(action)){
            Log.d("on=====", "screen is on...");
//            Intent intents = new Intent(context, ClientService.class);
//            context.startService(intents);
            Message message = new Message();
            message.obj = "on";
            message.what = DeviceManager.SCREEN_ON;
            handler.sendMessage(message);
        }else if("android.intent.action.SCREEN_OFF".equals(action)){
            Log.d("off======", "screen is off...");
//            Intent intents = new Intent(context, ClientService.class);
//            context.startService(intents);
            Message message = new Message();
            message.obj = "off";
            message.what =DeviceManager.SCREEN_OFF;
            handler.sendMessage(message);
        }
    }
}
