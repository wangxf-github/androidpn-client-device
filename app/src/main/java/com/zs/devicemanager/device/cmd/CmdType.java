package com.zs.devicemanager.device.cmd;

/**
 * Created by Saber on 2015/10/10.
 */
public class CmdType {


        public static final int BATTERYSTATUS =4001;
        public  static final int RAMSIZE= 4002;
        public  static final int PROCESSOR = 4003;
        public  static final int ROMSIZE = 4004;
        public  static final int APPINFOS=4005;
        public  static final int DISPLAYSIZE=4006;
        public  static final int BLUETOOTHMAC=4007;
        public  static final int WIFIMAC = 4008;
        public  static final int IMEI = 4009;
        public  static final int DEVICEOS = 4010;
        public  static final int ISROOT = 4011;
        public  static final int ISLOCK= 4012;
        public  static final int IMSINO = 4013;
        public  static final int MOBILEOPERATOR = 4014;
        public  static final int DEVICEMOBILENO = 4015;
        public  static final int ISROAMING = 4016;
        public  static final int SIMFLOW = 4017;
        public  static final int WIFIFLOW = 4018;
        public  static final int MANUFACTURER = 4019;
        public  static final int CAMERA = 4020;
        public  static final int PHONEMODEL = 4021;
        public  static final int SCREENLOCK = 6001;
        public  static final int DEVICEWIPE = 6002;
        public static final int UNSTALLAPK = 6003;
        public static final int RESETPASSWORD = 6004;

        //上报信息标识
        public static final int COLLECTION = 9001;
        //限制操作标识
        public static final int LIMITION = 9002;
        //设备安全检测
        public  static final int HARDWARESECURITY = 9003;

        public  static final int LOCATION = 9005;

        //空指令
        public static final int NOCMD = 4444;

}
