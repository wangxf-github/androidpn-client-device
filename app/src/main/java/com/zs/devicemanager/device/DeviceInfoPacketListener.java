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
package com.zs.devicemanager.device;

import android.content.Context;
import android.util.Log;

import com.zs.devicemanager.device.cmd.CmdLines;
import com.zs.devicemanager.device.cmd.CmdOperate;
import com.zs.devicemanager.device.cmd.CmdShine;
import com.zs.devicemanager.device.cmd.CmdType;
import com.zs.devicemanager.model.DeviceInfoIQ;
import com.zs.devicemanager.utils.LogUtils;

import org.androidpn.connection.LogUtil;
import org.androidpn.connection.XmppManager;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Packet;


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
        LogUtils.makeInfoLog(DeviceInfoPacketListener.class,"----"+i);
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





    //初始化设备获取器
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
