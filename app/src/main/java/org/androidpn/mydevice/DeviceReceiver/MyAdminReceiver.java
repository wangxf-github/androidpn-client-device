package org.androidpn.mydevice.DeviceReceiver;

/**
 * Created by S on 2015/8/20.
 */

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.androidpn.client.LogUtil;

/**
 * 权限广播
 */
public class MyAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.e(LogUtil.makeLogTag(MyAdminReceiver.class), intent.getAction().toString());
    }

//    @Override
//    public CharSequence onDisableRequested(Context context, Intent intent) {
//        Log.e(LogUtil.makeLogTag(MyAdminReceiver.class), "onDisable............");
//        // 这里处理 不可编辑设备。
//
//        Intent intent1 = context.getPackageManager().getLaunchIntentForPackage("com.android.settings");
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent1);
//        WindowManager.LayoutParams wmParams;
//        final WindowManager mWindowManager;
//        wmParams = new WindowManager.LayoutParams();
//        mWindowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        wmParams.format = PixelFormat.RGBX_8888;
//        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
//        wmParams.alpha = 0;
//        wmParams.x = 0;
//        wmParams.y = 0;
//        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//        final View contentView = new Button(context);
//        mWindowManager.addView(contentView, wmParams);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(7000);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                mWindowManager.removeView(contentView);
//            }
//        }).start();
//        return "这个真心取消不了，看见你也点不了！！！";
//
//    }


    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.e(LogUtil.makeLogTag(MyAdminReceiver.class), "onEnabled............");
        SharedPreferences sharedPreferences = context.getSharedPreferences("deviceSharePre",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean("deviceStatus",true);
        editor.commit();
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        Log.e(LogUtil.makeLogTag(MyAdminReceiver.class), "onDisEnabled............");
        SharedPreferences sharedPreferences = context.getSharedPreferences("deviceSharePre",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sharedPreferences.edit();
        editor.putBoolean("deviceStatus",false);
        editor.commit();
    }

}
