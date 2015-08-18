package org.androidpn.mydevice;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by S on 2015/7/27.
 */
public class BaseDeviceActivity extends Activity {

    private  static DeviceManager manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        viewListener();
    }

    /**
     * 初始化数据
     */
    public void initData(){

    }
    /**
     * 初始化布局
     */
    public void initView(){

    }
    /**
     * 事件监听
     */
    public void viewListener(){

    }
    /**
     * 获取设备管理器
     * @return
     */
    public static  DeviceManager getDeviceManager(){
        if(manager==null){
            synchronized(DeviceManager.class){
                if(manager==null){
                    manager=new DeviceManager();
                }
            }
        }
        return manager;
    }

}
