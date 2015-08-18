package org.androidpn.mydevice;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by S on 2015/8/17.
 */
public class ScreenLock {

    private DevicePolicyManager manager;
    private boolean adminActive;
    private Intent intent;
    public ScreenLock(){
    }
    /**
     * 锁屏
     */
    public void lockScreen(Context context) {
        manager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE); 	// 获得设备管安全理服务
        ComponentName componentName = new ComponentName(context.getApplicationContext(), MyAdminReceiver.class);		// 申请权限
        adminActive = manager.isAdminActive(componentName); 								// 判断该组件是否有系统管理员的权限
        if (adminActive) {																	// 组件具备系统管理员权限
            manager.lockNow();																// 执行锁屏操作
            Log.e("qqqqqq","wwwwwww");
         //   manager.resetPassword("asd", 0);												// 设置解锁密码
        } else {
            Log.e("aaaaaaaaaa","sssssssss");
            intent = new Intent();															// 构造意图
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);					// 指定添加系统外设的动作名称
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);			// 指定给哪个组件授权
            context.startActivity(intent);
        }
    }
}
