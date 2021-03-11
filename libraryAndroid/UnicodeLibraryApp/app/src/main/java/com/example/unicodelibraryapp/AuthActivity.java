package com.example.unicodelibraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

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

    @Override
    public void onStart()
    {
        super.onStart();

        //Checking if user is already logged in
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this); //Getting the shared preferences
        if(sharedPreferences.contains(SessionInfo.SHARED_PREF_TOKEN_KEY) && sharedPreferences.contains(SessionInfo.SHARED_PREF_ROLE_KEY))
        {
            //Setting the logged user
            SessionInfo.loggedUser = new User(sharedPreferences.getString(SessionInfo.SHARED_PREF_TOKEN_KEY,null), sharedPreferences.getString(SessionInfo.SHARED_PREF_ROLE_KEY,null));

            if(SessionInfo.loggedUser.getRole() == User.Role.Librarian)
            {
                //Switching to librarian main activity
                Intent intent = new Intent(this, LibrarianActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else
            {
                //Switching to student main activity
                Intent intent = new Intent(this, StudentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}