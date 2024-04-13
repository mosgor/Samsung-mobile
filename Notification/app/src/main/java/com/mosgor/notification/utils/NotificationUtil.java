package com.mosgor.notification.utils;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mosgor.notification.MainActivity;
import com.mosgor.notification.R;

public class NotificationUtil {

	private static final String CHANNEL_ID = "ChannelId";
	private static final String CHANNEL_NAME = "Channel Name";
	private static final Integer NOTIFICATION_ID = 1;

	static private NotificationUtil instance;
	public Integer a = 0;

	static public NotificationUtil getInstance() {
		if (instance == null) {
			instance = new NotificationUtil();
		}
		return instance;
	}

	private NotificationUtil() {
	}

	public Integer increase() {
		return a++;
	}

	public void createNotificationChanel(Context context) {
		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
		NotificationChannel adm = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
		adm.setDescription("ADM");
		adm.enableLights(true);
		adm.enableVibration(true);
		adm.setLightColor(Color.RED);
		notificationManager.createNotificationChannel(adm);
	}

	public void showNotification(Context context, String title, String description) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("text1", "some text");
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
		Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
				.setContentTitle(title)
				.setContentText(description)
				.setSmallIcon(R.drawable.ic_launcher_foreground)
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setCategory(NotificationCompat.CATEGORY_EVENT)
				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.build();
		NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		notificationManagerCompat.notify(NOTIFICATION_ID, notification);
	}
}