package org.androidpn.mydevice.cmdcontrol;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.client.DeviceInfoPacketListener;
import org.androidpn.client.XmppManager;
import org.androidpn.mydevice.AppInfo;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.mydevice.DeviceGetter;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceSecurity;
import org.jivesoftware.smack.packet.IQ;

import java.util.List;

import mylocation.GetLocation;

/**
 * Created by Saber on 2015/10/9.
 */
public class CmdLines extends BaseDeviceFunction {

    /**
     * 执行单个指令
     * @param context
     * @param deviceInfoIQ
     * @param infoIQ
     * @param cmd
     */
    public void doMethods(Context context, DeviceInfoIQ deviceInfoIQ,
                          DeviceInfoIQ infoIQ, String cmd) {
        DeviceManager deviceManager = getDeviceManagerInstance();
        PackageManager packageManager = getPackageManager();
        DeviceSecurity deviceSecurity = deviceManager.getDeviceSecurityInstance();
        DeviceGetter deviceGetter = deviceManager.getDeviceGetterInstance();
        XmppManager xmppManager = DeviceInfoPacketListener.getXmppManager();
//        PackageManager packageManager = context.getPackageManager();
//        if("limitscreen".equals(cmd)){
//            infoIQ.setLimitscreen(true);
//        }
//        if("limitcamera".equals(cmd)){
//            infoIQ.setLimitcamera(true);
//        }
//        if("limitapplication".equals(cmd)){
//            infoIQ.setLimitapplication(true);
//        }
        if("batteryStatus".equals(cmd)){
            infoIQ.setBatteryStatus("99%");
        }
        if("camera".equals(cmd)){
            infoIQ.setCamera("索尼生产，1800w像素");
        }
        if("deviceOS".equals(cmd)){
            infoIQ.setDeviceOS("android 4.4.4");
        }
        if ("location".equals(cmd)) {
            //获取地理位置
            new GetLocation(context);
//                    String[] action = {ConnectivityManager.CONNECTIVITY_ACTION};
//                     deviceManager.registReceivers(context,wifiStateReceiver,action);
//                        deviceManager.registReceivers(context, mobileStatesReceiver, action);
        } else if ("validate".equals(cmd)) {
            infoIQ.setType(IQ.Type.SET);
            infoIQ.setReqFlag("validate");
            infoIQ.setDeviceOS(deviceGetter.getVersion()[3] + " " + deviceGetter.getVersion()[1]);
            infoIQ.setIsRoot(deviceGetter.isRoot() + "");
            infoIQ.setImsiNo(deviceGetter.getImsi(context));
            xmppManager.getConnection().sendPacket(infoIQ);
        } else if ("screenLock".equals(cmd)) {
            //当password的值是null时，表示解锁
            if ("null".equals(deviceInfoIQ.getPassword())) {
                deviceInfoIQ.setPassword("");
            }
            infoIQ.setType(IQ.Type.SET);
            infoIQ.setReqFlag("screenLock");

            //锁屏及修改密码
//            deviceLockOrWipe(DeviceManager.SCREEN_LOCK, deviceInfoIQ.getPassword());

            if ("null".equals(deviceInfoIQ.getPassword())) {
                infoIQ.setIsLocked("0");
            } else {
                infoIQ.setIsLocked("1");
            }

            xmppManager.getConnection().sendPacket(infoIQ);
        } else if ("deleteApp".equals(cmd)) {
            //删除数据
//                        deviceSecurity.deleteFile(context, Environment.getExternalStorageDirectory().getAbsoluteFile());

            //卸载应用
//                        String[] action = {Intent.ACTION_PACKAGE_ADDED,Intent.ACTION_PACKAGE_REMOVED};
//                        deviceManager.registReceivers(context,bootReceiver,action);

//                        if(!deviceSecurity.isInstall(packageManager,deviceInfoIQ.getAppPackage())) {
//                            infoIQ.setType(IQ.Type.SET);
//                            infoIQ.setIsWiped("0");
//                            infoIQ.setReqFlag("deleteApp");
//                            xmppManager.getConnection().sendPacket(infoIQ);
//                        }else{
//                            boolean flag = deviceSecurity.clientUninstall(deviceInfoIQ.getAppPackage());
//                        }

        } else if ("deviceWipe".equals(cmd)) {
            //删除数据
            deviceSecurity.deleteFile(context, Environment.getExternalStorageDirectory().getAbsoluteFile());

            //卸载应用
//                        String[] action = {Intent.ACTION_PACKAGE_ADDED,Intent.ACTION_PACKAGE_REMOVED};
//                        deviceManager.registReceivers(context,bootReceiver,action);

            if (!deviceSecurity.isInstall(packageManager, "移动展业包名")) {
                infoIQ.setType(IQ.Type.SET);
                infoIQ.setReqFlag("deviceWipe");
                infoIQ.setIsWiped("0");
                xmppManager.getConnection().sendPacket(infoIQ);
            } else {
                boolean flag = deviceSecurity.clientUninstall("移动展业包名");
            }

        } else if ("app".equals(cmd)) {
            List<AppInfo> appInfos = deviceSecurity.getApp(context);
            infoIQ.setType(IQ.Type.SET);
            infoIQ.setReqFlag("appInfo");
            infoIQ.setAppInfos(appInfos);
            xmppManager.getConnection().sendPacket(infoIQ);
        } else if ("imei".equals(cmd)) {
            infoIQ.setImei(deviceGetter.getIMEI(context));
        } else if ("battery".equals(cmd)) {
            deviceGetter.getBatteryInfo(context);
        } else if ("romsize".equals(cmd)) {
            long[] romsize = deviceGetter.getRomSize();
            infoIQ.setRomSize(romsize[0] + "," + romsize[1]);
        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        } else if ("appInfo".equals(cmd)) {

        }
    }
}
