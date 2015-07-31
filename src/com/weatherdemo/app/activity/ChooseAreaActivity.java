package com.weatherdemo.app.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.weatherdemo.app.db.WeatherDemoDB;
import com.weatherdemo.app.domain.City;
import com.weatherdemo.app.domain.Country;
import com.weatherdemo.app.domain.Province;
import com.weatherdemo.app.util.HttpCallbackListener;
import com.weatherdemo.app.util.HttpUtil;
import com.weatherdemo.app.util.Utility;
import com.wetherdemo.app.R;

public class ChooseAreaActivity extends Activity {
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTRY = 2;
	private int currentLevel;
	
	private ProgressDialog progressDialog;
	private TextView tv_title;
	private ListView lv;
	private ArrayAdapter<String> adapter;
	private WeatherDemoDB weatherDemoDB;
	private List<String> dataList = new ArrayList<String>();
	private List<Province> pList;
	private List<City> ciList;
	private List<Country> coList;
	
	private Province selectedProvince;
	private City selectedCity;
	private Country selectedCountry;
	private Button bt;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		
		checkNet();  

		lv = (ListView) findViewById(R.id.lv);
		tv_title = (TextView)findViewById(R.id.tv_title);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataList);
		lv.setAdapter(adapter);
		weatherDemoDB = WeatherDemoDB.getInstance(this);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(currentLevel == LEVEL_PROVINCE){
					qureyFromServer(null,"province2");
					selectedProvince = pList.get(position);
					Intent it = new Intent(ChooseAreaActivity.this,WeatherActivity.class);
					it.putExtra("pCode", selectedProvince.getpCode());
					startActivity(it);
				} 
			}
		});
		
		queryProvinces();//加载省级数据
	}


	private void checkNet() {
		ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);  
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
		boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
		if(wifi|internet){  
		    //执行相关操作  
		}else{  
		    Toast.makeText(getApplicationContext(),  
		            "请先连接网络。", Toast.LENGTH_LONG)  
		            .show();  
		    return;
		}
	}

	
	@Override
	protected void onResume() {
		super.onResume();
//		checkNet();
		
	}
	
 	private void queryProvinces() {
		pList = weatherDemoDB.loadProvince();
		if(pList.size() > 0 ){
			dataList.clear();
			for(Province p:pList){
				dataList.add(p.getpName());
			}
			adapter.notifyDataSetChanged();
			lv.setSelection(0);
			currentLevel = LEVEL_PROVINCE;
		}else{
			qureyFromServer(null,"province");
		}
		
	}

	private void qureyFromServer(final String code,final String type) {
		String address;
		if(!TextUtils.isEmpty(code)){
			//已失效
			address = "http://www.weather.com.cn/data/list3/city"+code+".xml";
		}else{
			address = "http://flash.weather.com.cn/wmaps/xml/china.xml";
		}
		showProgressDialog();
		
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				boolean result = false;
				if("province".equals(type)){
					result = Utility.handleProvincesResponse(ChooseAreaActivity.this,1,weatherDemoDB, response);
				}
				if("province2".equals(type)){
					result = Utility.handleProvincesResponse(ChooseAreaActivity.this,2,weatherDemoDB, response);
				}
				
				if(result){
					runOnUiThread(new Runnable(){

						@Override
						public void run() {
							closeProgressDialog();
							if("province".equals(type)){
								queryProvinces();
							}//未完待续
						}
					
					});
				}
			}
			
			public void onError(Exception e) {
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						closeProgressDialog();
						Toast.makeText(ChooseAreaActivity.this, "检测到您未连网，为您自动返回上次查询数据", 0).show();
					}
				});
			}
		});
	}
	private void showProgressDialog(){
		if(progressDialog == null ){
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("玩命加载中......");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	private void closeProgressDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
}
