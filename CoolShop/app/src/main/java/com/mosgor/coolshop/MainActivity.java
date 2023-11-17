package com.mosgor.coolshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.mosgor.coolshop.databinding.ElementsBinding;

public class MainActivity extends AppCompatActivity {

    final int COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        ElementsBinding binding = ElementsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.RB1.setId((int) 0);
        binding.RB2.setId((int) 1);
        binding.RB3.setId((int) 2);
        RadioButton[] radioButtons = {binding.RB1, binding.RB2, binding.RB3};
        CheckBox[] allButtons = {binding.CB1, binding.CB2, binding.CB3, binding.CB4, binding.CB5, binding.CB6, binding.CB7, binding.CB8,
                binding.CB9, binding.CB10};
        binding.BT2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                for (int i = 0; i < COUNT; i++){
                    if (allButtons[i].isChecked()){
                        allButtons[i].toggle();
                    }
                }
                binding.RG1.clearCheck();
                binding.ET.setText("");
            }
        });
        binding.BT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = 0;
                for (int i = 0; i < COUNT; i++){
                    if(allButtons[i].isChecked()){
                        counter++;
                    }
                }
                if (counter == 0) {
                    Toast.makeText(getApplicationContext(), "Выберите хотя бы один товар!", Toast.LENGTH_SHORT).show();
                }
                else if (binding.RG1.getCheckedRadioButtonId() == -1){
                    Toast.makeText(getApplicationContext(), "Выберите способ доставки!", Toast.LENGTH_SHORT).show();
                }
                else {
                    String message = "Кол-во товаров: " + counter + "\nТовары: ";
                    for (int i = 0; i < COUNT; i++){
                        if(allButtons[i].isChecked()){
                            message += allButtons[i].getText() + ", ";
                        }
                    }
                    message = message.substring(0, message.length() - 2);
                    message += "\nСпособ доставки: " + radioButtons[binding.RG1.getCheckedRadioButtonId()].getText();
                    if (!binding.ET.getText().toString().equals("")){
                        message += "\nВаш комментарий: " + binding.ET.getText();
                    }
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    binding.BT2.performClick();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        Toast.makeText(this, "Будьте аккуратны, при выходе данные не сохраняются!", Toast.LENGTH_LONG).show();
        super.onPause();
    }
}