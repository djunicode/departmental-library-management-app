package com.example.unicodelibraryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class UserProfileFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_user_profile, container, false);

        ((Button)fragmentView.findViewById(R.id.logout_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                logoutUser();
            }
        });

        return fragmentView;
    }

    private void logoutUser()
    {
        /*Logs out the currently logged user*/

        //Deleting the token and role stored in shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor sharedPreferenceEditor = sharedPreferences.edit();
        sharedPreferenceEditor.remove(SessionInfo.SHARED_PREF_TOKEN_KEY);
        sharedPreferenceEditor.remove(SessionInfo.SHARED_PREF_ROLE_KEY);
        sharedPreferenceEditor.commit();

        //Making logged user object null
        SessionInfo.loggedUser = null;

        //Switching to auth activity
//        Intent intent = new Intent(getActivity(), AuthActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }

}