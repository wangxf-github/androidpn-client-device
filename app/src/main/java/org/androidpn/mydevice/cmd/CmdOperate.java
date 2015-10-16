package org.androidpn.mydevice.cmd;

import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothClass;
import android.content.ComponentName;
import android.content.Context;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.client.DeviceInfoPacketListener;
import org.androidpn.client.XmppManager;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceSecurity;
import org.androidpn.mydevice.receiver.MyAdminReceiver;
import org.androidpn.utils.DataUtils;
import org.androidpn.utils.LogUtils;
import org.jivesoftware.smack.packet.IQ;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Saber on 2015/10/10.
 */
public class CmdOperate extends BaseDeviceFunction{


    /**
     * 执行上报信息操作
     * @param context
     * @param deviceInfoIQ
     * @param infoIQ
     */
    public void doMethods(Context context, DeviceInfoIQ deviceInfoIQ,
                                DeviceInfoIQ infoIQ,int tag){
        DeviceManager deviceManager = DeviceManager.getDeviceManagerInstance();
        DeviceSecurity deviceSecurity = deviceManager.getDeviceSecurityInstance();
        CmdLines cmdLines = deviceManager.getDeviceCmdLine();
        XmppManager xmppManager = DeviceInfoPacketListener.getXmppManager();
        String[] cmdsArrey = null;
        String cmds = null;

        switch (tag){
            case CmdType.COLLECTION:
                infoIQ.setReqFlag("strategy");
                cmds = deviceInfoIQ.getDeviceCollection();
                if(cmds==null||cmds==""){
                    break;
                }
                cmdsArrey= DataUtils.convertStrToArray(cmds,";");

                break;
            case CmdType.LIMITION:
                infoIQ.setReqFlag("strategy");
                cmds= deviceInfoIQ.getDeviceLimition();
                if(cmds==null||cmds==""){
                    break;
                }
                Map<String,Boolean> limitMap = new HashMap<String,Boolean>();
                limitMap = DataUtils.convertToMap(cmds);
                Iterator i = limitMap.entrySet().iterator();
                while (i.hasNext()) {
                    int keycount = 0;
                    Object obj = i.next();
                    String key = obj.toString();
                    if(limitMap.get(key)){
                        cmdsArrey[keycount] = key;
                    }else{
                        //TODO 指令取消时的操作
                    }
                }

                break;
            case CmdType.HARDWARESECURITY:
                infoIQ.setReqFlag("hardwareSecurity");
                cmds = deviceInfoIQ.getHardwareSecurity();
                if(cmds==null||cmds==""){
                    break;
                }
                cmdsArrey= DataUtils.convertStrToArray(cmds,";");
                break;
            case CmdType.SECURITY:
                infoIQ.setReqFlag("hardwareSecurity");
                cmds = deviceInfoIQ.getHardwareSecurity();
                if(cmds==null||cmds==""){
                    break;
                }
                cmdsArrey= DataUtils.convertStrToArray(cmds,";");
                break;
            case CmdType.APPINFOS:
                cmds = "appInfos";
                cmdsArrey= DataUtils.convertStrToArray(cmds,";");
                break;
            case CmdType.LOCATION:
                cmds = "location";
                cmdsArrey= DataUtils.convertStrToArray(cmds,";");
                break;
        }
        infoIQ.setType(IQ.Type.SET);
        if(cmdsArrey!=null){
            int[] firstcmd = CmdShine.cmdTransfer(cmdsArrey);
            int[] lastcmd = setOrder(firstcmd);
            for (int s:lastcmd
                    ) {
                cmdLines.doMethod(context,deviceInfoIQ,infoIQ,s);
            }
            if(cmds.equals("location")){

            }else {
                xmppManager.getConnection().sendPacket(infoIQ);
                LogUtils.takeLog(CmdOperate.class, infoIQ.toString());
            }

        }
    }

    /**
     * 把命令集进行优先级排序
     * @param a
     * @return
     */
    public static int[] setOrder(int[] a) {
        Arrays.sort(a);
        return a;
    }



    /**
     * 进行锁屏操作
     * @param deviceInfoIQ
     * @param context
     */
    public static void doScreenLock(DeviceInfoIQ deviceInfoIQ,Context context){
        String pwd = deviceInfoIQ.getPassword();
        if(pwd.equals("null")){
            deviceInfoIQ.setPassword("");
        }

        boolean adminActive;
        DevicePolicyManager manager= (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE); 	// 获得设备管安全理服务
        ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);		// 申请权限
        adminActive = manager.isAdminActive(componentName); 								// 判断该组件是否有系统管理员的权限

        if (adminActive) {
            String password = deviceInfoIQ.getPassword();
            // 执行锁屏操作
            manager.lockNow();
            // 设置解锁密码
            manager.resetPassword(password, 0);
//                  manager.setPasswordExpirationTimeout(componentName,5000);
            //            manager.setMaximumFailedPasswordsForWipe(componentName,10);
//                    manager.setMaximumTimeToLock(componentName, Integer.MAX_VALUE);


        }
    }

    /**
     * 恢复出厂设置
     * @param context
     */
    public static void doWipe(Context context){
        boolean adminActive;
        DevicePolicyManager manager= (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE); 	// 获得设备管安全理服务
        ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);		// 申请权限
        adminActive = manager.isAdminActive(componentName); 								// 判断该组件是否有系统管理员的权限

        if (adminActive) {

            // 执行恢复出厂设置
            manager.wipeData(0);

        }
    }

}
