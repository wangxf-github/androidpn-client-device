package org.androidpn.demoapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by S on 2015/8/14.
 */
public class ScreenLockActivity extends Activity  {

    private MyBroadCastReceiver myBroadCastReceiver;
    private EditText pas;
    private Button but;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBroadCastReceiver = new MyBroadCastReceiver();
        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);//关键代码
        //隐去电池等图标和一切修饰部分（状态栏部分）
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
//                window.setAttributes(params);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.TYPE_STATUS_BAR, WindowManager.LayoutParams.TYPE_STATUS_BAR);
        setContentView(R.layout.screenlock);
        pas = (EditText) findViewById(R.id.pas);
        but = (Button) findViewById(R.id.but);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = pas.getText().toString();
                if (pass.equals("123456")) {
                    ScreenLockActivity.this.finish();
                } else {
                    Toast.makeText(ScreenLockActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志


    public boolean onKeyDown(int keyCode,KeyEvent event){
        Log.e("the case ::",keyCode+"==="+event.toString());
        switch(keyCode){
            case KeyEvent.KEYCODE_HOME:return true;
            case KeyEvent.KEYCODE_BACK:return true;
            case KeyEvent.KEYCODE_CALL:return true;
            case KeyEvent.KEYCODE_SYM: return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN: return true;
            case KeyEvent.KEYCODE_VOLUME_UP: return true;
            case KeyEvent.KEYCODE_STAR: return true;
            case KeyEvent.KEYCODE_MENU:return true;
            case KeyEvent.KEYCODE_POWER:return true;
            case KeyEvent.KEYCODE_NOTIFICATION:return true;
            case KeyEvent.KEYCODE_SEARCH:return true;

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        Log.e("===============", "I'm coming, myBroadCastReceiver注册了!");
        registerReceiver(myBroadCastReceiver, intentFilter);
    }

    private class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //你自己先把 reasons == homekey 和 长按homekey 排除，剩下的做下面的处理
            String reason = intent.getStringExtra("reason");
            if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)){
                Log.e("===============", "Intent.ACTION_CLOSE_SYSTEM_DIALOGS : " + intent.getStringExtra("reason"));

                if (intent.getExtras()!=null && intent.getExtras().getBoolean("myReason")){
                    myBroadCastReceiver.abortBroadcast();
                }else if (reason != null){

                    if (reason.equalsIgnoreCase("globalactions")){

                        //屏蔽电源长按键的方法：
                        Intent myIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                        myIntent.putExtra("myReason", true);
                        context.sendOrderedBroadcast(myIntent, null);
                        Log.e("===============","电源  键被长按");

                    }else if (reason.equalsIgnoreCase("homekey")){

                        //屏蔽Home键的方法
                        //在这里做一些你自己想要的操作,比如重新打开自己的锁屏程序界面，这样子就不会消失了
                        Log.e("===============", "Home 键被触发");

                    }else if (reason.equalsIgnoreCase("recentapps")){

                        //屏蔽Home键长按的方法
                        Log.e("===============", "Home 键被长按");
                    }
                }
            }
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        Log.e("===============", "I get out, myBroadCastReceiver注销了!");
        unregisterReceiver(myBroadCastReceiver);
    }

}
