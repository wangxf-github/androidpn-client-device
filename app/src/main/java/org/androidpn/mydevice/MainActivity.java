package org.androidpn.mydevice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidpn.demoapp.R;

import java.util.List;
import java.util.Map;


public class MainActivity extends BaseDeviceActivity {

    StringBuilder str = null;
    Button button;
    Button button_zhan;
    DeviceReceiver deviceReceiver;
    DeviceInfo deviceInfo;
    Activity ac;
    DeviceGetter deviceGetter;
    TextView textView;
    private DeviceSecurity deviceSecurity ;

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int tag = msg.what;
            switch (tag){
                case DeviceManager.BATTERY_INFO:
                    Map map = (Map)msg.obj;

                    String level = (String) map.get("level");
                    String  temperature = (String) map.get("temperature");
                    deviceInfo.setBatteryLevel(level);
                    deviceInfo.setBatteryTemperature(temperature);
                    Log.e("asdf", level + "====" + temperature);
                 //   str.append("==battery" +"level:"+level+"=temperature:"+temperature);
                    break;
                    default:
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        deviceInfo = getDeviceManager().getDeviceInfoInstance();
        ac = MainActivity.this;
        str = new StringBuilder();
        deviceGetter = getDeviceManager().getDeviceGetterInstance(handler);
        deviceReceiver = getDeviceManager().getDeviceReceiverInstance(handler);
        deviceSecurity = getDeviceManager().getDeviceSecurityInstance();
    }

    @Override
    public void initView() {
        setContentView(R.layout.main);
        button = (Button) findViewById(R.id.btn_settings);
     //   button_zhan = (Button) findViewById(R.id.button_zhan);
        textView = (TextView) findViewById(R.id.tv_info);
    }

    @Override
    public void viewListener() {
        Log.e("qwe","333333333");
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
                Log.e("adf","222222222222");
               //getter.getAvailRamMemory(ac);
                //textView.setText("==="+availRomMemory[0]+"===="+availRomMemory[1]);
//                deviceGetter.getAvailRamMemory(MainActivity.this);
//                deviceGetter.getBatteryInfo(MainActivity.this);
//
//                deviceInfo.setIEMI(deviceGetter.getIMEI(MainActivity.this));
//                deviceInfo.setPhoneModle(deviceGetter.getPhoneModel());
//                deviceInfo.setManufacturer(deviceGetter.getManufacturer());
//                deviceInfo.setCpuName(deviceGetter.getCpuName());
//                deviceInfo.setAvailRom(deviceGetter.getAvailableRomMemroy());
//                deviceInfo.setTotalRom(deviceGetter.getTotalInternalMemorySize());
//                deviceInfo.setSystemVersion(deviceGetter.getVersion()[3]);
//                deviceInfo.setSoftWateVersion(deviceGetter.getVersion()[1]);
//                deviceInfo.setScreenHeight(deviceGetter.getDisplayMetrics(MainActivity.this)[0]);
//                deviceInfo.setScreenWidth(deviceGetter.getDisplayMetrics(MainActivity.this)[1]);
//                deviceInfo.setAllSDSize(deviceGetter.getAllSDSize());
//                deviceInfo.setAvailableSDSize(deviceGetter.getAvailaleSDSize());
//                deviceInfo.setIsHasCamera(deviceGetter.getCamera());
//                deviceInfo.setBluetoothMAC(deviceGetter.getBluetoothMac());
//                deviceInfo.setWifiMAC(deviceGetter.getLocalMacAddress(MainActivity.this));
//                deviceInfo.setUDid(deviceGetter.getUdid(MainActivity.this));
//                deviceInfo.setSDSerial(deviceGetter.getSDSerial());
//                deviceInfo.setIsRoot(deviceGetter.isRoot());
//                deviceInfo.setUpTime(deviceGetter.getUpTime(MainActivity.this));
//                deviceInfo.setIsScreenLock(deviceGetter.getScreenLock(MainActivity.this));
//                deviceInfo.setPhoneNunber(deviceGetter.getNativePhoneNumber(MainActivity.this));
//                deviceInfo.setPhoneProvidersName(deviceGetter.getProvidersName(MainActivity.this));
//                deviceInfo.setImsi(deviceGetter.getImsi(MainActivity.this));
//                deviceInfo.setIsPhoneRoamState(deviceGetter.getPhoneRoamState(MainActivity.this));
//
                List appList = deviceSecurity.getApp(MainActivity.this);
                Log.e("app",appList.toString());
                textView.setText(appList.toString());
//                deviceSecurity.clientUninstall("pay.xiaofeng.com.location");
//                deviceSecurity.deleteFile(getApplicationContext(),Environment.getExternalStorageDirectory().getAbsoluteFile());
            }
        });

    }
}
