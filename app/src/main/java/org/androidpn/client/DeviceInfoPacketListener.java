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

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.androidpn.demoapp.ScreenLockActivity;
import org.androidpn.mydevice.AppInfo;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.mydevice.DeviceGetter;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceSecurity;
import org.androidpn.mydevice.cmd.CmdLines;
import org.androidpn.mydevice.cmd.CmdOperate;
import org.androidpn.mydevice.cmd.CmdShine;
import org.androidpn.mydevice.cmd.CmdType;
import org.androidpn.utils.LogUtils;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;

import java.util.ArrayList;
import java.util.List;

import mylocation.GetLocation;
import update.Client;


/** 
 * This class notifies the receiver of incoming notifcation packets asynchronously.  
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DeviceInfoPacketListener  implements PacketListener {


    private static XmppManager xmppManager;
    private static final String LOGTAG = LogUtil
            .makeLogTag(DeviceInfoPacketListener.class);
    private Context context;
    private CmdOperate cmdOperate ;
    private CmdLines cmdLines ;
    private DeviceManager deviceManager;

    private int i = 0;

    public DeviceInfoPacketListener(XmppManager xmppManager,Context context) {
        this.xmppManager = xmppManager;
        this.context = context;
        initDatas();
    }


    @Override
    public void processPacket(Packet packet) {
        i++;
        LogUtils.takeLog(DeviceInfoPacketListener.class,"------------"+i);
        Log.e(LOGTAG, "packet.toXML()=" + packet.toXML());
        if (packet instanceof DeviceInfoIQ) {
            DeviceInfoIQ deviceInfoIQ = (DeviceInfoIQ) packet;
            String flag = deviceInfoIQ.getReqFlag();
            if (deviceInfoIQ.getChildElementXML().contains("androidpn:iq:deviceinfo"))
            {
                    if("strategy".equals(flag)){
                        DeviceInfoIQ infoIQ = deviceManager.getSingleDeviceInfoInstance();
                        cmdOperate.doMethods(context,deviceInfoIQ,infoIQ, CmdType.COLLECTION);
                        cmdOperate.doMethods(context,deviceInfoIQ,infoIQ, CmdType.LIMITION);

                    }else  {
                            DeviceInfoIQ infoIQ = deviceManager.getSingleDeviceInfoInstance();
                            int cmdInt = CmdShine.cmdToInt(flag);
                            cmdOperate.doMethods(context, deviceInfoIQ, infoIQ, cmdInt);
                        }
                    }
             }

        }





    public void initDatas(){
        deviceManager =DeviceManager.getDeviceManagerInstance() ;
//        deviceSecurity = deviceManager.getDeviceSecurityInstance();
//        deviceGetter = deviceManager.getDeviceGetterInstance();
//        wifiStateReceiver  =deviceManager.getWifiStateReceiver();
//        mobileStatesReceiver = deviceManager.getMobileStatesReceiver();
        cmdOperate =deviceManager.getDeviceCmdOperate();
        cmdLines = deviceManager.getDeviceCmdLine();
    }

    public static XmppManager getXmppManager(){
        if(xmppManager==null){
        }
        return xmppManager;
    }

}
