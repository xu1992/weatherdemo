package com.weatherdemo.app.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

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
			tv_temp1.setText(prefs.getString("temp1"+pCode, "")+"¡æ");
			tv_temp2.setText(prefs.getString("temp2"+pCode, "")+"¡æ");
			tv_weatherDesp.setText(prefs.getString("weather_desp"+pCode, ""));
			tv_weatherWind.setText(prefs.getString("weather_wind"+pCode, ""));
			tv_currentDate.setText(prefs.getString("current_date"+pCode, ""));
			tv_publish.setText("Í¬²½ÖÐ");
			//weatherInfoLayout.setVisibility(View.VISIBLE);
		}
	}
	
}
