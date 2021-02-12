package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;


public class NotificationsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_notifications, container, false);

        //Initializing the recycler view
        RecyclerView recyclerView = fragmentView.findViewById(R.id.notifications_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation()));

        //Initialize recycler adapter
        ArrayList<String> bookTitles = new ArrayList<String>(Arrays.asList("Condensed Matter Physics Book 1000223", "Operating Systems 1", "Computer Networks 3"));
        NotificationsAdapter adapter = new NotificationsAdapter(bookTitles);
        recyclerView.setAdapter(adapter);

        return fragmentView;
    }
}