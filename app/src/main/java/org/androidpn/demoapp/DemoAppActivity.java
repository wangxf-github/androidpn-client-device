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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zs.devicemanager.R;

import org.androidpn.client.ClientService;
import org.androidpn.client.Constants;
import org.androidpn.client.LogUtil;
import org.androidpn.client.ServiceManager;
import org.androidpn.mydevice.DeviceReceiver.MyAdminReceiver;
import org.androidpn.utils.LogUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Properties;

/**
 * This is an androidpn client demo application.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DemoAppActivity extends Activity{

    private SharedPreferences sharedPreferences;
    Button but_login ;
    EditText ip_tx;
    ServiceManager serviceManager ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DemoAppActivity", "onCreate()...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        serviceManager = new ServiceManager(DemoAppActivity.this);
        initView();
        initListener();
        // Start the service
//        ServiceManager serviceManager = new ServiceManager(this);
//        serviceManager.setNotificationIcon(R.drawable.notification);
//        serviceManager.startService();
//        Intent intent = new Intent(DemoAppActivity.this, ClientService.class);
//        startService(intent);
    }

        public void initView(){
            serviceManager.setNotificationIcon(R.drawable.notification);
            but_login = (Button) findViewById(R.id.but_login);
            ip_tx = (EditText) findViewById(R.id.ip_text);
        }

         public void initListener(){
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        serviceManager.stopService();
                    }
                }.start();

                String ip = ip_tx.getText().toString();
                SharedPreferences sharedPrefs =getSharedPreferences(
                        Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(Constants.XMPP_HOST, ip);
                editor.commit();
                Log.i(LogUtil.makeLogTag(DemoAppActivity.class), ip);
                serviceManager.startService();
            }
        });
        }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e("comming", "onStart...");
        sharedPreferences = getSharedPreferences("deviceSharePre", MODE_PRIVATE);
        Boolean isEnable= sharedPreferences.getBoolean("deviceStatus",false);
        Log.e("demo//////////.......", isEnable + "");
        if(isEnable){

        }else {
            Intent intents = new Intent();                                                            // 构造意图
            ComponentName componentName = new ComponentName(this, MyAdminReceiver.class);        // 申请权限
            intents.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);                    // 指定添加系统外设的动作名称
            intents.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);            // 指定给哪个组件授权
            startActivity(intents);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.takeLog(this.getClass(), "onPause...");
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
//        finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.takeLog(this.getClass(), "onRestart...");
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

    private Properties loadProperties() {
        //        InputStream in = null;
        //        Properties props = null;
        //        try {
        //            in = getClass().getResourceAsStream(
        //                    "/org/androidpn/client/client.properties");
        //            if (in != null) {
        //                props = new Properties();
        //                props.load(in);
        //            } else {
        //                Log.e(LOGTAG, "Could not find the properties file.");
        //            }
        //        } catch (IOException e) {
        //            Log.e(LOGTAG, "Could not find the properties file.", e);
        //        } finally {
        //            if (in != null)
        //                try {
        //                    in.close();
        //                } catch (Throwable ignore) {
        //                }
        //        }
        //        return props;

        Properties props = new Properties();
        try {
            int id = getResources().getIdentifier("androidpn", "raw",
                    getPackageName());
            props.load(getResources().openRawResource(id));
        } catch (Exception e) {
            Log.e("ad", "Could not find the properties file.", e);
            // e.printStackTrace();
        }
        return props;
    }
}