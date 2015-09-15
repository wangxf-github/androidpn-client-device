package org.androidpn.utils;

import android.util.Log;

/**
 * Created by Saber on 2015/9/15.
 */
public class LogUtils {
    public static void takeLog(Class clazz,String str){
        Log.e(clazz.getSimpleName(),str);
    }
}
