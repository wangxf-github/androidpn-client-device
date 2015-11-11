package com.zs.devicemanager.device.cmd;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.zs.devicemanager.model.DeviceInfoIQ;
import com.zs.devicemanager.device.DeviceInfoPacketListener;
import org.androidpn.connection.XmppManager;
import com.zs.devicemanager.device.BaseDeviceFunction;
import com.zs.devicemanager.device.DeviceGetter;
import com.zs.devicemanager.device.DeviceManager;
import com.zs.devicemanager.device.receiver.MyAdminReceiver;
import com.zs.devicemanager.utils.DataUtils;
import com.zs.devicemanager.utils.LogUtils;
import org.jivesoftware.smack.packet.IQ;

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
        DeviceGetter deviceGetter = deviceManager.getDeviceGetterInstance();
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
            default:
                cmdLines.doMethod(context,deviceInfoIQ,infoIQ,tag);
                xmppManager.getConnection().sendPacket(infoIQ);
                LogUtils.makeErroLog(CmdOperate.class, infoIQ.toString());
        }
        infoIQ.setType(IQ.Type.SET);
        if(cmdsArrey!=null){
            int[] firstcmd = CmdShine.cmdTransfer(cmdsArrey);
            int[] lastcmd = setOrder(firstcmd);
            for (int s:lastcmd
                    ) {
                cmdLines.doMethod(context,deviceInfoIQ,infoIQ,s);
            }
            if(cmds.contains("batteryStatus")){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                deviceGetter.destoryInitData(context);
            }
                    xmppManager.getConnection().sendPacket(infoIQ);
                    LogUtils.makeErroLog(CmdOperate.class, infoIQ.toString());


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
        Log.i("password",pwd);

        if(pwd.equals("null")||pwd.equals("")||pwd.equals(null)){
            deviceInfoIQ.setPassword("");
        }
        String pwds = deviceInfoIQ.getPassword();
        SharedPreferences preferences = context.getSharedPreferences("deviceInfo", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("screenpassword",pwds);
        edit.commit();

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

    /**
     * 修改密码
     */
    public static void resetPassword(DeviceInfoIQ deviceInfoIQ,Context context){
        String pwd = deviceInfoIQ.getPassword();
        SharedPreferences preferences = context.getSharedPreferences("deviceInfo", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("screenpassword",pwd);
        edit.commit();
        if(pwd.equals("null")||pwd.equals("")||pwd.equals(null)){
            deviceInfoIQ.setPassword("");
        }

        boolean adminActive;
        DevicePolicyManager manager= (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE); 	// 获得设备管安全理服务
        ComponentName componentName = new ComponentName(context, MyAdminReceiver.class);		// 申请权限
        adminActive = manager.isAdminActive(componentName); 								// 判断该组件是否有系统管理员的权限

        if (adminActive) {
            String password = deviceInfoIQ.getPassword();
            String passwordExpiration = deviceInfoIQ.getPasswordExpiration();
            long pwdEx = 0;
            // 设置解锁密码
            manager.resetPassword(password, 0);
            if(passwordExpiration!=null||passwordExpiration!=""){
                pwdEx = (long)Integer.getInteger(passwordExpiration);
            }
            //设置密码有效期
            manager.setPasswordExpirationTimeout(componentName, pwdEx);
            manager.setMaximumFailedPasswordsForWipe(componentName,3);

        }
    }

    /**
     * 想服务端发出请求
     * @param deviceInfoIQ
     */
    public static  void sendRequest(DeviceInfoIQ deviceInfoIQ){
        XmppManager xmppManager = DeviceInfoPacketListener.getXmppManager();
        deviceInfoIQ = new DeviceInfoIQ();
         xmppManager.getConnection().sendPacket(deviceInfoIQ);
    }

}
