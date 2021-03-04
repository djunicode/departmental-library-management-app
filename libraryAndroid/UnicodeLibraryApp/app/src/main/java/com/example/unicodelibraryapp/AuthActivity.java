package com.example.unicodelibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthActivity extends AppCompatActivity
{

    public static RetrofitApiInterface retrofitApiInterface;  //The interface required by retrofit for api calls to backend

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //Initializing the toolbar
        Toolbar toolbar = findViewById(R.id.auth_toolbar);
        setSupportActionBar(toolbar);

        //Initializing retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.2:5000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Initializing retrofit interface
        retrofitApiInterface = retrofit.create(RetrofitApiInterface.class);
    }
}