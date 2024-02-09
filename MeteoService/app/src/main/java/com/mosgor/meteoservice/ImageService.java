package com.mosgor.meteoservice;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ImageService extends Service {

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
				Bitmap img = (Bitmap) msg.obj;
				Bundle bund = msg.getData();
				Intent intent = new Intent("ImageService");
				intent.putExtra("INFO", img);
				intent.putExtra("viewId", bund.getInt("id"));
				sendBroadcast(intent);
			}
		};
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String address = intent.getStringExtra("url");
		int id = intent.getIntExtra("viewId", -1);
		Thread imageTread = new Thread(new ImageRequest(handler, address, id));
		imageTread.start();
		return START_NOT_STICKY;
	}
}
