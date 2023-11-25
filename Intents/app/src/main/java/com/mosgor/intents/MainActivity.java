package com.mosgor.intents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mosgor.intents.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //Для работы нельзя забывать отредактировать gradle: buildFeatures{ viewBinding = true;}

    static final String key = "Key 1";

    static final int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.P2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        binding.P3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, activity_third.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        binding.GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("key2", "From page 1");
                setResult(RESULT_OK,intent); //intent для передачи информации
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            String message = data.getStringExtra("key2");
            Toast.makeText(this, message + " to page 1", Toast.LENGTH_SHORT).show();
        }
    }
}