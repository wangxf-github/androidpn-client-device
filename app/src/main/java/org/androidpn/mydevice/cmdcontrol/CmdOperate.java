package org.androidpn.mydevice.cmdcontrol;

import android.content.Context;
import android.content.pm.PackageManager;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.client.DeviceInfoPacketListener;
import org.androidpn.client.XmppManager;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.utils.DataUtils;

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
        CmdLines deviceCmdLine = getDeviceManagerInstance().getDeviceCmdLine();
        String cmdsStr = null;
        String[] cmdsArrey = null;
        if(tag==CmdType.COLLECTION_CMD){
            cmdsArrey= DataUtils.convertStrToArray(cmds,";");
        }else if(tag ==CmdType.LIMITION_CMD){
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
            for (String cmd:cmdsArrey ) {
                deviceCmdLine.doMethods(context,deviceInfoIQ,infoIQ, cmd);
            }
        }
    }
}
