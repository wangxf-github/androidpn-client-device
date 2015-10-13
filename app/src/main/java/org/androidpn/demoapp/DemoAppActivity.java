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
package org.androidpn.demoapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.zs.devicemanager.R;

import org.androidpn.client.ClientService;
import org.androidpn.client.ServiceManager;
import org.androidpn.client.XmppManager;
import org.androidpn.mydevice.BaseDeviceFunction;
import org.androidpn.mydevice.DeviceGetter;
import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceReceiver.MyAdminReceiver;
import org.androidpn.mydevice.DeviceReceiver.WifiStateReceiver;
import org.androidpn.mydevice.DeviceSecurity;
import org.androidpn.utils.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is an androidpn client demo application.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DemoAppActivity extends BaseDeviceFunction{

    private SharedPreferences sharedPreferences;
    private WifiStateReceiver wifiStateReceiver;
    private DeviceManager deviceManager;
//    private DeviceSecurity deviceSecurity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DemoAppActivity", "onCreate()...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        deviceManager =getDeviceManagerInstance() ;
//        deviceSecurity = deviceManager.getDeviceSecurityInstance();
        wifiStateReceiver  =deviceManager.getWifiStateReceiver();
        // Start the service
//        ServiceManager serviceManager = new ServiceManager(this);
//        serviceManager.setNotificationIcon(R.drawable.notification);
//        serviceManager.startService();
//        String[] action = {ConnectivityManager.CONNECTIVITY_ACTION};
//        deviceManager.registReceivers(this,wifiStateReceiver,action);
        Intent intent = new Intent(DemoAppActivity.this, ClientService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("comming", "onStart...");
        sharedPreferences = getSharedPreferences("deviceSharePre", MODE_PRIVATE);
        Boolean isEnable = sharedPreferences.getBoolean("deviceStatus",false);
        Log.e("demo//////////.......",isEnable+"");
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
        LogUtils.takeLog(this.getClass(),"onPause...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.takeLog(this.getClass(), "onResume..");

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
        LogUtils.takeLog(this.getClass(),"onRestart...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.takeLog(this.getClass(),"onStop..");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.takeLog(this.getClass(), "Destory..");
//        sharedPreferences =null;
    }
}