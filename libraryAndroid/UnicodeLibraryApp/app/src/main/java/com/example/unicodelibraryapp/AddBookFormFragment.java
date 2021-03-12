package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class AddBookFormFragment extends Fragment
{
    private String isbn; //The ISBN of the book

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_add_book_form, container, false);

        //Getting the isbn
        Bundle bundle = getArguments();
        isbn = bundle.getString("ISBN");

        //Displaying the ISBN
        ((TextView)fragmentView.findViewById(R.id.book_form_isbn)).setText(String.format("ISBN: %s", isbn));

        return fragmentView;
    }
}