package com.zs.devicemanager.utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by Saber on 2015/9/15.
 */
public class LogUtils {


    /**
     * debug
     * @param context
     * @param str
     */
    public static void makeDebugLog(Class context,String str){
        String tag =  "com.zs.devicemanager_" + context.getClass().getSimpleName()+":";
        Log.d(tag,str);
    }

    /**
     * verbose
     * @param context
     * @param str
     */
    public static void makeVerboseLog(Class context,String str){
        String tag =  "com.zs.devicemanager_" + context.getClass().getSimpleName()+":";
        Log.v(tag,str);
    }

    /**
     * info
     * @param context
     * @param str
     */
    public static void makeInfoLog(Class context,String str){
        String tag =  "com.zs.devicemanager_" + context.getClass().getSimpleName()+":";
        Log.i(tag,str);
    }

    /**
     * error
     * @param context
     * @param str
     */
    public static void makeErroLog(Class context,String str){
        String tag =  "com.zs.devicemanager_" + context.getClass().getSimpleName()+":";
        Log.e(tag,str);
    }

    /**
     * warn
     * @param context
     * @param str
     */
    public static void makeWarnLog(Class context,String str){
        String tag = "com.zs.devicemanager_" + context.getClass().getSimpleName()+":";
        Log.w(tag,str);
    }
}


