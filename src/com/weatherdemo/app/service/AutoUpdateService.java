package com.weatherdemo.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.weatherdemo.app.db.WeatherDemoDB;
import com.weatherdemo.app.receiver.AutoUpdateReceiver;
import com.weatherdemo.app.util.HttpCallbackListener;
import com.weatherdemo.app.util.HttpUtil;
import com.weatherdemo.app.util.Utility;

public class AutoUpdateService extends Service {
	
	
	private String handlerString;
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
		it.putExtra("handlerString", handlerString);
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
					//Toast.makeText(AutoUpdateService.this,"后台自动更新成功 ", 1).show();
					handlerString = "begin";
				}
			}
			
			@Override
			public void onError(Exception e) {
				e.printStackTrace();
			}
		});
	}

}
