package com.mosgor.notification;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.mosgor.notification.utils.BaseBuilder;
import com.mosgor.notification.utils.NotificationUtil;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NotificationUtil.getInstance().createNotificationChanel(this);

		NotificationUtil notificationUtil = NotificationUtil.getInstance();
		Log.d("NT", notificationUtil.a.toString());
		NotificationUtil notificationUtil2 = NotificationUtil.getInstance();
		Log.d("NT", notificationUtil2.increase().toString());
		NotificationUtil notificationUtil3 = NotificationUtil.getInstance();
		Log.d("NT", notificationUtil3.increase().toString());

		BaseBuilder baseBuilder = new BaseBuilder.Builder()
				.setA(1)
				.setB(2)
				.setC(3)
				.build();
		Button push = findViewById(R.id.pushButton);
		requestPermission();
		push.setOnClickListener(v -> {
			//NotificationUtil.getInstance()
			//		.showNotification(getApplicationContext(), "firstNoty", "Hello World!");
			OneTimeWorkRequest workRequest =
					new OneTimeWorkRequest.Builder(MyWorker.class).build();
			//PeriodicWorkRequest workRequest =
			//					new PeriodicWorkRequest.Builder(MyWorker.class, 3, TimeUnit.SECONDS).build();
			WorkManager workManager = WorkManager.getInstance(getApplicationContext());
			Operation enqueue
					//= workManager.enqueueUniquePeriodicWork("notnot", ExistingPeriodicWorkPolicy.REPLACE, workRequest);
					= workManager.enqueueUniqueWork("notnot", ExistingWorkPolicy.REPLACE, workRequest);
		});
	}
	// Запрос разрешения на использование уведомлений

	private final ActivityResultLauncher<String> requestPermissionLauncher =
			registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
				if (isGranted) {
					Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
				}
			});

	private void requestPermission() {
		requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
	}
}