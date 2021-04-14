package com.example.unicodelibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LibrarianActivity extends AppCompatActivity
{
    public AddBookFragment addBookFragment; //Reference to the add book fragment

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null)
        {
            if(intentResult.getContents() == null)
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            else
                this.addBookFragment.sendBarcode(intentResult.getContents());
        }
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

    public void scanBarcode()
    {
        /*Starts the barcode scanning process*/

        //Creating IntentIntegrator for ZXING
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.initiateScan();
    }
}