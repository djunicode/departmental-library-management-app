package com.example.unicodelibraryapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Arrays;


public class BookListFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_book_list, container, false);

        //Initializing the recycler view
        RecyclerView recyclerView = fragmentView.findViewById(R.id.book_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation()));

        //Initialize recycler adapter
        ArrayList<String> bookTitles = new ArrayList<String>(Arrays.asList("Condensed Matter Physics Book 1000223", "Operating Systems 1", "Computer Networks 3"));
        BooklistAdapter adapter = new BooklistAdapter(bookTitles);
        recyclerView.setAdapter(adapter);

        return fragmentView;
    }


}