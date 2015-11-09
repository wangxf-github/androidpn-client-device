package com.zs.devicemanager.device.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by S on 2015/8/20.
 * 移动网络开关的广播
 */
public class MobileStatesReceiver extends BroadcastReceiver {

    ConnectivityManager mConnectivityManager;

    @Override
    public void onReceive(Context context, Intent intent) {
       mConnectivityManager =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络服务
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取移动网络服务
        NetworkInfo gprs = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (!gprs.isConnected() ) {
            // network closed
            Log.i("mobile status.....", "duan kai le ......");
        } else {
            // network opend
            Log.i("mobile status.....", "lian jie  le ......");
        setMobileNetEnable(false);
        }
    }



    /**
     * 控制移动网络
     * setMobileNetEnable方法在connectivityManager中是隐藏的，必须反射获取
     * @param isOpen 是否开启移动网络
     */
    public final void setMobileNetEnable(boolean isOpen){

        Object[] arg = null;
        try {
            boolean isMobileDataEnable = invokeMethod("getMobileDataEnabled", arg);
            if(isMobileDataEnable) {
                invokeBooleanArgMethod("setMobileDataEnabled", isOpen);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 反射获取ConnectivityManager的setMobileDataEnabled方法
     * @param methodName  获取的方法名
     * @param arg  方法内的参数
     * @return
     * @throws Exception
     */
    public boolean invokeMethod(String methodName,
                                Object[]  arg) throws Exception {

        Class ownerClass = mConnectivityManager.getClass();

        Class[]  argsClass = null;
        if (arg != null) {
            argsClass = new Class[1];
            argsClass[0] = arg.getClass();
        }

        Method method = ownerClass.getMethod(methodName, argsClass);

        Boolean isOpen = (Boolean) method.invoke(mConnectivityManager, arg);

        return isOpen;
    }

    //运行反射获取的方法
    public Object invokeBooleanArgMethod(String methodName,
                                         boolean value) throws Exception {

        Class ownerClass = mConnectivityManager.getClass();

        Class[]  argsClass = new Class[1];
        argsClass[0] = boolean.class;

        Method method = ownerClass.getMethod(methodName, argsClass);

        return method.invoke(mConnectivityManager, value);
    }

}
