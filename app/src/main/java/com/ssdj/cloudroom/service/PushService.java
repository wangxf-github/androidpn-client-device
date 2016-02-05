/**
 * file_name: PushService.java
 * date： 2015年7月30日 上午9:22:50
 */
package com.ssdj.cloudroom.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import org.androidpn.connection.ClientService;

/**
 * @ClassName: PushService
 * @date 2015年7月30日 上午9:22:50
 */
public class PushService extends Service {

	/**
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param intent
	 * @return
	 * @see Service#onBind(Intent)
	 */

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @see Service#onCreate()
	 */

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "PushService服务已启动！", 1).show();
	}

	/**
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @param intent
	 * @param flags
	 * @param startId
	 * @return
	 * @see Service#onStartCommand(Intent, int, int)
	 */

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "PushService服务已启动2！", 1).show();
		                Intent intents = new Intent(this, ClientService.class);
                intents.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(intents);
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * <p>
	 * Description:
	 * </p>
	 *
	 * @see Service#onDestroy()
	 */

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
