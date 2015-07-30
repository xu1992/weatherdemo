package com.weatherdemo.app.util;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.weatherdemo.app.db.WeatherDemoDB;
import com.weatherdemo.app.domain.City;
import com.weatherdemo.app.domain.Country;
import com.weatherdemo.app.domain.Province;

/*
 * Pull解析XML多属性文件。
 * 因为数据时"代号|城市，代号|城市"
 */
public class Utility {
	 
	public synchronized static boolean handleProvincesResponse(Context context,WeatherDemoDB weatherDemoDB,String response){
		if(!TextUtils.isEmpty(response)){
			parseXMLWithPull(context,weatherDemoDB,response);
			return true;
		}
		return false;
	}
	private static void parseXMLWithPull(Context context,WeatherDemoDB weatherDemoDB,String response) {
		 
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(response));
			//int eventType = xmlPullParser.getEventType();
			//直到文档结尾处
			int i = 1;
			while(xmlPullParser.getEventType() != xmlPullParser.END_DOCUMENT){
				 
				if(xmlPullParser.getEventType() == xmlPullParser.START_TAG){
					String nodeName = xmlPullParser.getName();
					if("city".equals(nodeName)){
						Province p = new Province();
						p.setpName(xmlPullParser.getAttributeValue(null, "quName"));
						p.setpCode(i+"");
						saveWeatherInfo(context,i,
								xmlPullParser.getAttributeValue(2),xmlPullParser.getAttributeValue(null, "tem1"),xmlPullParser.getAttributeValue(null, "tem2"), xmlPullParser.getAttributeValue(null, "stateDetailed"),xmlPullParser.getAttributeValue(null, "windState"),"2015");
						weatherDemoDB.saveProvince(p);
						i++;
					}
				}
				xmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void saveWeatherInfo(Context context,int i,String cityName,String temp1,String temp2,String weatherDesp,String weatherWind,String publishTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString("city_name"+i, cityName);
		editor.putString("temp1"+i, temp1);
		editor.putString("temp2"+i, temp2);
		editor.putString("weather_desp"+i, weatherDesp);
		editor.putString("weather_wind"+i, weatherWind);
		editor.putString("publish_time"+i, publishTime);
		editor.putString("current_date"+i, sdf.format(new Date()));
		editor.commit();
		
	}
}
