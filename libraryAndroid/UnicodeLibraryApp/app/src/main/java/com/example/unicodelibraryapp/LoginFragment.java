package com.example.unicodelibraryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        //Getting the entered email and password
        String email = ((EditText)getView().findViewById(R.id.login_email)).getText().toString();
        String password = ((EditText)getView().findViewById(R.id.login_password)).getText().toString();

        //Checking if entered creds are valid
        if(validateInput(email, password))
        {
            //Sending log in request
            Call<AuthResponse> loginReq = AuthActivity.retrofitApiInterface.loginUser(email, password);
            loginReq.enqueue(new Callback<AuthResponse>() {
                @Override
                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                    //Checking if the request was successful
                    if (response.isSuccessful()) {
                        //Getting the returned json object
                        AuthResponse authResponse = (AuthResponse) response.body();

                        //Creating the user
                        SessionInfo.loggedUser = new User(authResponse.getToken(), authResponse.getRole());

                        //Saving the access token
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext()); //Getting the shared preferences file
                        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit(); //Getting the shared preference editor
                        sharedPrefEditor.putString(SessionInfo.SHARED_PREF_TOKEN_KEY, SessionInfo.loggedUser.getToken()); //Saving the access token
                        sharedPrefEditor.putString(SessionInfo.SHARED_PREF_ROLE_KEY, SessionInfo.loggedUser.getRole().toString()); //Saving the user role
                        sharedPrefEditor.commit(); //Saving the shared pref file

                        //Switching to main activity
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        Log.e("LOGIN FAILURE", Integer.valueOf(response.code()).toString());
                        Toast.makeText(getActivity(), "Failed to login. Try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AuthResponse> call, Throwable t) {
                    Log.e("LOGIN FAILURE", t.getMessage());
                    Toast.makeText(getActivity(), "Failed to login. Try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(getActivity(), "Invalid email or password. Try again", Toast.LENGTH_SHORT).show(); //Showing error message

    }

    private boolean validateInput(String email, String pss)
    {
        /*Checks whether the given email and pss are valid*/

        //Removing trailing whitespace
        email = email.trim();
        pss = pss.trim();

        //Checking if email and password are empty
        boolean isValid = (email.length() > 0) && (pss.length() > 0);

        //Checking if email format is valid
        isValid &= Patterns.EMAIL_ADDRESS.matcher(email).matches();

        return isValid;
    }
}