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
package org.androidpn.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zs.devicemanager.R;

import org.androidpn.utils.LogUtils;

import java.util.Random;

/** 
 * Activity for displaying the notification details view.
 *
 * @author Sehwan Noh (devnoh@gmail.com)
 */
public class NotificationDetailsActivity extends Activity {

    private static final String LOGTAG = LogUtil
            .makeLogTag(NotificationDetailsActivity.class);

    private String callbackActivityPackageName;

    private String callbackActivityClassName;

    ImageView imageView ;
    TextView notify_title;
    TextView notify_des;
    TextView notify_url;

    public NotificationDetailsActivity() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.e("111","onOption");
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Log.e("111","id.........");
            String sa = getRan();
            Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
            intent.putExtra(Constants.NOTIFICATION_ID,sa );
            intent.putExtra(Constants.NOTIFICATION_API_KEY,
                    sa);
            intent
                    .putExtra(Constants.NOTIFICATION_TITLE,
                            sa);
            intent.putExtra(Constants.NOTIFICATION_MESSAGE,
                    sa);
            intent.putExtra(Constants.NOTIFICATION_URI, sa);
            //                intent.setData(Uri.parse((new StringBuilder(
            //                        "notif://notification.androidpn.org/")).append(
            //                        notificationApiKey).append("/").append(
            //                        System.currentTimeMillis()).toString()));

            sendBroadcast(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    Random random = new Random();

    public String  getRan(){
        int max=20;
        int min=10;
        int s = random.nextInt(max)%(max-min+1) + min;
        return s+"";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.takeLog(NotificationDetailsActivity.class,"onCreate....");
        setContentView(R.layout.notifity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.takeLog(NotificationDetailsActivity.class, "onStart....");
        imageView = (ImageView) findViewById(R.id.notify_header);
        notify_title = (TextView) findViewById(R.id.notify_title);
        notify_des = (TextView) findViewById(R.id.notify_des);
        notify_url = (TextView) findViewById(R.id.notify_url);
        SharedPreferences sharedPrefs = this.getSharedPreferences(
                Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        callbackActivityPackageName = sharedPrefs.getString(
                Constants.CALLBACK_ACTIVITY_PACKAGE_NAME, "");
        callbackActivityClassName = sharedPrefs.getString(
                Constants.CALLBACK_ACTIVITY_CLASS_NAME, "");

        Intent intent = getIntent();
        String notificationId = intent
                .getStringExtra(Constants.NOTIFICATION_ID);
        String notificationApiKey = intent
                .getStringExtra(Constants.NOTIFICATION_API_KEY);
        String notificationTitle = intent
                .getStringExtra(Constants.NOTIFICATION_TITLE);
        String notificationMessage = intent
                .getStringExtra(Constants.NOTIFICATION_MESSAGE);
        String notificationUri = intent
                .getStringExtra(Constants.NOTIFICATION_URI);

        notify_title.setText(notificationTitle);
        notify_des.setText(notificationMessage);
        notify_url.setText(notificationUri);
        imageView.setImageResource(R.drawable.pander);

        Log.d(LOGTAG, "notificationId=" + notificationId);
        Log.d(LOGTAG, "notificationApiKey=" + notificationApiKey);
        Log.e(LOGTAG, "notificationTitle=" + notificationTitle);
        Log.e(LOGTAG, "notificationMessage=" + notificationMessage);
        Log.d(LOGTAG, "notificationUri=" + notificationUri);


    }

        protected void onPause() {
            super.onPause();
            LogUtils.takeLog(NotificationDetailsActivity.class,"onPause");
            finish();
        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
