package com.example.android.navigationadvancedsample.formscreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import com.example.android.navigationadvancedsample.R;




public final class Register extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ((Button)view.findViewById(R.id.signup_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View it) {
                FragmentKt.findNavController(Register.this).navigate(R.id.action_register_to_registered);
            }
        });
        return view;
    }
}
