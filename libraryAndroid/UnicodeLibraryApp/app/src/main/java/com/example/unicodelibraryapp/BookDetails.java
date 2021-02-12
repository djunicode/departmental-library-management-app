package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class BookDetails extends Fragment {

    Button issuebt,waitingbt;
    ImageView book_img;
    TextView book_name,book_author,book_subject,isbn,isbn_no,available,available_no,waiting,waiting_no,book_summary;

    public BookDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View book_view = inflater.inflate(R.layout.fragment_book_details, container, false);
        issuebt = book_view.findViewById(R.id.issue_bt);
        waitingbt = book_view.findViewById(R.id.waiting_bt);
        book_img = book_view.findViewById(R.id.book_img);
        book_name= book_view.findViewById(R.id.book_name);
        book_author= book_view.findViewById(R.id.book_author);
        book_subject= book_view.findViewById(R.id.subject);
        isbn = book_view.findViewById(R.id.isbn);
        isbn_no= book_view.findViewById(R.id.isbn_no);
        available= book_view.findViewById(R.id.available);
        available_no= book_view.findViewById(R.id.available_no);
        waiting= book_view.findViewById(R.id.waiting);
        waiting_no= book_view.findViewById(R.id.waiting_no);
        book_summary= book_view.findViewById(R.id.book_summary);
        issuebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issuebt.setVisibility(View.GONE);
                available.setVisibility(View.GONE);
                available_no.setVisibility(View.GONE);
                waiting.setVisibility(View.VISIBLE);
                waitingbt.setVisibility(View.VISIBLE);
                waiting_no.setVisibility(View.VISIBLE);
            }
        });
        
        Toast.makeText(getContext(),getArguments().getString("book_title"),Toast.LENGTH_SHORT).show();
        return book_view;
    }
}