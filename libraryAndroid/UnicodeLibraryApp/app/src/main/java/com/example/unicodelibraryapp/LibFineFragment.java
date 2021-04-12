package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class LibFineFragment extends Fragment {

public static ArrayList<User> user_fineList;


    public LibFineFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_lib_fine, container, false);
        TabLayout tablayout = v.findViewById(R.id.tablayout);
        TabItem unpaidtab= v.findViewById(R.id.unpaidtab);
        TabItem paidtab= v.findViewById(R.id.paidtab);
        final ViewPager viewPager = v.findViewById(R.id.viewpager);
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.AddFragment(new UnpaidFineFragment(),"UNPAID");
        pagerAdapter.AddFragment(new PaidFineFragment(),"PAID");
        viewPager.setAdapter(pagerAdapter);
        tablayout.setupWithViewPager(viewPager);
        // setting tab layout with the view pager





        return v;
    }
}