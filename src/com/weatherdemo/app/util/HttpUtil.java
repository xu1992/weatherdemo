package com.weatherdemo.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtil {
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		
		new Thread(new Runnable(){
			
			public void run(){
				HttpURLConnection conn = null;
				try {
					URL url = new URL(address);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					InputStream in = conn.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line = reader.readLine()) != null){
						response.append(line);
					}
					if(listener != null){
						Log.v("ABC", response.toString());
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					if(listener != null){
						listener.onError(e);
					}
				}finally{
					if(conn != null){
						conn.disconnect();
					}
				}
				
			}
		}).start();
	}
}
