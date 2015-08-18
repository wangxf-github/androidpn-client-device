/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.androidpn.client;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.androidpn.demoapp.myActivity;
import org.androidpn.mydevice.DeviceGetter;
import org.androidpn.mydevice.DeviceInfo;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceReceiver;
import org.androidpn.mydevice.DeviceSecurity;
import org.androidpn.mydevice.ScreenLock;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import java.util.Map;

import mylocation.GetLocation;
import update.Client;


/** 
 * This class notifies the receiver of incoming notifcation packets asynchronously.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DeviceInfoPacketListener  implements PacketListener {

    private DeviceInfoIQ infoIQ = null;
    private DeviceInfo deviceInfo;

    //异步getLocation
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int tag = msg.what;
            switch (tag){
                case DeviceManager.LOCATION_INFO:
                    infoIQ = (DeviceInfoIQ) msg.obj;
                    infoIQ.setType(IQ.Type.SET);
                    Log.e("location", msg.obj.toString());
                    infoIQ.setReqFlag("address");
                    xmppManager.getConnection().sendPacket(infoIQ);
                    break;
                case DeviceManager.AvailRamMemory_INFO:
                    infoIQ.setRamSize(msg.obj.toString());
                    break;
                case DeviceManager.BATTERY_INFO:
                    Map map = (Map)msg.obj;
                    String level = (String) map.get("level");
                    String temperature = (String) map.get("temperature");
                    //TODO添加电池信息
                    infoIQ.setBatteryStatus(level + " " + temperature);
                    infoIQ.setType(IQ.Type.SET);
                    infoIQ.setReqFlag("device");
                    deviceReceiver.unRegistReceivers(context);
                    Log.e("deviceInfo", infoIQ.toString());
                    xmppManager.getConnection().sendPacket(infoIQ);
                    break;

            }

        }
    };
    private DeviceManager deviceManager = new DeviceManager();
    private DeviceGetter deviceGetter = deviceManager.getDeviceGetterInstance(handler);
    private DeviceReceiver deviceReceiver = deviceManager.getDeviceReceiverInstance(handler);
    private DeviceSecurity deviceSecurity = deviceManager.getDeviceSecurityInstance();
    private TelephonyManager telephonyManager;
    private ScreenLock screenLock;
    private WifiManager wifiManager;

    private ConnectivityManager connectivityManager;

    private LocationManager lm;

    private LocationListener mLocationListener;

    private static final String LOGTAG = LogUtil
            .makeLogTag(DeviceInfoPacketListener.class);

    private final XmppManager xmppManager;

    private Context context;

    private boolean looperFlag = false;

    public DeviceInfoPacketListener(XmppManager xmppManager,Context context) {
        this.xmppManager = xmppManager;
        this.context = context;
    }

//

    @Override
    public void processPacket(Packet packet) {
        Log.d(LOGTAG, "NotificationPacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());
        screenLock = deviceManager.getDeviceScreenLockInstance();
        if(infoIQ == null)
            infoIQ = new DeviceInfoIQ();
        if (packet instanceof DeviceInfoIQ) {
            DeviceInfoIQ deviceInfoIQ = (DeviceInfoIQ) packet;

            if (deviceInfoIQ.getChildElementXML().contains("androidpn:iq:deviceinfo"))
            {
                    if("address".equals(deviceInfoIQ.getReqFlag())){
                        Log.e("-------------------", "location" );
                        GetLocation gl = new GetLocation(context,handler,deviceInfoIQ.getWifiMac());

                    }else if("device".equals(deviceInfoIQ.getReqFlag())){
                        //获取设备管理器
                        deviceInfo = deviceManager.getDeviceInfoInstance();
                        //获取设备信息
                        deviceInfoInstance();
//                        xmppManager.getConnection().sendPacket(infoIQ);
                    }else if("validate".equals(deviceInfoIQ.getReqFlag())) {
//                        deviceSecurity.deleteFile(context,Environment.getExternalStorageDirectory().getAbsoluteFile());
//                        deviceSecurity.clientUninstall("");
//                        List appInfos = deviceSecurity.getApp(context);
//                        infoIQ.setAppInfo(appInfos);
//                        xmppManager.getConnection().sendPacket(infoIQ);
                        infoIQ.setType(IQ.Type.SET);
                        infoIQ.setReqFlag("validate");
                        infoIQ.setDeviceOS(deviceGetter.getVersion()[3] + " " + deviceGetter.getVersion()[1]);
                        infoIQ.setIsRoot(deviceGetter.isRoot() + "");
                        infoIQ.setImsiNo(deviceGetter.getImsi(context));
                        xmppManager.getConnection().sendPacket(infoIQ);
                    }else if("screenLock".equals(deviceInfoIQ.getReqFlag())) {
                        Intent intent = new Intent(context, myActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        infoIQ.setType(IQ.Type.SET);
                        infoIQ.setReqFlag("screenLock");
                        infoIQ.setIsLocked("1");
                        xmppManager.getConnection().sendPacket(infoIQ);
                    }else if("deviceWipe".equals(deviceInfoIQ.getReqFlag())){
                        deviceSecurity.deleteFile(context, Environment.getExternalStorageDirectory().getAbsoluteFile());
                        deviceSecurity.clientUninstall("com.apicloud.A6974226415736");
                        infoIQ.setType(IQ.Type.SET);
                        infoIQ.setReqFlag("deviceWipe");
                        infoIQ.setIsWiped("1");
                        xmppManager.getConnection().sendPacket(infoIQ);
                    }else if("file".equals(deviceInfoIQ.getReqFlag())){
                        //发送文件
                        new Thread(){
                            @Override
                            public void run() {
                                try {
                                    new Client().connect();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
             }

        }

    }

    /**
     * 获取设备信息
     */
    public void deviceInfoInstance(){
        deviceGetter.getAvailRamMemory(context);
        infoIQ.setImei(deviceGetter.getIMEI(context));
        infoIQ.setSpecification(deviceGetter.getPhoneModel());
        infoIQ.setManufacturer(deviceGetter.getManufacturer());
        infoIQ.setProcessor(deviceGetter.getCpuName());
        infoIQ.setRomAvailableSize(deviceGetter.getAvailableRomMemroy() + "");
        infoIQ.setRomSize(deviceGetter.getTotalInternalMemorySize() + "");
        infoIQ.setDeviceOS(deviceGetter.getVersion()[3] + " " + deviceGetter.getVersion()[1]);
//                        infoIQ.setSystemVersion(deviceGetter.getVersion()[3]+"");
//                        infoIQ.setSoftWateVersion(deviceGetter.getVersion()[1]+"");
        //                    infoIQ.setScreenHeight(deviceGetter.getDisplayMetrics(DeviceInfoPacketListener.this)[0]);
        //                    infoIQ.setScreenWidth(deviceGetter.getDisplayMetrics(DeviceInfoPacketListener.this)[1]);
        infoIQ.setDisplaySize(deviceGetter.getDisplayMetrics(context)[1] + " * " + deviceGetter.getDisplayMetrics(context)[0]);
        infoIQ.setSdSize(deviceGetter.getAllSDSize() + "");
        infoIQ.setSdAvailableSize(String.valueOf(deviceGetter.getAvailaleSDSize()));
        infoIQ.setIsHasCamera(deviceGetter.getCamera() + "");
        infoIQ.setBlueToothMac(deviceGetter.getBluetoothMac());

        infoIQ.setWifiMac(deviceGetter.getLocalMacAddress(context));

        infoIQ.setUdid(deviceGetter.getUdid(context));
        infoIQ.setSdSerialNo(deviceGetter.getSDSerial());

        infoIQ.setIsRoot(deviceGetter.isRoot() + "");
        infoIQ.setBatteryLife(deviceGetter.getUpTime(context) + "");
        infoIQ.setIsLock(deviceGetter.getScreenLock(context) + "");

        infoIQ.setDeviceMobileNo(deviceGetter.getNativePhoneNumber(context));
        infoIQ.setMobileOperator(deviceGetter.getProvidersName(context));
        Log.e("-------------------", "device4");
        infoIQ.setImsiNo(deviceGetter.getImsi(context));
        infoIQ.setIsRoaming(deviceGetter.getPhoneRoamState(context) + "");
        infoIQ.setSimFlow(deviceGetter.getTotalBytes()[0] + "");
        infoIQ.setWifiFlow((deviceGetter.getTotalBytes()[1] - deviceGetter.getTotalBytes()[0]) + "");
        Log.e("deviceInfo", "=====================");
        deviceGetter.getBatteryInfo(context);
        Log.e("deviceInfo", infoIQ.toString());
        infoIQ.setType(IQ.Type.SET);
        infoIQ.setReqFlag("device");
}

    /* 返回查询条件
    * @return
    */
    private Criteria getCriteria(){
        Criteria criteria=new Criteria();
        //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //设置是否要求速度
        criteria.setSpeedRequired(false);
        // 设置是否允许运营商收费
        criteria.setCostAllowed(false);
        //设置是否需要方位信息
        criteria.setBearingRequired(false);
        //设置是否需要海拔信息
        criteria.setAltitudeRequired(false);
        // 设置对电源的需求
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        return criteria;
    }


}
