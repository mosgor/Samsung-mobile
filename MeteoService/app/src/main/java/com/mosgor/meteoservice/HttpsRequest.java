package com.mosgor.meteoservice;

import android.content.res.AssetManager;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.os.Handler;
import android.util.Log;

public class HttpsRequest implements Runnable {

	String key;

	String city;

	static final String APIREQUEST = "http://api.weatherapi.com/v1/forecast.json";

	URL url;

	boolean isCity = true;

	Handler handler;

	public HttpsRequest(Handler handler, AssetManager am, String city) {
		this.handler = handler;
		this.city = city;
		if (city.equals("0")) {
			isCity = false;
		}
		InputStream file;
		try {
			file = am.open("key.txt");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.key = new Scanner(file).next();
		try {
			url = new URL(APIREQUEST + "?" + "q=" + city + "&" + "key=" + key + "&days=7&aqi=no&alerts=no&lang=ru");
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		if (isCity) {
			try {
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				if (connection.getResponseCode() != 200) {
					Message msg = Message.obtain();
					msg.obj = "Error";
					handler.sendMessage(msg);
					connection.disconnect();
					return;
				}
				Scanner in = new Scanner(connection.getInputStream());
				StringBuilder response = new StringBuilder();

				while (in.hasNext()) {
					response.append(in.nextLine());
				}
				in.close();
				connection.disconnect();
				Message msg = Message.obtain();
				msg.obj = response.toString();
				handler.sendMessage(msg);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} else {
			Message msg = Message.obtain();
			msg.obj = "noCity";
			handler.sendMessage(msg);
		}
	}
}
