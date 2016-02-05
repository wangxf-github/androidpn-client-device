/**
 * 
 */
package com.ssdj.cloudroom.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.zs.application.MainApplication;

/**
 * @description 守护进程服务 在应用程序被强制停止后，守护进程会命令开启该服务，该服务会开启需要开启的其他的服务；
 */
public class DaemonService extends Service {
	/**
	 * 定义本地c方法
	 * 
	 * @return
	 */
	public native static void forkDaemon(String proName, int cmdType);


	/**
	 * 加载动态库
	 */
	static {
		System.loadLibrary("daemon");
	}
	public static final String START_DAEMON_ACTION ="com.ssdj.cloudroom.ACTION_START_DAEMON";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "DaemonService服务已启动0！", 1).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "DaemonService服务已启动！", 1).show();
		if (intent == null) {
			stopSelf();
			return super.onStartCommand(intent, flags, startId);
		}
		String action = intent.getAction();
		String proName = this.getApplicationInfo().packageName;
		int cmdType = MainApplication.systemVersion;
		if (START_DAEMON_ACTION.equals(action)) {
			Toast.makeText(this, "DaemonService服务已重新启动！", 1).show();
			forkDaemon(proName, cmdType);// 重新开启守护进程
		} 
		stopSelf();
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * @description
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
