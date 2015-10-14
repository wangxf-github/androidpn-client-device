package org.androidpn.mydevice.cmd;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.client.DeviceInfoPacketListener;
import org.androidpn.client.XmppManager;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.mydevice.receiver.MyAdminReceiver;
import org.androidpn.utils.DataUtils;
import org.jivesoftware.smack.packet.IQ;

import java.lang.reflect.Array;
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
     * @param cmds
     */
    public void doStrategyMethod(Context context, DeviceInfoIQ deviceInfoIQ,
                                DeviceInfoIQ infoIQ, String cmds,int tag){
        CmdLines cmdLines = getDeviceManagerInstance().getDeviceCmdLine();
        XmppManager xmppManager = DeviceInfoPacketListener.getXmppManager();
        CmdLines deviceCmdLine = getDeviceManagerInstance().getDeviceCmdLine();
        String cmdsStr = null;
        String[] cmdsArrey = null;
        if(tag==CmdType.COLLECTION){
            cmdsArrey= DataUtils.convertStrToArray(cmds,";");
        }else if(tag ==CmdType.LIMITION){
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
        }
        if(cmdsArrey!=null){
            int[] firstcmd = CmdShine.cmdTransfer(cmdsArrey);
            int[] lastcmd = setOrder(firstcmd);
            for (int s:lastcmd
                 ) {
                cmdLines.doMethod(context,deviceInfoIQ,infoIQ,s);
            }
            infoIQ.setType(IQ.Type.SET);
            infoIQ.setReqFlag("strategy");
            xmppManager.getConnection().sendPacket(infoIQ);
        }
    }


    public static int[] setOrder(int[] a) {
        Arrays.sort(a);
        return a;
    }



    /**
     * 执行单一命令
     * @param context
     * @param deviceInfoIQ
     * @param infoIQ
     * @param ints
     */
    public void runMethods(Context context, DeviceInfoIQ deviceInfoIQ,
                           DeviceInfoIQ infoIQ,int[] ints){
        CmdLines cmdLines = getDeviceManagerInstance().getDeviceCmdLine();
        for (int cmd:ints
             ) {
            cmdLines.doMethod(context,deviceInfoIQ, infoIQ,cmd);
        }
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
     * @param deviceInfoIQ
     * @param context
     */
    public static void doWipe(DeviceInfoIQ deviceInfoIQ,Context context){
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
