package com.zs.devicemanager.device.cmd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 命令映射
 * Created by Saber on 2015/10/13.
 */
public class CmdShine {

    public static Map<String,Integer> cmdShine = new HashMap<String,Integer>();

    public static void initData()
    {
        cmdShine.put("batteryStatus",4001);
        cmdShine.put("ramSize",4002);
       cmdShine.put("processor",4003);
        cmdShine.put("romSize",4004);
        cmdShine.put("appInfos",4005);
        cmdShine.put("displaySize",4006);
        cmdShine.put("blueToothMac",4007);
        cmdShine.put("wifiMac",4008);
        cmdShine.put("imei",4009);
        cmdShine.put("deviceOS",4010);
        cmdShine.put("isRoot",4011);
        cmdShine.put("isLock",4012);
        cmdShine.put("imsiNo",4013);
        cmdShine.put("mobileOperator",4014);
        cmdShine.put("deviceMobileNo",4015);
        cmdShine.put("isRoaming",4016);
        cmdShine.put("simFlow",4017);
        cmdShine.put("wifiFlow",4018);
        cmdShine.put("manufacturer", 4019);
        cmdShine.put("camera",4020);
        cmdShine.put("phoneModel",4021);



        cmdShine.put("screenLock",6001);
        cmdShine.put("deviceWipe",6002);
        cmdShine.put("uninstallapp",6003);
        cmdShine.put("resetpassword",6004);

        cmdShine.put("security", 9004);
        cmdShine.put("hardwareSecurity", 9003);
        cmdShine.put("location",9005);



    }

    /**
     * 把命令映射成int类型
     * @param cmds
     * @return
     */
    public  static int[] cmdTransfer(String[] cmds){
        initData();

        int[] ints = new int[cmds.length];
        for (int j=0;j<cmds.length;j++) {
            Iterator i = cmdShine.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry entry = (Map.Entry) i.next();
                String key = (String) entry.getKey();
                if (key.equals(cmds[j])) {
                    int cmdValue = cmdShine.get(key);
                    ints[j] = cmdValue;
                    break;
                }
            }
            }
        return ints;
    }

    /**
     * 单个命令转换成int
     * @param cmd
     * @return
     */
    public static int cmdToInt(String cmd){
        initData();
        Iterator i = cmdShine.entrySet().iterator();
        int s = CmdType.NOCMD;
        while (i.hasNext()) {
            Map.Entry entry = (Map.Entry) i.next();
            String key = (String) entry.getKey();
            if(key.equals(cmd)){
                int cmdValue = cmdShine.get(key);
                s= cmdValue;
            }
        }
        return s;
    }


}
