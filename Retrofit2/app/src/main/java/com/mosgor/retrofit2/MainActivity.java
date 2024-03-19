package com.mosgor.retrofit2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	Handler handler;

	Handler handlerLogin;

	ArrayList<Tag> tags;

	Network network = new Network();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		handlerLogin = new Handler(){
			@Override
			public void handleMessage(@NonNull Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1){
					network.getTags(handler);
				}
			}
		};

		handler = new Handler(){
			@Override
			public void handleMessage(@NonNull Message msg) {
				super.handleMessage(msg);

				if(msg.obj!=null && msg.obj instanceof List){
					Log.d("answer",((List<Tag>)msg.obj).toString());
					tags = (ArrayList<Tag>)msg.obj;
					network.likeTag(tags.get(4).id, handler);
				}
				if(msg.obj!=null && msg.obj instanceof Tag){
					Log.d("LikeTag",((Tag)msg.obj).toString());
				}
			}
		};
		network.login(handlerLogin);
		network.postTag(handler);
	}
}