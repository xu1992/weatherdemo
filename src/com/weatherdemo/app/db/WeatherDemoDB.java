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
		values.put("pName", p.getpName());
		values.put("pCode", p.getpCode());
		db.insert("province", null, values);
	}
	
	public void saveCity(City ci){
		ContentValues values = new ContentValues();
		values.put("ciName", ci.getCiName());
		values.put("ciCode", ci.getCiCode());
		values.put("pId", ci.getpId());
		db.insert("city", null, values);
	}
	public void saveCountry(Country co){
		ContentValues values = new ContentValues();
		values.put("coName", co.getCoName());
		values.put("coCode", co.getCoCode());
		values.put("ciId", co.getCiId());
		
		db.insert("country", null, values);
	}
	
	
	/*
	 * 从数据库读取全国所有的省份信息
	 */
	
	public List<Province> loadProvince(){
		List<Province> lists = new ArrayList<Province>();
		Cursor cursor = db.query("province", null, null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				Province p = new Province();
				p.setId(cursor.getInt(cursor.getColumnIndex("id")));
				p.setpName(cursor.getString(cursor.getColumnIndex("pName")));
				p.setpCode(cursor.getString(cursor.getColumnIndex("pCode")));
				lists.add(p);
			}while(cursor.moveToNext());
		}
		if(cursor != null)
			cursor.close();
		return lists;
	}
	public List<City> loadCity(){
		List<City> lists = new ArrayList<City>();
		Cursor cursor = db.query("country", null, null,null,null,null,null);
		if(cursor.moveToFirst()){
			do{
				City ci = new City();
				ci.setId(cursor.getInt(cursor.getColumnIndex("id")));
				ci.setCiName(cursor.getString(cursor.getColumnIndex("coName")));
				ci.setCiCode(cursor.getString(cursor.getColumnIndex("coCode")));
				ci.setpId(cursor.getInt(cursor.getColumnIndex("pId")));
				lists.add(ci);
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
