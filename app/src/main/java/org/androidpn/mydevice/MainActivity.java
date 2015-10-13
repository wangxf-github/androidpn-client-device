package org.androidpn.mydevice;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zs.devicemanager.R;

import org.androidpn.client.ClientService;
import org.androidpn.demoapp.ScreenLockActivity;
import org.androidpn.mydevice.receiver.BatteryReceiver;
import org.androidpn.mydevice.receiver.BootReceiver;
import org.androidpn.mydevice.receiver.MobileStatesReceiver;
import org.androidpn.mydevice.receiver.WifiStateReceiver;


public class MainActivity extends BaseDeviceFunction {

    StringBuilder str = null;
    Button button;
    EditText password;
    Button button_zhan;
    DeviceInfo deviceInfo;
    Activity ac;
    DeviceGetter deviceGetter;
    TextView textView;
    private DeviceSecurity deviceSecurity ;
    private DeviceManager deviceManager;
    private BatteryReceiver batteryReceiver;
    private BootReceiver bootReceiver;
    private WifiStateReceiver wifiStateReceiver;
    private MobileStatesReceiver mobileStatesReceiver;
    private PackageManager packageManager;
    private Uri packageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(MainActivity.this, ClientService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);
    }


    public void initData() {
        deviceManager = getDeviceManagerInstance();
        deviceInfo = deviceManager.getDeviceInfoInstance();
        ac = MainActivity.this;
        str = new StringBuilder();
        deviceGetter = deviceManager.getDeviceGetterInstance();
        deviceSecurity = deviceManager.getDeviceSecurityInstance();
        bootReceiver = deviceManager.getBootReceiver();
        wifiStateReceiver  =deviceManager.getWifiStateReceiver();
        mobileStatesReceiver = deviceManager.getMobileStatesReceiver();
        packageManager = getPackageManager();
    }

    public void initView() {
        setContentView(R.layout.main);
        button = (Button) findViewById(R.id.button);
     //   button_zhan = (Button) findViewById(R.id.button_zhan);
//        textView = (TextView) findViewById(R.id.tv_info);
//        password = (EditText) findViewById(R.id.password);
    }


    public void viewListener() {
        Log.e("qwe", "333333333");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
//                intent
//                        .putExtra(Constants.NOTIFICATION_TITLE,
//                                "afsdafsad");
//                intent.putExtra(Constants.NOTIFICATION_MESSAGE,
//                        "dsafsadfsdafsa");
//                intent.putExtra(Constants.NOTIFICATION_URI, "www.baidu.com");
//                sendBroadcast(intent);
                deviceSecurity.setScreenCutEnable();

            }
        });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("adf", "222222222222");
//                Intent intent = new Intent(MainActivity.this,
//                        NotificationDetailsActivity.class);
//                intent.putExtra(Constants.NOTIFICATION_TITLE, "sadfsadfsa");
//                intent.putExtra(Constants.NOTIFICATION_MESSAGE, "adsfsafsadf");
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                startActivity(intent);
//                PendingIntent contentIntent = PendingIntent.getActivity(MainActivity.this, 0,
//                        intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                packageUri = getPackageUri();
      //          install();
//                String passWord = password.getText().toString();
//                deviceLockOrWipe(DeviceManager.SCREEN_LOCK,passWord);
//                deviceSecurity.isInstall(packageManager,"")
//                String[] action = {Intent.ACTION_PACKAGE_ADDED,Intent.ACTION_PACKAGE_REMOVED};
//                deviceManager.registReceivers(MainActivity.this, bootReceiver, action);
//                String[] action = {ConnectivityManager.CONNECTIVITY_ACTION};
//                deviceManager.registReceivers(MainActivity.this,wifiStateReceiver,action);
//                deviceManager.registReceivers(MainActivity.this,mobileStatesReceiver,action);

//                Intent intent = new Intent(MainActivity.this, ScreenLockActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putCharSequence("password", "123");
//                bundle.putInt("tag", DeviceManager.SCREEN_LOCK);
//                intent.putExtras(bundle);
//                MainActivity.this.startActivity(intent);

//                IntentFilter intentFilter = new IntentFilter();
//                intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
//                intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
//                MainActivity.this.registerReceiver(bootReceiver,intentFilter);
             //   deviceSecurity.clientUninstall("pay.xiaofeng.com.location");


//                          List appList = deviceSecurity.getApp(MainActivity.this);
//                Log.e("app", appList.toString());
//                textView.setText(appList.toString());
//                deviceSecurity.clientUninstall("com.jd.jrapp");
//                GetLocation getLocation = new GetLocation(MainActivity.this);
//                String packageName = getPackageName();
//                textView.setText(packageName);

//                deviceSecurity.deleteFile(getApplicationContext(),Environment.getExternalStorageDirectory().getAbsoluteFile());
//            }
//        });

    }

    public void getDevice(){
        deviceGetter.getAvailRamMemory(ac);
        deviceGetter.getAvailRamMemory(MainActivity.this);
        deviceInfo.setIEMI(deviceGetter.getIMEI(MainActivity.this));
        deviceInfo.setPhoneModle(deviceGetter.getPhoneModel());
        deviceInfo.setManufacturer(deviceGetter.getManufacturer());
        deviceInfo.setCpuName(deviceGetter.getCpuName());
        deviceInfo.setAvailRom(deviceGetter.getAvailableRomMemroy());
        deviceInfo.setTotalRom(deviceGetter.getTotalInternalMemorySize());
        deviceInfo.setSystemVersion(deviceGetter.getVersion()[3]);
        deviceInfo.setSoftWateVersion(deviceGetter.getVersion()[1]);
        deviceInfo.setScreenHeight(deviceGetter.getDisplayMetrics(MainActivity.this)[0]);
        deviceInfo.setScreenWidth(deviceGetter.getDisplayMetrics(MainActivity.this)[1]);
        deviceInfo.setAllSDSize(deviceGetter.getAllSDSize());
        deviceInfo.setAvailableSDSize(deviceGetter.getAvailaleSDSize());
        deviceInfo.setIsHasCamera(deviceGetter.getCamera());
        deviceInfo.setBluetoothMAC(deviceGetter.getBluetoothMac());
        deviceInfo.setWifiMAC(deviceGetter.getLocalMacAddress(MainActivity.this));
        deviceInfo.setUDid(deviceGetter.getUdid(MainActivity.this));
        deviceInfo.setSDSerial(deviceGetter.getSDSerial());
        deviceInfo.setIsRoot(deviceGetter.isRoot());
        deviceInfo.setUpTime(deviceGetter.getUpTime(MainActivity.this));
        deviceInfo.setIsScreenLock(deviceGetter.getScreenLock(MainActivity.this));
        deviceInfo.setPhoneNunber(deviceGetter.getNativePhoneNumber(MainActivity.this));
        deviceInfo.setPhoneProvidersName(deviceGetter.getProvidersName(MainActivity.this));
        deviceGetter.getBatteryInfo(MainActivity.this);
    }



//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Intent intent = new Intent(MainActivity.this, ScreenLockActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putCharSequence("password", "");
//        bundle.putInt("tag", DeviceManager.SCREEN_LOCK);
//        intent.putExtras(bundle);
//        MainActivity.this.startActivity(intent);
//        deviceManager.unRegistReceivers(this,wifiStateReceiver);
//        deviceManager.unRegistReceivers(this,mobileStatesReceiver);
//    }

    /**
     * 进行锁屏，修改锁屏密码和恢复出厂设置
     * @param tag 标志是否锁屏或者恢复出厂值设置
     * @param password 锁屏密码
     */
    private void deviceLockOrWipe(int tag,String password){
        Intent intent = new Intent(this, ScreenLockActivity.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("password", password);
        bundle.putInt("tag", tag);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
