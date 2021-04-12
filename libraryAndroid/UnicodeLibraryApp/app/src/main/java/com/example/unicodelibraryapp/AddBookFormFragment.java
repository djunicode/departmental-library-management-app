package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookFormFragment extends Fragment
{
    private String isbn; //The ISBN of the book

    private View.OnClickListener submitBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            AddBookFormFragment.this.sendAddBookRequest();
        }
    }; //Click listener for the submit button

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

        //Setting click listener for submit button
        ((AppCompatButton)fragmentView.findViewById(R.id.book_form_submit_btn)).setOnClickListener(submitBtnClickListener);

        return fragmentView;
    }

    private void sendAddBookRequest()
    {
        /*Sends request to add the entered book details*/

        String bookName = ((EditText)getView().findViewById(R.id.book_name_txtbx)).getText().toString();
        String publisherName = ((EditText)getView().findViewById(R.id.publisher_txtbx)).getText().toString();
        String authorName = ((EditText)getView().findViewById(R.id.author_txtbx)).getText().toString();
        String subject = ((EditText)getView().findViewById(R.id.category_txtbx)).getText().toString();
        String description = ((EditText)getView().findViewById(R.id.description_txtbx)).getText().toString();
        String publishYear = ((EditText)getView().findViewById(R.id.publish_year_txtbx)).getText().toString();

        //Checking if the entered details are of correct format
        if(validDetails(bookName, publisherName, authorName, subject, description, publishYear))
        {
            //Sending the add book request
            Call<Void> addBookReq = AuthActivity.retrofitApiInterface.addNewBook(SessionInfo.loggedUser.getToken(), isbn, bookName, publisherName, authorName,
                    publishYear, subject, description);
            addBookReq.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful())
                        Toast.makeText(getActivity(), "Book Added", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Operation Failed. Try Again", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("ADD_BK_ERR", t.getMessage());
                    Toast.makeText(getActivity(), "Operation Failed. Try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
            Toast.makeText(getActivity(), "Invalid Details Entered. Try Again", Toast.LENGTH_SHORT).show();

    }

    private boolean validDetails(String bookName, String publisherName, String authorName, String subject, String dscr, String publishYear)
    {
        //Removing whitespace
        bookName = bookName.trim();
        publisherName = publisherName.trim();
        authorName = authorName.trim();
        subject = subject.trim();
        dscr = dscr.trim();
        publishYear = publishYear.trim();

        boolean areValid = true;
        areValid &= (bookName.length() > 0);
        areValid &= (publisherName.length() > 0);
        areValid &= (authorName.length() > 0);
        areValid &= (subject.length() > 0);
        areValid &= (dscr.length() > 0);
        areValid &= (Pattern.compile("^(\\d){4}$")).matcher(publishYear).matches();

        return areValid;
    }

}