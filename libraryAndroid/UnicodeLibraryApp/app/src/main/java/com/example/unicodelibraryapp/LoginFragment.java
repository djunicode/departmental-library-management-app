package com.example.unicodelibraryapp;

import android.net.DnsResolver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment
{

    private View.OnClickListener loginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            LoginFragment.this.loginUser();
        }
    }; //The click listener for the login button

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_login, container, false);

        //Setting click listener for login button
        Button loginButton = fragmentView.findViewById(R.id.login_submit_btn);
        loginButton.setOnClickListener(loginClickListener);

        return fragmentView;
    }

    private void loginUser()
    {
        /*Logs the user with the entered credentials*/


        String un = "test@test.com";
        String pss = "123456";

        Call<AuthResponse> loginReq = AuthActivity.retrofitApiInterface.loginUser(un, pss);
        loginReq.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(response.isSuccessful())
                {
                    AuthResponse authResponse = (AuthResponse)response.body();
                    Toast.makeText(getActivity(), String.format("%s\n%s", authResponse.getToken(), authResponse.getRole()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}