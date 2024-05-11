package com.example.android.navigationadvancedsample.homescreen;

import static android.content.Intent.getIntent;
import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.FragmentKt;

import com.example.android.navigationadvancedsample.R;
import com.example.android.navigationadvancedsample.formscreen.Register;

public final class Title extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_title, container, false);

        //////////////////////
        TextView viewById = (TextView) view.findViewById(R.id.game_title);
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra("EXTRA")) {
            Bundle inputData = intent.getExtras();
            String input = inputData.getString("EXTRA");
            viewById.setText(input);
        }
        ////////////////////////

        view.findViewById(R.id.about_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentKt.findNavController(Title.this).navigate(R.id.action_title_to_about);
            }
        });
        return view;
    }
}
