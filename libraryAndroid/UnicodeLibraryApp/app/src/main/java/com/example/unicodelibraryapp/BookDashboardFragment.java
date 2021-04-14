package com.example.unicodelibraryapp;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class BookDashboardFragment extends Fragment {

    public BookDashboardFragment() {
        // Required empty public constructor
    }

   private TextView filter_tag;
   private RecyclerView book_dashboard_recyclerview;
   FloatingActionButton addbook_btn;


    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_book_dashboard, container, false);
        filter_tag = v.findViewById(R.id.filter_tag);
        book_dashboard_recyclerview = v.findViewById(R.id.book_dashboard_recyclerview);

        addbook_btn= v.findViewById(R.id.addbook_btn);
        addbook_btn.setBackgroundColor(R.color.maroon);
        Bundle bundle = new Bundle();
        bundle.putString("ISBN","isbn");
        addbook_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_book_dashboard_fragment_to_add_book_form_fragment,bundle);
            }
        });

        ArrayList <Book> book_dashboardlist = new ArrayList<>();
        book_dashboardlist.add(new Book("condensed physics","author name","subject"));
        book_dashboardlist.add(new Book("condensed physics","author name","subject"));
        book_dashboardlist.add(new Book("condensed physics","author name","subject"));
        book_dashboardlist.add(new Book("condensed physics","author name","subject"));
        book_dashboardlist.add(new Book("condensed physics","author name","subject"));
        book_dashboardlist.add(new Book("condensed physics","author name","subject"));
        book_dashboardlist.add(new Book("condensed physics","author name","subject"));
        BookDashboadAdapter bookDashboadAdapter = new BookDashboadAdapter(getActivity(),book_dashboardlist);
        book_dashboard_recyclerview.setAdapter(bookDashboadAdapter);
        book_dashboard_recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }
}