package com.weatherdemo.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherdemo.app.service.AutoUpdateService;
import com.wetherdemo.app.R;

public class WeatherActivity extends Activity {
	private LinearLayout weatherInfoLayout;
	private TextView tv_cityName;
	private TextView tv_publish;
	private TextView tv_weatherDesp;
	private TextView tv_weatherWind;
	private TextView tv_temp1; 
	private TextView tv_temp2;
	private TextView tv_currentDate;
	private Handler handler = new Handler(){
		private void handlerMess(Message msg) {
			switch(msg.what){
			case 1:
				Toast.makeText(WeatherActivity.this, "天气更新 成功", 1).show();
				break;
			default:
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather);
		weatherInfoLayout = (LinearLayout) findViewById(R.id.ll_weather);
		tv_cityName = (TextView) findViewById(R.id.city_name);
		tv_publish = (TextView) findViewById(R.id.tv_publish);
		tv_weatherDesp = (TextView) findViewById(R.id.weather_desp);
		tv_weatherWind = (TextView) findViewById(R.id.weather_wind);
		tv_temp1 = (TextView) findViewById(R.id.temp1);
		tv_temp2 = (TextView) findViewById(R.id.temp2);
		tv_currentDate = (TextView) findViewById(R.id.current_date);
		String pCode = getIntent().getStringExtra("pCode");
		tv_cityName.setText(pCode);
		if(!TextUtils.isEmpty(pCode)){
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			tv_cityName.setText(prefs.getString("city_name"+pCode,""));
			tv_temp1.setText(prefs.getString("temp1"+pCode, "")+"℃");
			tv_temp2.setText(prefs.getString("temp2"+pCode, "")+"℃");
			tv_weatherDesp.setText(prefs.getString("weather_desp"+pCode, ""));
			tv_weatherWind.setText(prefs.getString("weather_wind"+pCode, ""));
			String currentdate = prefs.getString("current_date"+pCode, "");
			tv_currentDate.setText(currentdate.substring(0, 10));
			tv_publish.setText("同步中:"+currentdate.substring(10,16));
			//weatherInfoLayout.setVisibility(View.VISIBLE);
			Intent it = new Intent(this,AutoUpdateService.class);
			startService(it);
		}
	}
	@Override
	public void onBackPressed() {
//		// TODO Auto-generated method stub
		super.onBackPressed();
//		ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);  
//		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
//		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
//		if(wifi|internet){  
//		    //执行相关操作  
//		}else{  
//		    Toast.makeText(getApplicationContext(),  
//		            "请先连接网络。", Toast.LENGTH_LONG)  
//		            .show();  
//		    return;
//		}
	}
	
}
