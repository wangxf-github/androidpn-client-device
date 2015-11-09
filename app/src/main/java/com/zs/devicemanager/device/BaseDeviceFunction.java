package com.zs.devicemanager.device;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by S on 2015/7/27.
 */
public class BaseDeviceFunction extends Activity {

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



}
