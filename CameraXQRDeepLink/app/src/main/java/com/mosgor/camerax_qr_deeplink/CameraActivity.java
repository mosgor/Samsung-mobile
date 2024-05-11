package com.mosgor.camerax_qr_deeplink;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Surface;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;

import com.google.common.util.concurrent.ListenableFuture;
import com.mosgor.camerax_qr_deeplink.databinding.CameraActivityBinding;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

public class CameraActivity extends AppCompatActivity {

	CameraActivityBinding binding;
	ImageCapture imageCapture;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = CameraActivityBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		if (permissionsGranted()) {
			startCamera();
		} else {
			askPermissions();
		}
		binding.take.setOnClickListener(view -> {
			takePhoto();
		});
	}

	private void takePhoto() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
		//File file = new File(getDirectoryName(), format.format(new Date())+".jpg");
		File file = null;
		try {
			file = File.createTempFile(format.format(new Date()),".jpg", getDirectoryName());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		ImageCapture.OutputFileOptions outputFileOptions =
				new ImageCapture.OutputFileOptions.Builder(file).build();
		imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
			@Override
			public void onImageSaved(@NotNull ImageCapture.OutputFileResults outputFileResults) {
				Toast.makeText(CameraActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onError(@NotNull ImageCaptureException exception) {
				Toast.makeText(CameraActivity.this, "Error", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private File getDirectoryName() {
//		String appFolderPath="";
//		appFolderPath = Environment.getExternalStorageDirectory().toString() + "/images";
//		File dir = new File(appFolderPath);
//		if (!dir.exists() && !dir.mkdir()) {}
//		return appFolderPath;
		File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		return dir;
	}

	public void startCamera() {
		ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);
		cameraProviderFuture.addListener(new Runnable() {
			@Override
			public void run() {
				try {
					ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
					bindPreview(cameraProvider);
				} catch (ExecutionException | InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

		}, ContextCompat.getMainExecutor(this));
	}

	private void bindPreview(ProcessCameraProvider cameraProvider) {
		Preview preview = new Preview.Builder().build();
		preview.setSurfaceProvider(binding.PV.getSurfaceProvider());
		//CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
		CameraSelector cameraSelector = new CameraSelector.Builder()
				.requireLensFacing(CameraSelector.LENS_FACING_BACK)
				//.addCameraFilter() фильтрация камер
				.build(); // для внешней камеры
		imageCapture = new ImageCapture.Builder()
				.setTargetRotation(Surface.ROTATION_0)
				.build();
		ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
				.setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
				.build();
		imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new QrCodeImageAnalyzer(new QrCodeFoundListener() {

			@Override
			public void onQRCodeFound(String QRCode) {
				Intent intent = new Intent();
				intent.putExtra("QR_INFO", QRCode);
				setResult(RESULT_OK, intent);
				finish();
			}

			@Override
			public void onQRCodeNotFound() {

			}
		}));
		cameraProvider.unbindAll();
		cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);
	}

	String[] PERMISSIONS = new String[]{android.Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

	private ActivityResultLauncher<String[]> requestLauncher =
			registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
				AtomicBoolean permissionGranted = new AtomicBoolean(true);
				permissions.forEach((key, value) -> {
					if (Arrays.asList(PERMISSIONS).contains(key) && !value) {
						Toast.makeText(this,
								key + " " + value,
								Toast.LENGTH_SHORT).show();
						permissionGranted.set(false);
					}
				});
				if (!permissionGranted.get()) {
					Toast.makeText(this,
							"Permission request denied",
							Toast.LENGTH_SHORT).show();
				} else {
					startCamera();
				}
			});

	public void askPermissions() {
		requestLauncher.launch(PERMISSIONS);
	}

	public boolean permissionsGranted() {
		AtomicBoolean good = new AtomicBoolean(true);
		Arrays.asList(PERMISSIONS).forEach(key -> {
			if (ContextCompat.checkSelfPermission(this, key) == PackageManager.PERMISSION_DENIED) {
				good.set(false);
			}
		});
		return good.get();
	}
}