package org.androidpn.demoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by S on 2015/8/14.
 */
public class myActivity extends Activity implements View.OnTouchListener{
    View main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        window.setAttributes(params);
        setContentView(R.layout.screenlock);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("1", "1111111111111");
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("2",main.getSystemUiVisibility()+"111");
        int i = main.getSystemUiVisibility();
        if(i==View.SYSTEM_UI_FLAG_VISIBLE) {
            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("3","1111111111111");
        return true;
    }

}
