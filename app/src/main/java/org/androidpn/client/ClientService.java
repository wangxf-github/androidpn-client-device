package org.androidpn.client;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.zs.devicemanager.R;

import org.androidpn.mydevice.DeviceReceiver.BatInfoReceiver;

/**
 * Created by Saber on 2015/9/15.
 */
public class ClientService extends Service {
    private BatInfoReceiver batInfoReceiver;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        batInfoReceiver = new BatInfoReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        Log.e("clientService","onStart......");
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(batInfoReceiver, filter);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, ClientService.class); //销毁时重新启动Service
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startService(localIntent);
        unregisterReceiver(batInfoReceiver);
    }
}
