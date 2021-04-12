package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static com.example.unicodelibraryapp.LibFineFragment.user_fineList;


public class PaidFineFragment extends Fragment {



    public PaidFineFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    RecyclerView paid_recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_paid_fine, container, false);
        paid_recyclerView = v.findViewById(R.id.paid_recyclerview);
        ArrayList <User> paidfineUsers = new ArrayList<>();
        paidfineUsers.add(new User("condensed physics","101","karishni","60004190004","50","1 day",String.valueOf(R.drawable.txt_bk)));
        paidfineUsers.add(new User("condensed physics","101","karishni","60004190004","50","1 day",String.valueOf(R.drawable.txt_bk)));
        paidfineUsers.add(new User("condensed physics","101","karishni","60004190004","50","1 day",String.valueOf(R.drawable.txt_bk)));
        paidfineUsers.add(new User("condensed physics","101","karishni","60004190004","50","1 day",String.valueOf(R.drawable.txt_bk)));
        LibrarianFineAdapter librarianFineAdapter = new LibrarianFineAdapter(getActivity(),paidfineUsers);
        paid_recyclerView.setAdapter(librarianFineAdapter);
        paid_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return v;
    }
}