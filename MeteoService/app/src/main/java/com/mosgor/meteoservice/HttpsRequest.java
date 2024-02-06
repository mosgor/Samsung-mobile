package com.mosgor.meteoservice;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Message;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import android.os.Handler;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class HttpsRequest implements Runnable {

	String key;

	static final String CITY = "Moscow";

	static final String APIREQUEST = "http://api.weatherapi.com/v1/current.json";

	URL url;

	Handler handler;

	public HttpsRequest(Handler handler, AssetManager am) {
		this.handler = handler;
		InputStream file;
		try {
			file = am.open("key.txt");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.key = new Scanner(file).next();
		try {
			url = new URL(APIREQUEST + "?" + "q=" + CITY + "&" + "key=" + key);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void run() {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			Log.d("RESULT", url.toString());
			Scanner in = new Scanner(connection.getInputStream());
			StringBuilder responce = new StringBuilder();
			Log.d("RESULT52", url.toString());

			while (in.hasNext()) {
				responce.append(in.nextLine());
			}
			in.close();
			connection.disconnect();
			Log.d("RESULT2", url.toString());
			Message msg = Message.obtain();
			msg.obj = responce.toString();
			handler.sendMessage(msg);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);

		}
	}
}
