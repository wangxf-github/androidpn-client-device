package org.androidpn.mydevice.DeviceReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zs.devicemanager.R;

import org.androidpn.client.ServiceManager;

/**
 * Created by Saber on 2015/9/14.
 */
public class ReBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ServiceManager serviceManager = new ServiceManager(context);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();
        Log.e("comming","ReBootReceiver...");
    }
}
