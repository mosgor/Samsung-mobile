package com.mosgor.gyroscopesensor;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor mAccelerator, mMagnetic;

    SensorManager mSensorManager;

    TextView tvXY, tvXZ, tvZY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerator = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetic = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        tvXY = findViewById(R.id.xy);
        tvXZ = findViewById(R.id.xz);
        tvZY = findViewById(R.id.zy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerator, SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this, mMagnetic, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    float[] accel = new float[3];
    float[] magnet = new float[3];
    float[] rotationMatrix = new float[16];
    float[] orientation = new float[3];

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accel = sensorEvent.values.clone();
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magnet = sensorEvent.values.clone();
        }
        SensorManager.getRotationMatrix(rotationMatrix, null, accel, magnet);
        SensorManager.getOrientation(rotationMatrix, orientation);
        tvXY.setText(String.valueOf(Math.round(Math.toDegrees(orientation[0]))));
        tvXZ.setText(String.valueOf(Math.round(Math.toDegrees(orientation[1]))));
        tvZY.setText(String.valueOf(Math.round(Math.toDegrees(orientation[2]))));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}