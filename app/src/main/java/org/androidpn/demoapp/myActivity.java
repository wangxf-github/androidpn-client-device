package org.androidpn.demoapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.androidpn.mydevice.MyAdminReceiver;

/**
 * Created by S on 2015/8/14.
 */
public class myActivity extends Activity {
    View main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        lock();
        finish();
    }

    public void lock() {
        Log.e("where???", "lock");
        boolean adminActive;
        Intent intent;
        DevicePolicyManager manager= (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE); 	// 获得设备管安全理服务
        ComponentName componentName = new ComponentName(this, MyAdminReceiver.class);		// 申请权限
        adminActive = manager.isAdminActive(componentName); 								// 判断该组件是否有系统管理员的权限
        if (adminActive) {
            Log.e("where???", "active");// 组件具备系统管理员权限
            manager.lockNow();																// 执行锁屏操作
            //	manager.resetPassword(null, 0);												// 设置解锁密码
        } else {
            Log.e("where???", "admin");
            intent = new Intent();															// 构造意图
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);					// 指定添加系统外设的动作名称
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);			// 指定给哪个组件授权
            startActivity(intent);
        }
    }
}
