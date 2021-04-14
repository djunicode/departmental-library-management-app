package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddBookIdFragment extends Fragment
{
    private String isbn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_add_book_id, container, false);

        //Getting isbn from bundle
        Bundle bundle = getArguments();
        isbn = bundle.getString("ISBN");

        //Adding click listener to submit button
        ((AppCompatButton)fragmentView.findViewById(R.id.book_id_submit_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Getting the entered number of copies
                int newCopies = Integer.parseInt(((EditText)getView().findViewById(R.id.book_quantity_txtbx)).getText().toString());

                addCopies(newCopies);
            }
        });

        return fragmentView;
    }

    private void addCopies(int newCopies)
    {
        /*Adds the new copies of the book*/

        Call<SuccessResponse> addCopiesCall = AuthActivity.retrofitApiInterface.addBookCopies(SessionInfo.loggedUser.getToken(), isbn, newCopies);
        addCopiesCall.enqueue(new Callback<SuccessResponse>() {
            @Override
            public void onResponse(Call<SuccessResponse> call, Response<SuccessResponse> response)
            {
                if(response.isSuccessful() && response.body().getSuccess())
                    Toast.makeText(getActivity(), "Book copies successfully incremented", Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(getActivity(), "Failed to increment book copies. Try Again", Toast.LENGTH_SHORT).show();
                    Log.e("ADD_COPIES_ERROR", response.message());
                }
            }

            @Override
            public void onFailure(Call<SuccessResponse> call, Throwable t)
            {
                Toast.makeText(getActivity(), "Failed to increment book copies. Try Again", Toast.LENGTH_SHORT).show();
                Log.e("ADD_COPIES_ERROR", t.getMessage());
            }
        });

    }
}