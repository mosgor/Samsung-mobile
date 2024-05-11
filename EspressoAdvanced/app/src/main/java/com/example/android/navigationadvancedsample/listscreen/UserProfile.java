package com.example.android.navigationadvancedsample.listscreen;


import static com.example.android.navigationadvancedsample.listscreen.Leaderboard.MyAdapter.USERNAME_KEY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.navigationadvancedsample.R;

public final class UserProfile extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        String name="Ali Connors";
        if(getArguments()!=null) {
           name = getArguments().getString(USERNAME_KEY);
        }
        ((TextView) view.findViewById(R.id.profile_user_name)).setText(name);
        return view;
    }
}
