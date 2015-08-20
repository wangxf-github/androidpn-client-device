package org.androidpn.demoapp;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.androidpn.mydevice.DeviceManager;
import org.androidpn.mydevice.DeviceReceiver.MyAdminReceiver;


/**
 * Created by S on 2015/8/14.
 */
public class ScreenLockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取传递过来的数据
        Bundle bundle = getIntent().getExtras();
        String password = bundle.getString("password");
        int tag = bundle.getInt("tag");
        lock(tag,password);
        finish();
    }

    /**
     * 锁屏
     * @param tag
     * @param password
     */
    public void lock(int tag,String password) {
        Log.e("where???", "lock");
        boolean adminActive;
        Intent intent;
        DevicePolicyManager manager= (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE); 	// 获得设备管安全理服务
        ComponentName componentName = new ComponentName(this, MyAdminReceiver.class);		// 申请权限
        adminActive = manager.isAdminActive(componentName); 								// 判断该组件是否有系统管理员的权限

        if (adminActive) {
            switch (tag){
                case DeviceManager.SCREEN_LOCK:
                    // 执行锁屏操作
                    manager.lockNow();
                    // 设置解锁密码
                    manager.resetPassword(password, 0);
//                    manager.setPasswordExpirationTimeout(componentName,5000);
                    manager.setMaximumFailedPasswordsForWipe(componentName,10);
                    manager.setMaximumTimeToLock(componentName, 10000);
                    ScreenLockActivity.this.finish();
                    break;
                case DeviceManager.WIPE_DATA:
                    //恢复出厂设置
                    manager.wipeData(0);
                    break;
                default:
                    Log.e("Tag erro","tag错误");
            }

        } else {
            intent = new Intent();															// 构造意图
            intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);					// 指定添加系统外设的动作名称
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);			// 指定给哪个组件授权
            startActivity(intent);
        }
    }
}
