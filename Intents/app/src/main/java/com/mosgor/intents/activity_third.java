package com.mosgor.intents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mosgor.intents.databinding.ActivityThirdBinding;

public class activity_third extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityThirdBinding binding = ActivityThirdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.P1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_third.this, MainActivity.class);
                startActivityForResult(intent, MainActivity.REQUEST_CODE);
            }
        });
        binding.P2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_third.this, SecondActivity.class);
                startActivityForResult(intent, MainActivity.REQUEST_CODE);
            }
        });
        binding.GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("key2", "From page 3");
                setResult(RESULT_OK,intent); //intent для передачи информации
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.REQUEST_CODE && resultCode == RESULT_OK){
            String message = data.getStringExtra("key2");
            Toast.makeText(this, message + " to page 3", Toast.LENGTH_SHORT).show();
        }
    }
}