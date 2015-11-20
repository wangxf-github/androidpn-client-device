package com.zs.devicemanager.view;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
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

import org.androidpn.connection.ClientService;
import com.zs.devicemanager.device.BaseDeviceFunction;
import com.zs.devicemanager.device.DeviceGetter;
import com.zs.devicemanager.device.DeviceManager;
import com.zs.devicemanager.device.DeviceSecurity;
import com.zs.devicemanager.device.cmd.CmdOperate;
import com.zs.devicemanager.model.DeviceInfo;
import com.zs.devicemanager.device.receiver.BatteryReceiver;
import com.zs.devicemanager.device.receiver.BootReceiver;
import com.zs.devicemanager.device.receiver.MobileStatesReceiver;
import com.zs.devicemanager.device.receiver.MyAdminReceiver;
import com.zs.devicemanager.device.receiver.WifiStateReceiver;

import java.util.Random;


public class TestActivity extends BaseDeviceFunction {

    StringBuilder str = null;
    Button but_login;
    EditText et_ip;
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
//        Toolbar toolbar = (Toolbar) findViewById(R.id.action_settings);
//        toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String sa = getRan();
//                Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
//                intent.putExtra(Constants.NOTIFICATION_ID,sa );
//                intent.putExtra(Constants.NOTIFICATION_API_KEY,
//                        sa);
//                intent
//                        .putExtra(Constants.NOTIFICATION_TITLE,
//                                sa);
//                intent.putExtra(Constants.NOTIFICATION_MESSAGE,
//                        sa);
//                intent.putExtra(Constants.NOTIFICATION_URI, sa);
//                //                intent.setData(Uri.parse((new StringBuilder(
//                //                        "notif://notification.androidpn.org/")).append(
//                //                        notificationApiKey).append("/").append(
//                //                        System.currentTimeMillis()).toString()));
//
//                MainActivity.this.sendBroadcast(intent);
//            }
//        });

        Intent intent = new Intent(TestActivity.this, ClientService.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startService(intent);

    }

    public void initData() {
        Intent intent = new Intent();
        intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        ComponentName componentName = new ComponentName(this, MyAdminReceiver.class);		// 申请权限// 指定添加系统外设的动作名称
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);			// 指定给哪个组件授权
        startActivity(intent);// 构造意图

        deviceManager = DeviceManager.getDeviceManagerInstance();
//        deviceInfo = deviceManager.getDeviceInfoInstance();
        ac = TestActivity.this;
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
        but_login = (Button) findViewById(R.id.but_login);
     //   button_zhan = (Button) findViewById(R.id.button_zhan);
        textView = (TextView) findViewById(R.id.textId);
        et_ip = (EditText) findViewById(R.id.et_ip);
    }

    Random random = new Random();

    public String  getRan(){
        int max=20;
        int min=10;
        int s = random.nextInt(max)%(max-min+1) + min;
        return s+"";
    }

    public void viewListener() {
        Log.e("qwe", "333333333");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
//                intent
//                        .putExtra(Constants.NOTIFICATION_TITLE,
//                                "afsdafsad");
//                intent.putExtra(Constants.NOTIFICATION_MESSAGE,
//                        "dsafsadfsdafsa");
//                intent.putExtra(Constants.NOTIFICATION_URI, "www.baidu.com");
//                sendBroadcast(intent);
//                deviceSecurity.setScreenCutEnable();

//                String cmd = "deviceMobileNo;deviceOS;imei;imsiNo;isRoaming;mobileOperator;romSize;simFlow;wifiFlow;wifiMac";
//                DeviceInfoIQ deviceInfo = new DeviceInfoIQ();
//                CmdOperate cmdOperate = deviceManager.getDeviceCmdOperate();
////                deviceManager.getDeviceCmdOperate().doStrategyMethod(MainActivity.this, deviceInfo, new DeviceInfoIQ(), cmd, CmdType.COLLECTION);
////                deviceSecurity.unInstallApk("com.autonavi.minimap",MainActivity.this);
//                deviceInfo.setHardwareSecurity("isRoot;imsiNo;deviceOS");
//                String hardwareSecurity = deviceInfo.getHardwareSecurity();
//                cmdOperate.doMethods(MainActivity.this, deviceInfo, new DeviceInfoIQ(), hardwareSecurity, CmdType.HARDWARESECURITY);
//                long s = getTestRam();
//                DeviceInfoIQ deviceInfoIQ= deviceManager.getOverallDeviceInfoInstance();
//                    deviceGetter.getBatteryInfo(MainActivity.this);
//                DataListener.setDataListener(new HashMap().put("key",deviceInfoIQ.getBatteryStatus()));
//                        LogUtils.takeLog(MainActivity.class, s + ".......");

//                DeviceInfoIQ deviceInfoIQ = deviceManager.getOverallDeviceInfoInstance();
//                deviceInfoIQ.setReqFlag("location");
//                deviceInfoIQ.setDeviceCollection("isRoot;deviceOS;romSize;displaySize");
//                deviceInfoIQ.setDeviceLimition("");
//                deviceInfoIQ.setHardwareSecurity("isRoot;deviceOS;imsiNo");
//                String flag = deviceInfoIQ.getReqFlag();
//                if("strategy".equals(flag)){
//                    DeviceInfoIQ infoIQ = deviceManager.getSingleDeviceInfoInstance();
//                    cmdOperate.doMethods(MainActivity.this,deviceInfoIQ,infoIQ, CmdType.COLLECTION);
//                    cmdOperate.doMethods(MainActivity.this,deviceInfoIQ,infoIQ, CmdType.LIMITION);
//
//                }else  {
//                    DeviceInfoIQ infoIQ = deviceManager.getSingleDeviceInfoInstance();
//                    int cmdInt = CmdShine.cmdToInt(flag);
//                    cmdOperate.doMethods(MainActivity.this, deviceInfoIQ, infoIQ, cmdInt);
//                }
//                new GetLocation(MainActivity.this,new DeviceInfoIQ());
//                Log.e("eeeeeeee",deviceGetter.getDisplayMetrics(MainActivity.this)[1]+"---"+deviceGetter.getDisplayMetrics(MainActivity.this)[0]);


//                String sa = getRan();
//                Intent intent = new Intent(Constants.ACTION_SHOW_NOTIFICATION);
//                intent.putExtra(Constants.NOTIFICATION_ID,sa );
//                intent.putExtra(Constants.NOTIFICATION_API_KEY,
//                        sa);
//                intent
//                        .putExtra(Constants.NOTIFICATION_TITLE,
//                                sa);
//                intent.putExtra(Constants.NOTIFICATION_MESSAGE,
//                        sa);
//                intent.putExtra(Constants.NOTIFICATION_URI, sa);
//                //                intent.setData(Uri.parse((new StringBuilder(
//                //                        "notif://notification.androidpn.org/")).append(
//                //                        notificationApiKey).append("/").append(
//                //                        System.currentTimeMillis()).toString()));
//
//                MainActivity.this.sendBroadcast(intent);
//                textView.setText(deviceGetter.isRoot() + "");

//            }

//        });

        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = et_ip.getText().toString();

            }
        });
    }

        public long getTestRam(){


                ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
                am.getMemoryInfo(mi);
                return mi.availMem;


    }

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

//    }






    public void getDevice(){
//        deviceGetter.getAvailRamMemory(ac);
//        deviceGetter.getAvailRamMemory(MainActivity.this);
        deviceInfo.setIEMI(deviceGetter.getIMEI(TestActivity.this));
        deviceInfo.setPhoneModle(deviceGetter.getPhoneModel());
        deviceInfo.setManufacturer(deviceGetter.getManufacturer());
        deviceInfo.setCpuName(deviceGetter.getCpuName());
        deviceInfo.setAvailRom(deviceGetter.getAvailableRomMemroy());
        deviceInfo.setTotalRom(deviceGetter.getTotalInternalMemorySize());
        deviceInfo.setSystemVersion(deviceGetter.getVersion()[3]);
        deviceInfo.setSoftWateVersion(deviceGetter.getVersion()[1]);
        deviceInfo.setScreenHeight(deviceGetter.getDisplayMetrics(TestActivity.this)[0]);
        deviceInfo.setScreenWidth(deviceGetter.getDisplayMetrics(TestActivity.this)[1]);
//        deviceInfo.setAllSDSize(deviceGetter.getAllSDSize());
        deviceInfo.setAvailableSDSize(deviceGetter.getAvailaleSDSize());
        deviceInfo.setIsHasCamera(deviceGetter.getCamera());
        deviceInfo.setBluetoothMAC(deviceGetter.getBluetoothMac());
        deviceInfo.setWifiMAC(deviceGetter.getLocalMacAddress(TestActivity.this));
        deviceInfo.setUDid(deviceGetter.getUdid(TestActivity.this));
        deviceInfo.setSDSerial(deviceGetter.getSDSerial());
        deviceInfo.setIsRoot(deviceGetter.isRoot());
        deviceInfo.setUpTime(deviceGetter.getUpTime(TestActivity.this));
        deviceInfo.setIsScreenLock(deviceGetter.getScreenLock(TestActivity.this));
        deviceInfo.setPhoneNunber(deviceGetter.getNativePhoneNumber(TestActivity.this));
        deviceInfo.setPhoneProvidersName(deviceGetter.getProvidersName(TestActivity.this));
        deviceGetter.getBatteryInfo(TestActivity.this);
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
        Intent intent = new Intent(this, TestActivity.class);
        Bundle bundle = new Bundle();
        bundle.putCharSequence("password", password);
        bundle.putInt("tag", tag);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
