package org.androidpn.mydevice;

import android.os.Handler;

/**
 * Created by S on 2015/7/27.
 */
public class DeviceManager {


    public static final int BATTERY_INFO =1;
    public static final int AvailRamMemory_INFO = 2;
    public static final int IMEI_INFO = 3;
    public static final int LOCATION_INFO = 4;


    private DeviceReceiver deviceReceiver;
    private DeviceGetter deviceGetter;
    private DeviceInfo deviceInfo;
    private DeviceSecurity deviceSecurity;
    private ScreenLock screenLock;

    /**
     * 获取设备获取器
     * @param handler
     * @return
     */
    public DeviceGetter getDeviceGetterInstance(Handler handler){
        return new DeviceGetter(handler);
    }

    /**
     * 获取设备注册器
     * @param handler
     * @return
     */
    public  DeviceReceiver getDeviceReceiverInstance(Handler handler){
        if(deviceReceiver==null){
            synchronized(DeviceManager.class){
                if(deviceReceiver==null){
                    deviceReceiver=new DeviceReceiver(handler);
                }
            }
        }
        return deviceReceiver;
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
     * 获取设备锁屏器
     */
    public  ScreenLock getDeviceScreenLockInstance(){
        if(screenLock==null){
            synchronized(DeviceManager.class){
                if(screenLock==null){
                    screenLock=new ScreenLock();
                }
            }
        }
        return screenLock;
    }
}
