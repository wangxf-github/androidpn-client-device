package org.androidpn.utils;


import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Saber on 2015/10/9.
 */
public class DataUtils {
    //使用String的split 方法
    public static String[] convertStrToArray(String str,String tag){
        String[] strArray = null;
        strArray = str.split(tag); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    public static Map convertToMap(String jsonStr) {
        HashMap localHashMap = new HashMap();
        if(jsonStr!=null&&jsonStr!=""){
            JSONObject localJSONObject = JSONObject.fromObject(jsonStr);
            Iterator localIterator = localJSONObject.keys();
            while (localIterator.hasNext()) {
                Object localObject = localIterator.next();
                localHashMap.put(localObject, localJSONObject.get(localObject));
            }
        }else{
            return null;
        }
        return localHashMap;
    }

}
