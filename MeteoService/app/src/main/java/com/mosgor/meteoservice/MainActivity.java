package com.mosgor.meteoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


	TextView textView;
	EditText editText;

	Button approveBt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = findViewById(R.id.ed);
		textView = findViewById(R.id.view);
		SharedPreferences pref = getPreferences(Context.MODE_PRIVATE);
		String city = pref.getString("city", "0");
		if (!city.equals("0")) {
			editText.setText(city);
		}
		updateWeather();
		approveBt = findViewById(R.id.approve);
		approveBt.setOnClickListener(view -> {
			if (editText.getText().toString().length() != 0) {
				SharedPreferences.Editor edit = pref.edit();
				edit.putString("city", editText.getText().toString());
				edit.commit();
				updateWeather();
			}
		});
	}

	private void updateWeather() {
		registerReceiver(receiver, new IntentFilter("MeteoService"), RECEIVER_EXPORTED);
		Intent intent = new Intent(this, MeteoService.class);
		startService(intent);
	}

	BroadcastReceiver receiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String str = intent.getStringExtra("INFO");
			assert str != null;
			if (str.equals("noCity")) {
				textView.setText("Choose location");
			} else if (str.equals("Error")) {
				textView.setText("Can't find such location");
			} else {
				try {
					JSONObject start = new JSONObject(str);
					JSONObject current = start.getJSONObject("current");
					double temp = current.getDouble("temp_c");
					textView.setText(String.valueOf(temp));
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
			}
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		Intent intent = new Intent(this, MeteoService.class);
		stopService(intent);
	}
}