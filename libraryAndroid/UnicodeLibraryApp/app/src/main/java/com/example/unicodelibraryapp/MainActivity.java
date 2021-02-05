package com.example.unicodelibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting toolbar as action bar
        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        //Initializing the bottom nav
        initializBottomNav();
    }

    private void initializBottomNav()
    {
        /*Initializies the bottom navigation view*/

        BottomNavigationView bottomNav = findViewById(R.id.app_bottomnav); //Getting the bottom navigation view

        //Inflating the bottom nav menu
        bottomNav.inflateMenu(R.menu.bottomnav_menu);

        //Adding the nav controller
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment); //Getting the nav host fragment
        NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController()); //Adding the host fragment's nav controller to the bottom nav
    }
}