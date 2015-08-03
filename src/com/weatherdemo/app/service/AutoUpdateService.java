package com.weatherdemo.app.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.widget.Toast;

import com.weatherdemo.app.db.WeatherDemoDB;
import com.weatherdemo.app.receiver.AutoUpdateReceiver;
import com.weatherdemo.app.util.HttpCallbackListener;
import com.weatherdemo.app.util.HttpUtil;
import com.weatherdemo.app.util.Utility;
import com.wetherdemo.app.R;

public class AutoUpdateService extends Service {
	
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 1:
				Toast.makeText(getApplicationContext(), "更新成功", 1).show();
				NotificationManager manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
				Notification nf = new Notification(R.drawable.icon,null,System.currentTimeMillis());
				nf.setLatestEventInfo(getApplicationContext(), "自定义天气软件更新", "更新成功", null);
				manager.notify(1, nf);
				break;
			default:
				break;
			}
		}
	};
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				updateWeather();
				
			}
		}).start();
		AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int minte = 60 * 1000;
		long triggerAtTime = SystemClock.elapsedRealtime() + minte;
		Intent it = new Intent(this,AutoUpdateReceiver.class);
//		it.putExtra("handlerString", handlerString);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, it, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		
		return super.onStartCommand(intent, flags, startId);

	}
	protected void updateWeather() {
		String address = "http://flash.weather.com.cn/wmaps/xml/china.xml";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				boolean flag = Utility.handleProvincesResponse(AutoUpdateService.this,3,WeatherDemoDB.getInstance(AutoUpdateService.this), response);
				if(flag){
					Message m = new Message();
					m.what = 1;
					handler.sendMessage(m);
				}
			}
			
			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		});
	}

}
