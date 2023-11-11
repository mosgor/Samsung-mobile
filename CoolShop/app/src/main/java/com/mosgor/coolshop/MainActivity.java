package com.mosgor.coolshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mosgor.coolshop.databinding.ElementsBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "Create", Toast.LENGTH_SHORT).show();
        ElementsBinding binding = ElementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("main"+"Bedore",s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("main"+"On",s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("main"+"After",s.toString());
            }
        });
        binding.RG1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            
            }
        });
    }

    @Override
    protected void onRestart() {
        Toast.makeText(this, "Restart", Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "Destroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


}