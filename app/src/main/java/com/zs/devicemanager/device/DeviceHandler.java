package com.zs.devicemanager.device;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.zs.devicemanager.model.DeviceInfoIQ;

import org.androidpn.connection.LogUtil;
import org.androidpn.connection.XmppManager;

import java.util.Map;

/**
 * Created by S on 2015/8/20.
 */
public class DeviceHandler  extends Handler{

    private  XmppManager xmppManager;
    private DeviceInfoIQ infoIQ = null;

        @Override
        public void handleMessage(Message msg) {
            xmppManager = DeviceInfoPacketListener.getXmppManager();
            infoIQ = DeviceManager.getDeviceManagerInstance().getOverallDeviceInfoInstance();
            super.handleMessage(msg);
            int tag = msg.what;
            switch (tag){
                case DeviceManager.INSTALL_APK:
                    String IpackageName = msg.obj.toString();
                    Log.e("++++++++++",IpackageName);
                    break;
                case DeviceManager.UNINSTALL_APK:
                    String UpackageName = msg.obj.toString();
                    Log.e("----------------", UpackageName);
                    break;
//                case DeviceManager.LOCATION_INFO:
//                    infoIQ = (DeviceInfoIQ) msg.obj;
//                    infoIQ.setType(IQ.Type.SET);
//                    Log.e("location", msg.obj.toString());
//                    infoIQ.setReqFlag("deviceLocaltion");
//                    xmppManager.getConnection().sendPacket(infoIQ);
//                    break;
                case DeviceManager.AvailRamMemory_INFO:
                    infoIQ.setRamSize(msg.obj.toString());
                    break;
                case DeviceManager.BATTERY_INFO:
                    Map map = (Map)msg.obj;
                    String level = (String) map.get("level");
                    String temperature = (String) map.get("temperature");
                    //TODO添加电池信息
                    infoIQ.setBatteryStatus(level + ":" + temperature);

                    Log.e(LogUtil.makeLogTag(DeviceHandler.class), level + " " + temperature);
//                    xmppManager.getConnection().sendPacket(infoIQ);
                    break;

                case 1234:
                    String result = msg.obj.toString();
                    break;
            }
        }
}
