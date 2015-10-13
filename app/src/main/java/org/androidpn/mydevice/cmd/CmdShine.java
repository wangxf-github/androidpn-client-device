package org.androidpn.mydevice.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 命令映射
 * Created by Saber on 2015/10/13.
 */
public class CmdShine {

    public static Map<String,Integer> cmdShine = new HashMap<String,Integer>();

    {
        cmdShine.put("manufacturer",4001);
        cmdShine.put("specification",4002);
        cmdShine.put("processor",4003);
        cmdShine.put("romSize",4004);
        cmdShine.put("romAvailableSize",4005);
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
        cmdShine.put("ramSize",5001);
        cmdShine.put("batteryStatus",5002);
        cmdShine.put("location",5003);
        cmdShine.put("screenLock",6001);
        cmdShine.put("deviceWipe",6002);


        cmdShine.put("location",5004);
        cmdShine.put("location",5004);



    }

    /**
     * 把命令映射成int类型
     * @param cmds
     * @return
     */
    public  static int[] cmdTransfer(String[] cmds){

        Iterator i = cmdShine.entrySet().iterator();
        int[] ints = new int[cmds.length];
        int j = 0;
        while (i.hasNext()) {
            Object obj = i.next();
            String key = obj.toString();
            j++;
            if(key.equals(cmds[j])){
                int cmdValue = cmdShine.get(key);
                ints[j] = cmdValue;
            }
        }
        return ints;
    }



}
