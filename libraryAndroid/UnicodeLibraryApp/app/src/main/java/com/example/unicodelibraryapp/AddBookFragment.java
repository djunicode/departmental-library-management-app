package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


public class AddBookFragment extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_add_book, container, false);

        //Setting click listener for isbn submit button
        AppCompatButton isbnSubmitBtn = fragmentView.findViewById(R.id.isbn_submit_btn);
        isbnSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                addIsbn();
            }
        });

        return fragmentView;
    }

    private void addIsbn()
    {
        /*Opens form for book with the entered ISBN*/

        //Getting the entered ISBN
        String isbn = ((EditText)getView().findViewById(R.id.isbn_textbox)).getText().toString();

        //ISBN Regex: ^(?=.{14,17}$)(97(8|9))?(\-)(\d){1,5}(\-)(\d){0,7}(\-)(\d){0,6}(\-)(\d|X)$

        //Checking if isbn is of valid format
        Pattern isbnRegexPattern = Pattern.compile("^(?=.{14,17}$)(97(8|9))?(\\-)(\\d){1,5}(\\-)(\\d){0,7}(\\-)(\\d){0,6}(\\-)(\\d|X)$");
        if(!isbnRegexPattern.matcher(isbn).matches())
        {
            //Invalid Isbn format
            Toast.makeText(getContext(), "Invalid ISBN Format", Toast.LENGTH_SHORT).show(); //Showing error message
            return;
        }
        else
        {
            //Creating bundle conaining the book isbn
            Bundle bundle = new Bundle();
            bundle.putString("ISBN", isbn);

            //Switching to book form fragment
            NavHostFragment navHostFragment = (NavHostFragment)(getActivity().getSupportFragmentManager().findFragmentById(R.id.librarian_nav_host_fragment));
            navHostFragment.getNavController().navigate(R.id.add_book_form_fragment, bundle);
        }

    }
}