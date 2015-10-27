package org.androidpn.mydevice.cmd;

import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothClass;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.client.DeviceInfoPacketListener;
import org.androidpn.client.XmppManager;
import org.androidpn.demoapp.ScreenLockActivity;
import org.androidpn.mydevice.AppInfo;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.mydevice.Contacts;
import org.androidpn.mydevice.DeviceGetter;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceSecurity;
import org.androidpn.mydevice.receiver.BatteryReceiver;
import org.androidpn.mydevice.receiver.MyAdminReceiver;
import org.jivesoftware.smack.packet.IQ;

import java.util.ArrayList;
import java.util.List;

import mylocation.GetLocation;
import mylocation.GpsLocation;

/**
 * Created by Saber on 2015/10/9.
 */
public class CmdLines {

    DeviceManager deviceManager = DeviceManager.getDeviceManagerInstance();
    DeviceSecurity deviceSecurity = deviceManager.getDeviceSecurityInstance();
    DeviceGetter deviceGetter = deviceManager.getDeviceGetterInstance();
    /**
     * 执行单个指令
     * @param context
     * @param deviceInfoIQ
     * @param infoIQ
     * @param cmd
     */
    public void doMethod(Context context, DeviceInfoIQ deviceInfoIQ,
                          DeviceInfoIQ infoIQ, int cmd) {

        switch (cmd){
            case CmdType.IMEI:
                infoIQ.setImei(deviceGetter.getIMEI(context));
                break;
            case CmdType.BLUETOOTHMAC:
                infoIQ.setBlueToothMac(deviceGetter.getBluetoothMac());
                break;
            case CmdType.BATTERYSTATUS:
                deviceGetter.getBatteryInfo(context);
                break;
            case CmdType.PROCESSOR:
                infoIQ.setProcessor(deviceGetter.getCpuName());
                break;
            case CmdType.DEVICEMOBILENO:
                infoIQ.setDeviceMobileNo(deviceGetter.getNativePhoneNumber(context));
                break;
            case CmdType.DEVICEOS:
                infoIQ.setDeviceOS(deviceGetter.getVersion()[1]);
                break;
            case CmdType.PHONEMODEL:
                infoIQ.setPhoneModel(deviceGetter.getPhoneModel());
                break;
            case CmdType.DEVICEWIPE:
                CmdOperate.doWipe(context);
                break;
            case CmdType.DISPLAYSIZE:
                infoIQ.setDisplaySize(deviceGetter.getDisplayMetrics(context)[1] + " * " + deviceGetter.getDisplayMetrics(context)[0]);
                break;
            case CmdType.IMSINO:
                infoIQ.setImsiNo(deviceGetter.getImsi(context));
                break;
            case CmdType.ISLOCK:
                infoIQ.setIsLock(deviceGetter.getScreenLock(context) + "");
                break;
            case CmdType.CAMERA:
                boolean camera = deviceGetter.getCamera();
                Log.e("camera",camera+"");
                infoIQ.setCamera(deviceGetter.getCamera()+"");
                break;
            case CmdType.ISROAMING:
                infoIQ.setIsRoaming(deviceGetter.getPhoneRoamState(context) + "");
                break;
            case CmdType.ISROOT:
                infoIQ.setIsRoot(deviceGetter.isRoot()+"");
                //Test 测试
//                infoIQ.setIsRoot("true");
                break;
            case CmdType.LOCATION:
                new GetLocation(context,infoIQ);
//                new GpsLocation(context);
//                infoIQ.setType(IQ.Type.SET);
//                infoIQ.setLatitude("116.34356");
//                infoIQ.setLongitude("39.25252");
//                infoIQ.setReqFlag("location");
//                Log.e("1111111111","location");
                break;
            case CmdType.MANUFACTURER:
                infoIQ.setManufacturer(deviceGetter.getManufacturer());
                break;
            case CmdType.MOBILEOPERATOR:
                infoIQ.setMobileOperator(deviceGetter.getProvidersName(context));
                break;
            case CmdType.RAMSIZE:
                infoIQ.setRamSize(deviceGetter.getRamMemory(context));
                break;
            case CmdType.ROMSIZE:
//                long[] rom = deviceGetter.getRomSize();
                long[] size = deviceGetter.getAllSDSize();
                infoIQ.setRomSize(size[0]+";"+size[1]);
                break;
            case CmdType.SCREENLOCK:
                CmdOperate.doScreenLock(deviceInfoIQ, context);
                break;
            case CmdType.SIMFLOW:
                long[] ss = deviceGetter.getTotalBytes();
                infoIQ.setSimFlow(ss[0] + "");
                break;
            case CmdType.WIFIFLOW:
                long[] sss = deviceGetter.getTotalBytes();
                infoIQ.setWifiFlow( sss[2]+ "");
                break;
            case CmdType.WIFIMAC:
                infoIQ.setWifiMac(deviceGetter.getLocalMacAddress(context));
                break;
            case CmdType.UNSTALLAPK:
                deviceSecurity.unInstallApk(deviceInfoIQ.getPackageName(), context);
            case CmdType.APPINFOS:
                ArrayList arrayList = (ArrayList) deviceSecurity.getApp(context);
                infoIQ.setAppInfos(arrayList);
                Log.i("app", arrayList.toString());
            default:

                break;
        }
//      deviceGetter.getResultData(infoIQ);

    }

}
