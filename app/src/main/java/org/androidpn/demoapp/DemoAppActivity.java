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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.androidpn.client.ServiceManager;
import org.androidpn.mydevice.BaseDeviceActivity;
import org.androidpn.mydevice.DeviceSecurity;

/**
 * This is an androidpn client demo application.
 * 
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class DemoAppActivity extends BaseDeviceActivity {

    private DeviceSecurity deviceSecurity = getDeviceManager().getDeviceSecurityInstance();
    MyHandler handler ;

    public class MyHandler extends Handler {
        Activity activity;
        public MyHandler(BaseDeviceActivity activity){}
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("where???","handler....");
            Intent intent = new Intent(activity,myActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DemoAppActivity", "onCreate()...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Settings
        Button okButton = (Button) findViewById(R.id.btn_settings);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ServiceManager.viewNotificationSettings(DemoAppActivity.this);
//                Intent intent = new Intent(DemoAppActivity.this,ScreenLockActivity.class);
//                Intent intent = new Intent(DemoAppActivity.this,myActivity.class);
             //   DemoAppActivity.this.startActivity(intent);
//                Intent inten = new Intent("com.TEST");
//                startActivity(inten);
         //       deviceSecurity.masterClear(DemoAppActivity.this) ;
            }
        });

        // Start the service
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();
    }
    public  MyHandler getHandlerInstance(){
        if(handler==null){
            synchronized(DemoAppActivity.class){
                if(handler==null){
                    handler=new MyHandler(DemoAppActivity.this);
                }
            }
        }
        return handler;
}

}