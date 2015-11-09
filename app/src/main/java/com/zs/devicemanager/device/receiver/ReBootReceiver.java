package com.zs.devicemanager.device.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidpn.connection.ClientService;

/**
 * Created by Saber on 2015/9/14.
 */
public class ReBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intents = new Intent(context, ClientService.class);
        context.startService(intents);
        Log.i("comming","ReBootReceiver...");
    }
}
