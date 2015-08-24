package org.androidpn.mydevice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidpn.demoapp.R;
import org.androidpn.demoapp.ScreenLockActivity;
import org.androidpn.mydevice.DeviceReceiver.BatteryReceiver;
import org.androidpn.mydevice.DeviceReceiver.BootReceiver;
import org.androidpn.mydevice.DeviceReceiver.MobileStatesReceiver;
import org.androidpn.mydevice.DeviceReceiver.WifiStateReceiver;


public class MainActivity extends BaseDeviceFunction {

    StringBuilder str = null;
    Button button;
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

    DeviceHandler handler = new DeviceHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void initData() {
        deviceManager = getDeviceManagerInstance();
        deviceInfo = deviceManager.getDeviceInfoInstance();
        ac = MainActivity.this;
        str = new StringBuilder();
        deviceGetter = deviceManager.getDeviceGetterInstance(handler);
        deviceSecurity = deviceManager.getDeviceSecurityInstance();
        bootReceiver = deviceManager.getBootReceiver();
        wifiStateReceiver  =deviceManager.getWifiStateReceiver();
        mobileStatesReceiver = deviceManager.getMobileStatesReceiver();
    }


    public void initView() {
        setContentView(R.layout.main);
        button = (Button) findViewById(R.id.btn_settings);
     //   button_zhan = (Button) findViewById(R.id.button_zhan);
        textView = (TextView) findViewById(R.id.tv_info);
    }


    public void viewListener() {
        Log.e("qwe", "333333333");
//        button_zhan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("adf", "1111111111");
//                textView.setText(deviceInfo.toString());
//
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("adf", "222222222222");

//                String[] action = {Intent.ACTION_PACKAGE_ADDED,Intent.ACTION_PACKAGE_REMOVED};
//                deviceManager.registReceivers(MainActivity.this, bootReceiver, action);
//                String[] action = {ConnectivityManager.CONNECTIVITY_ACTION};
//                deviceManager.registReceivers(MainActivity.this,wifiStateReceiver,action);
//                deviceManager.registReceivers(MainActivity.this,mobileStatesReceiver,action);

                Intent intent = new Intent(MainActivity.this, ScreenLockActivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("password", "123");
                bundle.putInt("tag", DeviceManager.SCREEN_LOCK);
                intent.putExtras(bundle);
                MainActivity.this.startActivity(intent);

//                IntentFilter intentFilter = new IntentFilter();
//                intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
//                intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
//                MainActivity.this.registerReceiver(bootReceiver,intentFilter);
             //   deviceSecurity.clientUninstall("pay.xiaofeng.com.location");


                //          List appList = deviceSecurity.getApp(MainActivity.this);
      //          Log.e("app",appList.toString());
      //          textView.setText(appList.toString());
//                deviceSecurity.clientUninstall("pay.xiaofeng.com.location");
//                deviceSecurity.deleteFile(getApplicationContext(),Environment.getExternalStorageDirectory().getAbsoluteFile());
            }
        });

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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(MainActivity.this, ScreenLockActivity.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("password", "");
        bundle.putInt("tag", DeviceManager.SCREEN_LOCK);
        intent.putExtras(bundle);
        MainActivity.this.startActivity(intent);
//        deviceManager.unRegistReceivers(this,wifiStateReceiver);
//        deviceManager.unRegistReceivers(this,mobileStatesReceiver);
    }
}
