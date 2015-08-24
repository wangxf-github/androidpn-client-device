package org.androidpn.mydevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import org.androidpn.mydevice.DeviceReceiver.BatteryReceiver;
import org.androidpn.mydevice.DeviceReceiver.BootReceiver;
import org.androidpn.mydevice.DeviceReceiver.MobileStatesReceiver;
import org.androidpn.mydevice.DeviceReceiver.WifiStateReceiver;

/**
 * Created by S on 2015/7/27.
 */
public class DeviceManager {


    public static final int BATTERY_INFO =1;
    public static final int AvailRamMemory_INFO = 2;
    public static final int IMEI_INFO = 3;
    public static final int LOCATION_INFO = 4;
    public static final int SCREEN_LOCK = 5;
    public static final int WIPE_DATA = 6;
    public static final int  INSTALL_APK = 7;
    public static final int  UNINSTALL_APK = 8;


    private DeviceInfo deviceInfo;
    private DeviceSecurity deviceSecurity;
    private BatteryReceiver batteryReceiver ;
    private BootReceiver bootReceiver;
    private WifiStateReceiver wifiStateReceiver;
    private MobileStatesReceiver mobileStatesReceiver;

    /**
     * 获取设备获取器
     * @return
     */
    public DeviceGetter getDeviceGetterInstance(){
        return new DeviceGetter();
    }

    /**
     * 获取设备信息管理器
     * @return
     */
    public  DeviceInfo getDeviceInfoInstance(){
        if(deviceInfo==null){
            synchronized(DeviceManager.class){
                if(deviceInfo==null){
                    deviceInfo=new DeviceInfo();
                }
            }
        }
        return deviceInfo;
    }

    /**
     * 获取设备安全器
     */
    public  DeviceSecurity getDeviceSecurityInstance(){
        if(deviceSecurity==null){
            synchronized(DeviceManager.class){
                if(deviceSecurity==null){
                    deviceSecurity=new DeviceSecurity();
                }
            }
        }
        return deviceSecurity;
    }


    /**
     * 注册receiver
     */
    public void registReceivers(Context activity,BroadcastReceiver receiver,String[] action){
        IntentFilter filter = new IntentFilter();
        for(int i = 0 ;i<action.length;i++) {
            filter.addAction(action[i]);
        }
        activity.registerReceiver(receiver, filter);
        Log.e("reveiver", "I am resgist");
    }
    /**
     * 注销receiver
     */
    public void unRegistReceivers(Context activity,BroadcastReceiver receiver){

        activity.unregisterReceiver(receiver);
        Log.e("reveiver", "I am unresgist");

    }

    /**
     * 获取电池的注册器
     * @return
     */
    public BatteryReceiver getBatteryReceiver(){
        if(batteryReceiver==null){
            synchronized(DeviceManager.class){
                if(batteryReceiver==null){
                    batteryReceiver=new BatteryReceiver();
                }
            }
        }
        return batteryReceiver;
    }


    /**
     * 获取应用卸载安装的注册器
     * @return
     */
    public BootReceiver getBootReceiver(){
        if(bootReceiver==null){
            synchronized(DeviceManager.class){
                if(bootReceiver==null){
                    bootReceiver=new BootReceiver();
                }
            }
        }
        return bootReceiver;
    }


    /**
     * 获取wifi状态的广播
     * @return
     */
    public WifiStateReceiver getWifiStateReceiver(){
        if(wifiStateReceiver==null){
            synchronized(DeviceManager.class){
                if(wifiStateReceiver==null){
                    wifiStateReceiver=new WifiStateReceiver();
                }
            }
        }
        return wifiStateReceiver;
    }

    /**
     * 获取移动网络状态的广播
     * @return
     */
    public MobileStatesReceiver getMobileStatesReceiver(){
        if(mobileStatesReceiver==null){
            synchronized(DeviceManager.class){
                if(mobileStatesReceiver==null){
                    mobileStatesReceiver=new MobileStatesReceiver();
                }
            }
        }
        return mobileStatesReceiver;
    }
}
