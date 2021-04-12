package com.example.unicodelibraryapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> fragmentTitle = new ArrayList<>();

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitle.get(position);
    }

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
     return lstFragment.get(position);
    }

    @Override
    public int getCount() {
        return lstFragment.size();
    }
    public void AddFragment(Fragment fragment,String title){
        lstFragment.add(fragment);
        fragmentTitle.add(title);
    }
}
