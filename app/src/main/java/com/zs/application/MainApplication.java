/**   
 * @Title: MainApplication.java 
 * @Package com.ssdj.cloudroom 
 * @Description: 星星应用类
 * @author Sunocean
 * @email sunocean2008@sina.com
 * @date 2015年3月5日 下午1:57:52 
 * @version V1.0   
 */
package com.zs.application;

import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.ssdj.cloudroom.service.DaemonService;

/**
 * @ClassName: MainApplication
 * @date 2015年3月5日 下午1:57:52
 */
public class MainApplication extends Application {
	public static Context context;
	/**
	 * 系统版本类型，为0表示不支持--user参数（一般为2.3以下） 为1，表示支持--user参数（一般为4.0以上）
	 * 4.0以及4.1系统可能同时支持两种命令格式，以带参数的为优先
	 */
	public static int systemVersion;
	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		initSystemVersion(context);
		startDaemonService();
	}
	
	/**
	 * @description 启动守护进程
	 */
	private void startDaemonService() {
		Intent intent = new Intent(this, DaemonService.class);
		intent.setAction(DaemonService.START_DAEMON_ACTION);
		this.startService(intent);
	}

	/**
	 * @description 初始化系统版本
	 * @param context
	 */
	private void initSystemVersion(Context context) {
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("/system/bin/am abcd");// 确保输出错误日志
			InputStreamReader isr = new InputStreamReader(p.getErrorStream());
			char[] buf = new char[1024];
			while (isr.read(buf) > 0) {
				String tmp = new String(buf);
				if (tmp.contains("--user"))
					this.systemVersion = 1;
			}
		} catch (IOException e) {
			// Do noting
		} catch (NullPointerException e) {
			// Do noting
		} finally {
			if (p != null)
				p.destroy();
		}
	}
}
