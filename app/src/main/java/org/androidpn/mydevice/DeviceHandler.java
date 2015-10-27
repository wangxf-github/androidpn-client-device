package org.androidpn.mydevice;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.client.DeviceInfoPacketListener;
import org.androidpn.client.LogUtil;
import org.androidpn.client.XmppManager;
import org.androidpn.utils.LogUtils;
import org.jivesoftware.smack.packet.IQ;

import java.util.Map;

/**
 * Created by S on 2015/8/20.
 */
public class DeviceHandler  extends Handler{

    private  XmppManager xmppManager;
    private DeviceInfoIQ infoIQ = null;

        @Override
        public void handleMessage(Message msg) {
            LogUtils.takeLog(DeviceHandler.class,"i am in handlermessage.......");
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
                    LogUtils.takeLog(DeviceHandler.class,"result ==="+result);
                    break;
            }
        }
}
