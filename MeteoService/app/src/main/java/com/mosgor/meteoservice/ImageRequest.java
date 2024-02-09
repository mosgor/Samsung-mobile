package com.mosgor.meteoservice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageRequest implements Runnable {

	Handler handler;

	URL url;

	int id;

	public ImageRequest(Handler handler, String address, int id) {
		this.handler = handler;
		this.id = id;
		try {
			url = new URL(address);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			if (connection.getResponseCode() != 200) {
				Message msg = Message.obtain();
				msg.obj = "Error";
				handler.sendMessage(msg);
				connection.disconnect();
				return;
			}
			Bitmap img = BitmapFactory.decodeStream(connection.getInputStream());

			connection.disconnect();
			Message msg = Message.obtain();
			msg.obj = img;
			Bundle bund = new Bundle();
			bund.putInt("id", id);
			msg.setData(bund);
			handler.sendMessage(msg);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
