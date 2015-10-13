package org.androidpn.mydevice.cmd;

import android.content.Context;

import org.androidpn.client.DeviceInfoIQ;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.utils.DataUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Saber on 2015/10/10.
 */
public class CmdOperate extends BaseDeviceFunction{

    CmdLines cmdLines = getDeviceManagerInstance().getDeviceCmdLine();
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
            int[] lastcmd = quickSort(firstcmd,0,firstcmd.length);

        }
    }

    /**
     * 快速排序
     * @param a
     * @param low
     * @param height
     * @return
     */
    public static int partition(int a[], int low, int height) {
        int key = a[low];
        while (low < height) {
            while (low < height && a[height] >= key)
                height--;
            a[low] = a[height];
            while (low < height && a[low] <= key)
                low++;
            a[height] = a[low];
        }
        a[low] = key;
        return low;
    }

    public static int[] quickSort(int a[], int low, int height) {
        if (low < height) {
            int result = partition(a, low, height);
            quickSort(a, low, result - 1);
            quickSort(a, result + 1, height);
        }
        return a;
    }

    public void runMethods(Context context, DeviceInfoIQ deviceInfoIQ,
                           DeviceInfoIQ infoIQ,int[] ints){
        for (int cmd:ints
             ) {
            cmdLines.doMethod(context,deviceInfoIQ, infoIQ,cmd);
        }
    }
}
