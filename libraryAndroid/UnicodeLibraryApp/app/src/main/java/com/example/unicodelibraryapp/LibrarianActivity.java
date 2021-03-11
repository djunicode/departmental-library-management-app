package com.example.unicodelibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LibrarianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_librarian);

        //Setting the toolbar as action bar
        setSupportActionBar(findViewById(R.id.librarian_toolbar));

        //Initializing the bottom navigation
        initializeBottomNav();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflating the toolbar menu
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    private void initializeBottomNav()
    {
        /*Initializies the bottom navigation view*/

        BottomNavigationView bottomNav = findViewById(R.id.librarian_bottomnav); //Getting the bottom navigation view

        //Inflating the bottom nav menu
        bottomNav.inflateMenu(R.menu.librarian_bottomnav_menu);

        //Adding the nav controller
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.librarian_nav_host_fragment); //Getting the nav host fragment
        NavigationUI.setupWithNavController(bottomNav, navHostFragment.getNavController()); //Adding the host fragment's nav controller to the bottom nav android
    }
}