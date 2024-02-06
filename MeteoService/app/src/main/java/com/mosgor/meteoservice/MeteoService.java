package com.mosgor.meteoservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MeteoService extends Service {

	Handler handler;

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		handler = new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(@NonNull Message msg) {
				super.handleMessage(msg);
				String str = (String) msg.obj;
				Intent intent = new Intent("MeteoService");
				intent.putExtra("INFO", str);
				sendBroadcast(intent);
			}
		};
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		SharedPreferences pref = getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
		String city = pref.getString("city", "0");
		Thread weatherTread = new Thread(new HttpsRequest(handler, getAssets(), city));
		weatherTread.start();
		return START_NOT_STICKY;
	}

}
