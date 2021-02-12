package com.example.unicodelibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //Initializing the toolbar
        Toolbar toolbar = findViewById(R.id.auth_toolbar);
        setSupportActionBar(toolbar);
    }
}