package org.androidpn.mydevice.listener;

import android.os.Message;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.mydevice.DeviceHandler;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.utils.LogUtils;

import java.util.Map;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Created by Saber on 2015/10/15.
 */
public class Task extends TimerTask {
    DeviceHandler deviceHandler ;
    String data;
    DeviceInfoIQ deviceInfoIQ;
    public Task(DeviceHandler deviceHandler,String data){
        this.deviceHandler = deviceHandler;
        this.data = data;
        this.deviceInfoIQ = DeviceManager.getDeviceManagerInstance().getOverallDeviceInfoInstance();
    }
    @Override
    public void run() {

//        if(deviceInfoIQ.get!=null){
//            Message message = new Message();
//            message.obj = o;
//            message.what = 1234;
//            deviceHandler.sendMessage(message);
//        }
    }
}
