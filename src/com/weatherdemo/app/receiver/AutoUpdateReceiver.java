package com.weatherdemo.app.receiver;

import com.weatherdemo.app.service.AutoUpdateService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoUpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent it = new Intent(context,AutoUpdateService.class);
		context.startService(it);
	}

}
