package org.androidpn.mydevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by S on 2015/7/27.
 */
public class DeviceReceiver {
    Handler handler;
    DeviceInfo deviceInfo;
    private BatteryReceiver batteryReceiver ;

    /**
     * 电池信息广播
     */
    class BatteryReceiver extends  BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                int level = intent.getIntExtra("level", 0);
                int temperature = intent.getIntExtra("temperature", 0);
                Message message = new Message();
                Map map = new HashMap();
                map.put("level", level+"");
                map.put("temperature", temperature+"");
                message.obj =map;
                message.what=DeviceManager.BATTERY_INFO;
                handler.sendMessage(message);
                Log.e("asdf", "sssssss");
            }
        }
    };


    public DeviceReceiver(Handler handler) {
        this.handler = handler;
        deviceInfo = BaseDeviceActivity.getDeviceManager().getDeviceInfoInstance();
    }

    /**
     * 注册receiver
     */
    public void registReceivers(Context activity,int[] s ){
        for(int i=0;i<s.length;i++) {
            switch (s[i]) {
                case DeviceManager.BATTERY_INFO:
                    IntentFilter filter = new IntentFilter(
                            Intent.ACTION_BATTERY_CHANGED);
                    activity.registerReceiver(getBatteryReceiver(), filter);
                    Log.e("reveiver","I am resgist");
            }
        }

    }
    /**
     * 注销receiver
     */
    public void unRegistReceivers(Context activity){
        int[] s = deviceInfo.getRegisters();
        for(int i=0;i<s.length;i++) {
            switch (s[i]) {
                case DeviceManager.BATTERY_INFO:
                    activity.unregisterReceiver(getBatteryReceiver());
                    Log.e("reveiver", "I am unresgist");

            }
        }
    }

    /**
     * 获取电池的注册器
     * @return
     */
    public BroadcastReceiver getBatteryReceiver(){
    if(batteryReceiver==null){
        synchronized(DeviceReceiver.class){
            if(batteryReceiver==null){
                batteryReceiver=new BatteryReceiver();
            }
        }
    }
    return batteryReceiver;
}

}
