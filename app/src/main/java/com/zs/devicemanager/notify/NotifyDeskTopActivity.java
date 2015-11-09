package com.zs.devicemanager.notify;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zs.devicemanager.R;

/**
 * 项目名称：Test
 * 类描述：
 * 创建人：Saber
 * 创建时间：2015/11/7 9:29
 * 修改人：Saber
 * 修改时间：2015/11/7 9:29
 * 修改备注：
 */
public class NotifyDeskTopActivity extends Activity {
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000;

    RelativeLayout relativeLayout;
    TextView notify_msg;
    TextView notify_time;
    long mLastTime= 0;
    long mCurTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String notificationTitle = bundle.getString("notificationTitle");
        String notificationMessage = bundle.getString("notificationMessage");
        String notificationUri = bundle.getString("notificationUri");
        String notificationCreateTime = bundle.getString("notificationCreateTime");
        initView();
        notify_msg.setText(notificationMessage);
        notify_time.setText(notificationCreateTime);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(NotifyDeskTopActivity.this,NotificationDetailsActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                NotifyDeskTopActivity.this.startActivity(intent);
//                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
//                KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("");
//                keyguardLock.disableKeyguard();
//                NotifyDeskTopActivity.this.finish();

                mLastTime = mCurTime;
                mCurTime = System.currentTimeMillis();
                if (mCurTime - mLastTime < 300) {
                    // "这就是传说中的双击事件"，Toast.LENGTH_SHORT).show();
                    NotifyDeskTopActivity.this.finish();
                } else {
                    Toast.makeText(NotifyDeskTopActivity.this,"双击退出此消息！！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void initView() {
        setContentView(R.layout.notify_desktop);
        relativeLayout = (RelativeLayout) findViewById(R.id.rl_notify);
        notify_msg = (TextView) findViewById(R.id.notify_msg);
        notify_time = (TextView) findViewById(R.id.notify_time);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return false;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {

            return false;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 拦截/屏蔽系统Home键
    public void onAttachedToWindow() {
        this.getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
        super.onAttachedToWindow();
    }
}
