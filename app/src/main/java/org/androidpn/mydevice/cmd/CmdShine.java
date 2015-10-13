package org.androidpn.mydevice.cmd;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 命令映射
 * Created by Saber on 2015/10/13.
 */
public class CmdShine {

    public static Map<String,Integer> cmdShine = new HashMap<String,Integer>();


    public  static Integer cmdTransfer(String cmd){

        Iterator i = cmdShine.entrySet().iterator();
        while (i.hasNext()) {
            Object obj = i.next();
            String key = obj.toString();
            if(key.equals(cmd)){
                int cmdValue = cmdShine.get(key);
                return cmdValue;
            }
        }
        return null;
    }

}
