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
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import org.androidpn.demoapp.ScreenLockActivity;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.mydevice.DeviceGetter;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceReceiver.BatteryReceiver;
import org.androidpn.mydevice.DeviceReceiver.BootReceiver;
import org.androidpn.mydevice.DeviceReceiver.MobileStatesReceiver;
import org.androidpn.mydevice.DeviceReceiver.WifiStateReceiver;
import org.androidpn.mydevice.DeviceSecurity;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import update.Client;


/** 
 * This class notifies the receiver of incoming notifcation packets asynchronously.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DeviceInfoPacketListener extends BaseDeviceFunction implements PacketListener {


    private static  XmppManager xmppManager;

    private static final String LOGTAG = LogUtil
            .makeLogTag(DeviceInfoPacketListener.class);
    private Context context;

    public DeviceInfoPacketListener(XmppManager xmppManager,Context context) {
        this.xmppManager = xmppManager;
        this.context = context;
    }


    @Override
    public void processPacket(Packet packet) {
        Log.d(LOGTAG, "NotificationPacketListener.processPacket()...");
        Log.d(LOGTAG, "packet.toXML()=" + packet.toXML());
        DeviceInfoIQ infoIQ =new  DeviceInfoIQ();
        DeviceManager deviceManager = getDeviceManagerInstance();;
        DeviceSecurity deviceSecurity = deviceManager.getDeviceSecurityInstance();
        BatteryReceiver batteryReceiver = deviceManager.getBatteryReceiver();
        BootReceiver bootReceiver = deviceManager.getBootReceiver();
        DeviceGetter deviceGetter = deviceManager.getDeviceGetterInstance();
        WifiStateReceiver wifiStateReceiver = deviceManager.getWifiStateReceiver();
        MobileStatesReceiver mobileStatesReceiver = deviceManager.getMobileStatesReceiver();
        if (packet instanceof DeviceInfoIQ) {
            DeviceInfoIQ deviceInfoIQ = (DeviceInfoIQ) packet;

            if (deviceInfoIQ.getChildElementXML().contains("androidpn:iq:deviceinfo"))
            {
                    if("address".equals(deviceInfoIQ.getReqFlag())){
                        Log.e("-------------------", "location" );
                        //获取地理位置
//                        GetLocation gl = new GetLocation(context,handler,deviceInfoIQ.getWifiMac());
                    String[] action = {ConnectivityManager.CONNECTIVITY_ACTION};
                     deviceManager.registReceivers(context,wifiStateReceiver,action);
                        deviceManager.registReceivers(context,mobileStatesReceiver,action);
                    }else if("device".equals(deviceInfoIQ.getReqFlag())){
                        //获取设备信息
                        deviceGetter.getAvailRamMemory(context);
                        deviceGetter.getIMEI(context);
                        infoIQ.setSpecification(deviceGetter.getPhoneModel());
                        infoIQ.setManufacturer(deviceGetter.getManufacturer());
                        infoIQ.setProcessor(deviceGetter.getCpuName());
                        infoIQ.setRomAvailableSize(deviceGetter.getAvailableRomMemroy() + "");
                        infoIQ.setRomSize(deviceGetter.getTotalInternalMemorySize() + "");
                        infoIQ.setDeviceOS(deviceGetter.getVersion()[3] + " " + deviceGetter.getVersion()[1]);
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
                        infoIQ.setImsiNo(deviceGetter.getImsi(context));
                        infoIQ.setIsRoaming(deviceGetter.getPhoneRoamState(context) + "");
                        infoIQ.setSimFlow(deviceGetter.getTotalBytes()[0] + "");
                        infoIQ.setWifiFlow((deviceGetter.getTotalBytes()[1] - deviceGetter.getTotalBytes()[0]) + "");
                        deviceGetter.getBatteryInfo(context);
                        Log.e("deviceInfo", infoIQ.toString());
                        infoIQ.setType(IQ.Type.SET);
                        infoIQ.setReqFlag("device");
                    }else if("validate".equals(deviceInfoIQ.getReqFlag())) {
                        infoIQ.setType(IQ.Type.SET);
                        infoIQ.setReqFlag("validate");
                        infoIQ.setDeviceOS(deviceGetter.getVersion()[3] + " " + deviceGetter.getVersion()[1]);
                        infoIQ.setIsRoot(deviceGetter.isRoot() + "");
                        infoIQ.setImsiNo(deviceGetter.getImsi(context));
                        xmppManager.getConnection().sendPacket(infoIQ);
                    }else if("screenLock".equals(deviceInfoIQ.getReqFlag())) {
                        Log.e("ccccccccc","----------------");
                        //锁屏及修改密码
                        deviceLockOrWipe(DeviceManager.SCREEN_LOCK, "");

                        infoIQ.setType(IQ.Type.SET);
                        infoIQ.setReqFlag("screenLock");
                        infoIQ.setIsLocked("1");
                        xmppManager.getConnection().sendPacket(infoIQ);
                    }else if("deviceWipe".equals(deviceInfoIQ.getReqFlag())){
                        //删除数据
//                        deviceSecurity.deleteFile(context, Environment.getExternalStorageDirectory().getAbsoluteFile());

                        //卸载应用
                        String[] action = {Intent.ACTION_PACKAGE_ADDED,Intent.ACTION_PACKAGE_REMOVED};
                        deviceManager.registReceivers(context,bootReceiver,action);
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

    /**
     * 进行锁屏，修改锁屏密码和恢复出厂设置
     * @param tag 标志是否锁屏或者恢复出厂值设置
     * @param password 锁屏密码
     */
    private void deviceLockOrWipe(int tag,String password){
        Intent intent = new Intent(context, ScreenLockActivity.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("password", password);
        bundle.putInt("tag", tag);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public static XmppManager getXmppManager(){
        if(xmppManager==null){
        }
        return xmppManager;
    }
}
