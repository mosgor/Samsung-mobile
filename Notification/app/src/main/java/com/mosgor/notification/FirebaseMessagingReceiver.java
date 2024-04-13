package com.mosgor.notification;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mosgor.notification.utils.NotificationUtil;

public class FirebaseMessagingReceiver extends FirebaseMessagingService {
	@Override
	public void onMessageReceived(@NonNull RemoteMessage message) {
		super.onMessageReceived(message);
		if(message.getNotification()!= null) {
			NotificationUtil.getInstance().showNotification(getApplicationContext(),
					message.getNotification().getTitle(),
					message.getNotification().getBody());
		}
	}

	@Override
	public void onNewToken(@NonNull String token) {
		super.onNewToken(token);
	}
}
