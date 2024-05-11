package com.mosgor.camerax_qr_deeplink;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mosgor.camerax_qr_deeplink.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		binding.CamButton.setOnClickListener(view -> {
			Intent intent = new Intent(MainActivity.this, CameraActivity.class);
			requestLauncherToIntent.launch(intent);
		});
	}

	private ActivityResultLauncher<Intent> requestLauncherToIntent =
			registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
				if (result.getResultCode() == RESULT_OK) {
					String info = result.getData().getStringExtra("QR_INFO");
					Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT).show();
				}
			});
}