package org.androidpn.client;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.zs.devicemanager.R;

/**
 * Created by Saber on 2015/9/15.
 */
public class ClientService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        Log.e("clientService","onStart......");
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Intent localIntent = new Intent();
        localIntent.setClass(this, ClientService.class); //销毁时重新启动Service
        this.startService(localIntent);
    }
}
