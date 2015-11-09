/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zs.devicemanager.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.zs.devicemanager.R;

import org.androidpn.connection.ClientService;
import com.zs.devicemanager.device.BaseDeviceFunction;
import com.zs.devicemanager.device.DeviceManager;
import com.zs.devicemanager.device.receiver.WifiStateReceiver;
import com.zs.devicemanager.utils.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is an androidpn client demo application.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class StartAppActivity extends BaseDeviceFunction{

    private SharedPreferences sharedPreferences;
    private WifiStateReceiver wifiStateReceiver;
    private DeviceManager deviceManager;
//    private DeviceSecurity deviceSecurity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("StartAppActivity", "onCreate()...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        deviceManager =DeviceManager.getDeviceManagerInstance();
//        deviceSecurity = deviceManager.getDeviceSecurityInstance();
        wifiStateReceiver  =deviceManager.getWifiStateReceiver();
        // Start the service
//        ServiceManager serviceManager = new ServiceManager(this);
//        serviceManager.setNotificationIcon(R.drawable.notification);
//        serviceManager.startService();
//        String[] action = {ConnectivityManager.CONNECTIVITY_ACTION};
//        deviceManager.registReceivers(this,wifiStateReceiver,action);
        Intent intent = new Intent(StartAppActivity.this, ClientService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("comming", "onStart...");
        sharedPreferences = getSharedPreferences("deviceSharePre", MODE_PRIVATE);
        Boolean isEnable = sharedPreferences.getBoolean("deviceStatus",false);
        Log.e("demo//////////.......", isEnable + "");
        if(isEnable){

        }else {
//            Intent intents = new Intent();                                                            // 构造意图
//            ComponentName componentName = new ComponentName(this, MyAdminReceiver.class);        // 申请权限
//            intents.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);                    // 指定添加系统外设的动作名称
//            intents.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);            // 指定给哪个组件授权
//            startActivity(intents);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.makeInfoLog(this.getClass(), "onPause...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.makeInfoLog(this.getClass(), "onResume..");

        sharedPreferences = getSharedPreferences("deviceSharePre", MODE_PRIVATE);
        Boolean isEnable = sharedPreferences.getBoolean("deviceStatus", false);
        Intent resultData = new Intent();
        JSONObject json = new JSONObject();
        try {
            json.put("deviceStatus", isEnable);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        resultData.putExtra("result", json.toString());
        setResult(RESULT_OK, resultData);
        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.makeInfoLog(this.getClass(),"onRestart...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.makeInfoLog(this.getClass(),"onStop..");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.makeInfoLog(this.getClass(), "Destory..");
//        sharedPreferences =null;
    }
}