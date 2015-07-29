package com.weatherdemo.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weatherdemo.app.domain.City;
import com.weatherdemo.app.domain.Country;
import com.weatherdemo.app.domain.City;
import com.weatherdemo.app.domain.Province;

public class WeatherDemoDB {
	
	public static final String DB_NAME = "weather";
	public static final int VERSION = 1;
	private static WeatherDemoDB weatherDemoDB;
	private SQLiteDatabase db;
	/*
	 * 将构造方法私有化
	 */
	private WeatherDemoDB(Context context){
		 WeatherDemoOpenHelper helper = new WeatherDemoOpenHelper(context, DB_NAME, null, VERSION);
		 db = helper.getWritableDatabase();
	}
	/*
	 * 单例模式获取db实例
	 */
	public synchronized static WeatherDemoDB getInstance(Context context){
		if(weatherDemoDB == null){
			weatherDemoDB = new WeatherDemoDB(context);
		}
		return weatherDemoDB;
	}
	
	/*
	 * 将Province实例存储到数据库 
	 */
	public void saveProvince(Province p){
		ContentValues values = new ContentValues();
		values.put("p_name", p.getpName());
		values.put("p_code", p.getpCode());
		db.insert("province", null, values);
	}
	
	public void saveCity(City ci){
		ContentValues values = new ContentValues();
		values.put("ci_name", ci.getCiName());
		values.put("ci_code", ci.getCiCode());
		values.put("p_id", ci.getpId());
		db.insert("city", null, values);
	}
	public void saveProvince(Country co){
		ContentValues values = new ContentValues();
		values.put("p_name", co.getCoName());
		values.put("p_code", co.getCoCode());
		values.put("ci_id", co.getCiId());
		
		db.insert("province", null, values);
	}
	
	
	/*
	 * 从数据库读取全国所有的省份信息
	 */
	
	public List<City> loadProvince(){
		List<City> lists = new ArrayList<City>();
		Cursor cursor = db.query("province", null, null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				City p = new City();
				p.setId(cursor.getInt(cursor.getColumnIndex("id")));
				p.setCiName(cursor.getString(cursor.getColumnIndex("pName")));
				p.setCiCode(cursor.getString(cursor.getColumnIndex("pCode")));
				lists.add(p);
			}while(cursor.moveToNext());
		}
		if(cursor != null)
			cursor.close();
		return lists;
	}
	public List<Country> loadCountry(){
		List<Country> lists = new ArrayList<Country>();
		Cursor cursor = db.query("country", null, null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				Country co = new Country();
				co.setId(cursor.getInt(cursor.getColumnIndex("id")));
				co.setCoName(cursor.getString(cursor.getColumnIndex("coName")));
				co.setCoCode(cursor.getString(cursor.getColumnIndex("coCode")));
				co.setCiId(cursor.getInt(cursor.getColumnIndex("ciId")));
				lists.add(co);
			}while(cursor.moveToNext());
		}
		if(cursor != null)
			cursor.close();
		return lists;
	}
	
	
}
