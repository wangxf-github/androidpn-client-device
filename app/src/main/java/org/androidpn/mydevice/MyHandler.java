package org.androidpn.mydevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.androidpn.demoapp.myActivity;

/**
 * Created by S on 2015/8/18.
 */
public class MyHandler extends Handler{
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

