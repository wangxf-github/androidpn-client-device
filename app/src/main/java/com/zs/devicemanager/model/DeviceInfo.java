package com.zs.devicemanager.model;

import java.util.Arrays;

/**
 * Created by S on 2015/7/27.
 */
public class DeviceInfo {
    /*
    cpu名称
     */
    private String cpuName ;
    /*
    软件系统版本
     */
    private String softWateVersion;
    /*
    系统版本
     */
    private String systemVersion;
    /*
    手机型号
     */
    private String phoneModle;
    /*

     */
    private String manufacturer;
    /*
    设备是否root
     */
    private boolean isRoot;
    /*
    系统总的rom大小
     */
    private double totalRom;
    /*
    系统可用rom大小
     */
    private double availRom;
    /*
    系统可用ram大小
     */
    private double availRam;
    /*
    电池电量，1-100比例
     */
    private String batteryLevel ;
    /*
    电池温度
     */
    private String batteryTemperature;
    /*
    设备唯一识别号
     */
    private String IEMI;
    /*
    设备广播注册集合
     */
    private int[] registers;
    /*
    屏幕宽度
     */
    private int screenWidth;
    /*
    屏幕高度
     */
    private int screenHeight;
    /*
    是否有摄像机
     */
    private boolean isHasCamera;
    /*
    蓝牙物理地址
     */
    private String bluetoothMAC;
    /*
    wifi物理地址
     */
    private String wifiMAC;
    /*
    总内存大小
     */
    private long allSDSize;
    /*
    可用内存大小
     */
    private long availableSDSize;
    /*
    sd卡识别号
     */
    private String SDSerial;
    /*
    udid
     */
    private String  UDid;
    /*
    开机时间
     */
    private long UpTime;
    /*
    是否锁屏
     */
    private boolean isScreenLock;
    /*
    手机号码
     */
    private String phoneNunber;
    /*
    手机运营商
     */
    private String phoneProvidersName;
    /*
    sim卡唯一识别号
     */
    private String imsi;
    /*
    设备是否漫游
     */
    private boolean isPhoneRoamState;

    public DeviceInfo() {
    }

    public String getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(String batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getBatteryTemperature() {
        return batteryTemperature;
    }

    public void setBatteryTemperature(String batteryTemperature) {
        this.batteryTemperature = batteryTemperature;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public boolean isPhoneRoamState() {
        return isPhoneRoamState;
    }

    public void setIsPhoneRoamState(boolean isPhoneRoamState) {
        this.isPhoneRoamState = isPhoneRoamState;
    }

    public String getPhoneNunber() {
        return phoneNunber;
    }

    public void setPhoneNunber(String phoneNunber) {
        this.phoneNunber = phoneNunber;
    }

    public String getPhoneProvidersName() {
        return phoneProvidersName;
    }

    public void setPhoneProvidersName(String phoneProvidersName) {
        this.phoneProvidersName = phoneProvidersName;
    }

    public boolean isScreenLock() {
        return isScreenLock;
    }

    public void setIsScreenLock(boolean isScreenLock) {
        this.isScreenLock = isScreenLock;
    }

    public long getUpTime() {
        return UpTime;
    }

    public void setUpTime(long upTime) {
        UpTime = upTime;
    }

    public long getAllSDSize() {
        return allSDSize;
    }

    public void setAllSDSize(long allSDSize) {
        this.allSDSize = allSDSize;
    }

    public long getAvailableSDSize() {
        return availableSDSize;
    }

    public void setAvailableSDSize(long availableSDSize) {
        this.availableSDSize = availableSDSize;
    }

    public String getBluetoothMAC() {
        return bluetoothMAC;
    }

    public void setBluetoothMAC(String bluetoothMAC) {
        this.bluetoothMAC = bluetoothMAC;
    }

    public boolean isHasCamera() {
        return isHasCamera;
    }

    public void setIsHasCamera(boolean isHasCamera) {
        this.isHasCamera = isHasCamera;
    }

    public String getSDSerial() {
        return SDSerial;
    }

    public void setSDSerial(String SDSerial) {
        this.SDSerial = SDSerial;
    }

    public String getUDid() {
        return UDid;
    }

    public void setUDid(String UDid) {
        this.UDid = UDid;
    }

    public String getWifiMAC() {
        return wifiMAC;
    }

    public void setWifiMAC(String wifiMAC) {
        this.wifiMAC = wifiMAC;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getIEMI() {
        return IEMI;
    }

    public void setIEMI(String IEMI) {
        this.IEMI = IEMI;
    }

    public int[] getRegisters() {
        return registers;
    }

    public void setRegisters(int[] registers) {
        this.registers = registers;
    }

    public String getSoftWateVersion() {
        return softWateVersion;
    }

    public void setSoftWateVersion(String softWateVersion) {
        this.softWateVersion = softWateVersion;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCpuName() {
        return cpuName;
    }

    public void setCpuName(String cpuName) {
        this.cpuName = cpuName;
    }

    public String getPhoneModle() {
        return phoneModle;
    }

    public void setPhoneModle(String phoneModle) {
        this.phoneModle = phoneModle;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setIsRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public double getTotalRom() {
        return totalRom;
    }

    public void setTotalRom(double totalRom) {
        this.totalRom = totalRom;
    }

    public double getAvailRom() {
        return availRom;
    }

    public void setAvailRom(double availRom) {
        this.availRom = availRom;
    }

    public double getAvailRam() {
        return availRam;
    }

    public void setAvailRam(double availRam) {
        this.availRam = availRam;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "allSDSize=" + allSDSize +
                ", cpuName='" + cpuName + '\'' +
                ", softWateVersion='" + softWateVersion + '\'' +
                ", systemVersion='" + systemVersion + '\'' +
                ", phoneModle='" + phoneModle + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", isRoot=" + isRoot +
                ", totalRom=" + totalRom +
                ", availRom=" + availRom +
                ", availRam=" + availRam +
                ", batteryLevel='" + batteryLevel + '\'' +
                ", batteryTemperature='" + batteryTemperature + '\'' +
                ", IEMI='" + IEMI + '\'' +
                ", registers=" + Arrays.toString(registers) +
                ", screenWidth=" + screenWidth +
                ", screenHeight=" + screenHeight +
                ", isHasCamera=" + isHasCamera +
                ", bluetoothMAC='" + bluetoothMAC + '\'' +
                ", wifiMAC='" + wifiMAC + '\'' +
                ", availableSDSize=" + availableSDSize +
                ", SDSerial='" + SDSerial + '\'' +
                ", UDid='" + UDid + '\'' +
                ", UpTime=" + UpTime +
                ", isScreenLock=" + isScreenLock +
                ", phoneNunber='" + phoneNunber + '\'' +
                ", phoneProvidersName='" + phoneProvidersName + '\'' +
                ", imsi='" + imsi + '\'' +
                ", isPhoneRoamState=" + isPhoneRoamState +
                '}';
    }
}

