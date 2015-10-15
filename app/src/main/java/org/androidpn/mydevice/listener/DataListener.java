package org.androidpn.mydevice.listener;

import org.androidpn.mydevice.DeviceHandler;

import java.util.Map;
import java.util.Timer;
import java.util.logging.Handler;

/**
 * Created by Saber on 2015/10/15.
 */
public class DataListener {
    public static void setDataListener(String data){
        Timer timer = new Timer();
        DeviceHandler deviceHandler= new DeviceHandler();
        timer.schedule(new Task(deviceHandler,data), 100);
    }

}
