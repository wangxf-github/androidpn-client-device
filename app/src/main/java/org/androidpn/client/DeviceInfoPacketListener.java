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
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import org.androidpn.demoapp.ScreenLockActivity;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.mydevice.DeviceGetter;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceSecurity;
import org.androidpn.mydevice.cmdcontrol.CmdLines;
import org.androidpn.mydevice.cmdcontrol.CmdOperate;
import org.androidpn.mydevice.cmdcontrol.CmdType;
import org.androidpn.utils.DataUtils;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import java.util.HashMap;
import java.util.Map;


/** 
 * This class notifies the receiver of incoming notifcation packets asynchronously.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DeviceInfoPacketListener extends BaseDeviceFunction implements PacketListener {

    private static DeviceInfoIQ infoIQ = null;
    private static XmppManager xmppManager;
    private DeviceManager deviceManager;
    private DeviceGetter deviceGetter  ;
    private DeviceSecurity deviceSecurity;
    private CmdLines deviceCmdLine;
    private static final String LOGTAG = LogUtil
            .makeLogTag(DeviceInfoPacketListener.class);
    private Context context;

    public DeviceInfoPacketListener(XmppManager xmppManager,Context context) {
        this.xmppManager = xmppManager;
        this.context = context;
        initDatas();
    }


    @Override
    public void processPacket(Packet packet) {
        Log.e(LOGTAG, "packet.toXML()=" + packet.toXML());

        infoIQ =new DeviceInfoIQ();
        CmdOperate cmdOperate = deviceManager.getDeviceCmdOperate();
        if (packet instanceof DeviceInfoIQ) {
            DeviceInfoIQ deviceInfoIQ = (DeviceInfoIQ) packet;
            if (deviceInfoIQ.getChildElementXML().contains("androidpn:iq:deviceinfo")) {
                if ("strategy".equals(deviceInfoIQ.getReqFlag())) {
                    String collectCmds = deviceInfoIQ.getDeviceCollection();
                    String limitCmds = deviceInfoIQ.getDeviceLimition();
                    if (collectCmds != null) {
                        cmdOperate.doStrategyMethod(context, deviceInfoIQ, infoIQ, collectCmds, CmdType.COLLECTION_CMD);
                    }
                    if (limitCmds != null) {
                        cmdOperate.doStrategyMethod(context, deviceInfoIQ, infoIQ, limitCmds, CmdType.LIMITION_CMD);
                    }
                    infoIQ.setReqFlag("strategy");
                } else {
                    String cmd = deviceInfoIQ.getReqFlag();
                    deviceCmdLine.doMethods(context, deviceInfoIQ, infoIQ, cmd);
                }
                xmppManager.getConnection().sendPacket(infoIQ);
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
        infoIQ.setType(IQ.Type.SET);
        infoIQ.setReqFlag("hardwareInfo");
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


    public void initDatas(){
        deviceManager =getDeviceManagerInstance() ;
        deviceSecurity = deviceManager.getDeviceSecurityInstance();
        deviceGetter = deviceManager.getDeviceGetterInstance();

//        wifiStateReceiver  =deviceManager.getWifiStateReceiver();
//        mobileStatesReceiver = deviceManager.getMobileStatesReceiver();
    }

    public static XmppManager getXmppManager(){
        if(xmppManager==null){
        }
        return xmppManager;
    }

    /**
     * 获取设备信息IQ
     * @return
     */
    public static DeviceInfoIQ getDeviceInfoIQInstance(){
                if(infoIQ==null){
                    infoIQ=new DeviceInfoIQ();
                }
        return infoIQ;
    }

}
