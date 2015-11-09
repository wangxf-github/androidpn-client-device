package com.zs.devicemanager.model;

import java.util.Arrays;

/**
 * Created by S on 2015/7/27.
 */
public class DeviceInfo {

    private String cpuName ;
    private String softWateVersion;
    private String systemVersion;
    private String phoneModle;
    private String manufacturer;
    private boolean isRoot;
    private double totalRom;
    private double availRom;
    private double availRam;
    private String batteryLevel ;
    private String batteryTemperature;
    private String IEMI;
    private int[] registers;
    private int screenWidth;
    private int screenHeight;
    private boolean isHasCamera;
    private String bluetoothMAC;
    private String wifiMAC;
    private long allSDSize;
    private long availableSDSize;
    private String SDSerial;
    private String  UDid;
    private long UpTime;
    private boolean isScreenLock;
    private String phoneNunber;
    private String phoneProvidersName;
    private String imsi;
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

