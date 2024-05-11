package com.example.android.navigationadvancedsample;

import static androidx.navigation.ui.ActivityKt.setupActionBarWithNavController;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.ActivityKt;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.BottomNavigationViewKt;
import androidx.navigation.ui.NavControllerKt;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import kotlin.collections.SetsKt;

public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_container
        );
        navController = navHostFragment.getNavController();

        // Setup the bottom navigation view with navController
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        BottomNavigationViewKt.setupWithNavController(bottomNavigationView, navController);

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = new AppBarConfiguration.Builder(SetsKt.setOf(R.id.titleScreen, R.id.leaderboard,  R.id.register))
                                                    .build();

        ActivityKt.setupActionBarWithNavController(this, this.navController, this.appBarConfiguration);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavControllerKt.navigateUp(this.navController, this.appBarConfiguration);
    }


    public String getMyString(){
        return "MyString";
    }
}
