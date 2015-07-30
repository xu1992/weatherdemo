package com.weatherdemo.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class WeatherDemoOpenHelper extends SQLiteOpenHelper {
	/*
	 * 定义建表语句常量
	 */
	public static final String T_PROVINCE = "create table province ("+"id integer primary key autoincrement,"+"pName text,"+"pCode text)";
	public static final String T_CITY = "create table city ("+"id integer primary key autoincrement,"+"ciName text,"+"ciCode text,"+"pId integer)";
	public static final String T_COUNTRY = "create table country ("+"id integer primary key autoincrement,"+"coName text,"+"coCode text,"+"ciId integer)";
	
	public WeatherDemoOpenHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(T_PROVINCE);//创建表
		db.execSQL(T_CITY);
		db.execSQL(T_COUNTRY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
