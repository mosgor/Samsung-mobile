package com.mosgor.meteoservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

	GridLayout layout;

	TextView textView;
	EditText editText;

	Button approveBt;

	ImageView image;

	TextView status;

	TextView maxMin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		layout = findViewById(R.id.hoursGrid);
		layout.removeAllViews();
		layout.setColumnCount(24);
		LinearLayout[] hoursLines = new LinearLayout[24];
		LayoutInflater inflater =(LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < 24; i++) {
			hoursLines[i] = (LinearLayout) inflater.inflate(R.layout.hours, layout, false);
			hoursLines[i].setId(i);
			layout.addView(hoursLines[i]);
		}
		editText = findViewById(R.id.ed);
		textView = findViewById(R.id.view);
		image = findViewById(R.id.image);
		status = findViewById(R.id.status);
		maxMin = findViewById(R.id.MaxMin);
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
				textView.setText("Выберите локацию");
				cleanScreen();
			} else if (str.equals("Error")) {
				textView.setText("Такая локация не найдена");
				cleanScreen();
			} else {
				try {
					JSONObject start = new JSONObject(str);
					JSONObject current = start.getJSONObject("current");
					JSONObject condition = current.getJSONObject("condition");
					JSONArray forecast = start.getJSONObject("forecast").getJSONArray("forecastday");
					double temp = current.getDouble("temp_c");
					String output = performTemp(temp);
					String imageUrl = "http://" + condition.getString("icon");
					loadImage(image, imageUrl);
					String maxMinStr = performTemp(forecast.getJSONObject(0).getJSONObject("day").getDouble("maxtemp_c"));
					maxMinStr += " / " + performTemp(forecast.getJSONObject(0).getJSONObject("day").getDouble("mintemp_c"));
					maxMinStr += " Ощущается как " + performTemp(current.getDouble("feelslike_c"));
					String loc = start.getJSONObject("location").getString("name") + ", " + start.getJSONObject("location").getString("country");
					ImageView img;
					int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
					int currentDay = 0;
					for (int i = 0; i < 24; i++){
						img = (ImageView) layout.findViewById(i).findViewWithTag("i");
						img.setId(i + 24);
						if (currentHour > 23){
							currentHour = 0;
							currentDay = 1;
						}
						JSONObject day = forecast.getJSONObject(currentDay);
						JSONObject hour = day.getJSONArray("hour").getJSONObject(currentHour);
						loadImage(img, "http://" + hour.getJSONObject("condition").getString("icon"));
						TextView tv = findViewById(i).findViewWithTag("time");
						tv.setText(hour.getString("time").substring(11, 16));
						double tempo = hour.getDouble("temp_c");
						tv = findViewById(i).findViewWithTag("degree");
						tv.setText(performTemp(tempo));
						tv = findViewById(i).findViewWithTag("ll").findViewWithTag("humid");
						tv.setText(String.format("%s%%", hour.getString("humidity")));
						currentHour++;
					}
					editText.setText(loc);
					status.setText(condition.getString("text"));
					maxMin.setText(maxMinStr);
					textView.setTextSize(70);
					textView.setText(output);

				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
			}
		}
	};

	BroadcastReceiver imageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bitmap img = intent.getParcelableExtra("INFO");
			int viewId = intent.getIntExtra("viewId", -1);
			ImageView someImg = findViewById(viewId);
			someImg.setImageBitmap(img);
		}
	};

	private void cleanScreen(){
		textView.setTextSize(30);
		image.setImageBitmap(null);
		status.setText("");
		maxMin.setText("");
		layout.removeAllViews();
		LinearLayout[] hoursLines = new LinearLayout[24];
		LayoutInflater inflater =(LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
		for (int i = 0; i < 24; i++) {
			hoursLines[i] = (LinearLayout) inflater.inflate(R.layout.hours, layout, false);
			hoursLines[i].setId(i);
			layout.addView(hoursLines[i]);
		}
	}

	private String performTemp(double temp){
		String output;
		if ((int) temp == temp) {
			output = String.valueOf((int) temp);
		} else output = String.valueOf(temp);
		output += "°";
		return output;
	}

	private void loadImage(ImageView imageView, String address) {
		registerReceiver(imageReceiver, new IntentFilter("ImageService"), RECEIVER_EXPORTED);
		Intent intent = new Intent(this, ImageService.class);
		intent.putExtra("url", address);
		intent.putExtra("viewId", imageView.getId());
		startService(intent);
	}

	@Override
	protected void onPause() {
		super.onPause();
		Intent intent = new Intent(this, MeteoService.class);
		stopService(intent);
		Intent intent2 = new Intent(this, ImageService.class);
		stopService(intent2);
	}
}