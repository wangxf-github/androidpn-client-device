package com.zs.devicemanager.device;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.zs.devicemanager.model.Constants;
import com.zs.devicemanager.model.DeviceInfoIQ;
import com.zs.devicemanager.device.receiver.BatteryReceiver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.UUID;

/**
 * Created by S on 2015/7/20.
 */
public class DeviceGetter {

    BatteryReceiver batteryReceiver = new BatteryReceiver();
    DeviceManager deviceManager =DeviceManager.getDeviceManagerInstance();

    public DeviceGetter() {
    }


    //手机型号
    public String getPhoneModel() {
        String model = Build.MODEL;
        return model;
    }

    //手机厂商
    public String getManufacturer() {
        String manufacturer = Build.MANUFACTURER;
        return manufacturer;
    }

    /* 获取CPU名字 */
    public  String getCpuName() {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader("/proc/cpuinfo");
            br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            for (int i = 0; i < array.length; i++) {
            }
            return array[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
        return null;
    }

    /**
    *可用Rom存储
    *返回字节
     */
    public long getAvailableRomMemroy() {
        long[] romInfo = new long[2];
        //Total rom memory
        romInfo[0] = getTotalInternalMemorySize();

        //Available rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        romInfo[1] = blockSize * availableBlocks;
        return romInfo[1];
    }

    /**
     * 获取总内存
     * @return
     * 返回字节
     */
    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long totalMemory = totalBlocks * blockSize;
        return totalMemory;
    }
    public String[] getVersion(){
        String[] version={"null","null","null","null"};
        String str1 = "/proc/version";
        String str2;
        String[] arrayOfString;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            version[0]=arrayOfString[2];//KernelVersion
            localBufferedReader.close();
        } catch (IOException e) {
        }
        version[1] = Build.VERSION.RELEASE;// firmware version
        version[2]=Build.MODEL;//model
        version[3]=Build.DISPLAY;//system version
        return version;
    }

    /**
     * 获取android当前运行内存大小
     */
    public String  getRamMemory(final Context context) {

//        new Thread(){
//            @Override
//            public void run() {

                ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
                ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
                am.getMemoryInfo(mi);
                //mi.availMem; 当前系统的可用内存
//                Log.e("ram", String.valueOf(mi.availMem));
//                mi.totalMem;
//                Message message = new Message();
//                message.what=DeviceManager.AvailRamMemory_INFO;
//                message.obj = String.valueOf(mi.totalMem);
//                handler.sendMessage(message);

//            }
//        }.start();
         return mi.totalMem+"";

    }
    /**
    获取屏幕分辨率
     */
    public int[] getDisplayMetrics(Context activity){
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int[] mer = new int[2];
        mer[1] = size.x;
        mer[0] = size.y;
        return mer;
    }
    /*
    有无摄像头
     */
    public boolean getCamera(){
        int x = Camera.getNumberOfCameras();
        if(x>0){
            return true;
        }else{
            return  false;
        }
    }
    /*
    蓝牙mac地址
     */
    public String getBluetoothMac(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.getAddress();
    }
    /*
    获取wifi mac
     */
    public String getLocalMacAddress(final Context context) {
                 WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                 WifiInfo info = wifi.getConnectionInfo();
                 return info.getMacAddress();
             }
    /*
    sd卡剩余空间
     */
    public long getAvailaleSDSize() {

        File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }
    /*
    总sd卡大小
     */
    public long[] getAllSDSize() {
        long[] size = new long[2];
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        long avaBlocks = stat.getAvailableBlocks();
        size[0] = totalBlocks * blockSize;
        size[1] = avaBlocks*blockSize;
        return size;
    }
    /*
    获取sd卡序列号
     */
    public String getSDSerial(){
        String str1 = null;
        Object localOb;
        try
        {
            str1 = "/sys/block/mmcblk2/device/";
            localOb = new FileReader(str1 + "serial"); // 串号/序列号
            String sd_serial = new BufferedReader((Reader)localOb).readLine();
            System.out.println("serial: " + sd_serial);
            return  sd_serial;
        }catch(Exception e1)
        {
            System.out.println(e1.getMessage());
            return  null;
        }
    }
/*
获取电池信息
 */
    public void getBatteryInfo(Context activity){

        String[]  action = {Intent.ACTION_BATTERY_CHANGED};
        deviceManager.registReceivers(activity,batteryReceiver, action);

    }

    /**
     * Description : 获取开机的时间
     *
     *@return String 秒数
     *
     */
    public  long getUpTime(final Context context) {
        SharedPreferences sharedPreferences=context.getSharedPreferences("这是存储文件的名字", Context.MODE_PRIVATE);

        long seconds= sharedPreferences.getLong("存储时间的key", new Date().getTime());
        return seconds;
    }
    /*
    获取IEMI
     */
    public String getIMEI(Context context){
                String imei =((TelephonyManager)context.getSystemService(Activity.TELEPHONY_SERVICE)).getDeviceId();
        Log.e("22222222",imei+",,");
        if(imei!=null&&imei!="") {
            return imei;
        }
        return null;
    }
    /*
    获取UDid
     */
    public String getUdid(final Context context){
        final TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }



    /**
     * 判断当前手机是否有ROOT权限
     * @return
     */
    public boolean isRoot(){
        Process process = null;
        try
        {
            process  = Runtime.getRuntime().exec("su");
            process.getOutputStream().write("exit\n".getBytes());
            process.getOutputStream().flush();
            int i = process.waitFor();
            if(0 == i){
                process = Runtime.getRuntime().exec("su");
                return true;
            }

        } catch (Exception e)
        {
            return false;
        }
        return false;

    }
//    public boolean isRoot(){
//        boolean bool = false;
//
//        try{
//            if ((!new File("/system/bin/su").exists()) && (!new File("/system/xbin/su").exists())){
//                bool = false;
//            } else {
//                bool = true;
//            }
//            Log.d("root", "bool = " + bool);
//        } catch (Exception e) {
//
//        }
//        return bool;
//    }
    /*
    获取屏幕状态
     */
    public boolean getScreenLock(Context context){
        KeyguardManager mKeyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean flag = mKeyguardManager.inKeyguardRestrictedInputMode();
        return flag;
    }

    /**
     * Role:获取当前设置的电话号码
     * <BR>Date:2012-3-12
     * <BR>@author CODYY)peijiangping
     */
    public String getNativePhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                    .getSystemService(Context.TELEPHONY_SERVICE);
        String nativePhoneNumber=null;
        nativePhoneNumber=telephonyManager.getLine1Number();
        if(nativePhoneNumber!=null) {
            Log.e("num++",nativePhoneNumber+"");
            return nativePhoneNumber;
        }
        return null;
    }

    /**
     * Role:Telecom service providers获取手机服务商信息 <BR>
     * 需要加入权限<uses-permission
     * android:name="android.permission.READ_PHONE_STATE"/> <BR>
     * Date:2012-3-12 <BR>
     *
     * @author CODYY)peijiangping
     */
    public String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String providersName = null;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = telephonyManager.getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if(IMSI!=null) {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                providersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                providersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                providersName = "中国电信";
            }
            if(providersName!=null) {
                Log.e("name++",providersName+"");
                return providersName;
            }
        }

        return null;
    }

    /*
    获取imsi
     */
    public  String getImsi(Context context){
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        if(imsi!=null&&imsi!="") {
            return imsi;
        }
        return null;
    }
    /*
    判断手机是否漫游
     */
    public boolean getPhoneRoamState(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
        return false;
        }
        if (info.isRoaming()) {
            // here is the roaming option you can change it if you want to
            // disable internet while roaming, just return false
        return true;
        }
        return  false;

    }
    /**
     * 获取所有的下载流量
     * @return
     */
    public long[] getTotalBytes(){
        //1.获取一个包管理器。
//        PackageManager pm = activity.getPackageManager();
////2.遍历手机操作系统 获取所有的应用程序的uid
//        List<ApplicationInfo> appliactaionInfos = pm.getInstalledApplications(0);
//        for(ApplicationInfo applicationInfo : appliactaionInfos){
//            int uid = applicationInfo.uid;    // 获得软件uid
//            //proc/uid_stat/10086
//            long tx = TrafficStats.getUidTxBytes(uid);//发送的 上传的流量byte
//            long rx = TrafficStats.getUidRxBytes(uid);//下载的流量 byte
//            //方法返回值 -1 代表的是应用程序没有产生流量 或者操作系统不支持流量统计
//        }
        long[] bytes = new long[3];
        TrafficStats.getMobileTxBytes();//获取手机3g/2g网络上传的总流量
        bytes[0] = TrafficStats.getMobileRxBytes();//手机2g/3g下载的总流量


        TrafficStats.getTotalTxBytes();//手机全部网络接口 包括wifi，3g、2g上传的总流量
        bytes[1] = TrafficStats.getTotalRxBytes();//手机全部网络接口 包括wifi，3g、2g下载的总流量
        bytes[2] = bytes[1]-bytes[0];
        return bytes;
    }

    /**
     * 获取总容量和可用容量
     * @return
     */
    public long[] getRomSize(){
        long[] romInfo = new long[2];
        //Available rom memory
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        long totalBlocks = stat.getBlockCount();
        romInfo[0] =  totalBlocks * blockSize;
        romInfo[1] = blockSize * availableBlocks;
        return romInfo;
    }

    /*
    获取收集的额外信息
     */
    public void getResultData(DeviceInfoIQ infoIQ){
        infoIQ.setBatteryStatus(Constants.batteryInfo);
    }
    /*
    销毁注册数据
     */
    public void destoryInitData(Context context){
        if(batteryReceiver!=null) {
            deviceManager.unRegistReceivers(context, batteryReceiver);
        }
    }
}

