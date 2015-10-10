package org.androidpn.mydevice.DeviceReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.androidpn.client.ClientService;

/**
 * Created by Saber on 2015/9/14.
 */
public class ReBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intents = new Intent(context, ClientService.class);
        context.startService(intents);
        Log.e("comming","ReBootReceiver...");
    }
}
