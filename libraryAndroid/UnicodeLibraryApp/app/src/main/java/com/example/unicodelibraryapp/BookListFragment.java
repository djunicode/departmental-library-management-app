package com.example.unicodelibraryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookListFragment extends Fragment
{

    private RecyclerView.OnScrollListener booksListRvScrollListener = new RecyclerView.OnScrollListener()
    {
        @Override
        public void onScrolled(RecyclerView rv, int dx, int dy)
        {
            super.onScrolled(rv,dx,dy);
        }

    }; //The scroll listener for the books list recycler view

    private BooklistAdapter adapter; //Adapter for the booklist recycler view
    public boolean isSortedAscending; //Tells whether the book list is currently sorted in ascending order
    private ChipGroup appliedFiltersChipGroup; //The chip group for the applied filters chips

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_book_list, container, false);

        //Initializing the recycler view
        RecyclerView recyclerView = fragmentView.findViewById(R.id.book_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager)recyclerView.getLayoutManager()).getOrientation()));
        recyclerView.setOnScrollListener(booksListRvScrollListener);

        //Initialize recycler adapter
        //ArrayList<Book> bookTitles = new ArrayList<String>(Arrays.asList("Condensed Matter Physics Book 1000223", "Operating Systems 1", "Computer Networks 3"));
        adapter = new BooklistAdapter(new ArrayList<Book>());
        recyclerView.setAdapter(adapter);

        displayBookList(true);

        return fragmentView;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        ((StudentActivity)getActivity()).bookListFragment = this;
    }

    @Override
    public void onStop()
    {
        super.onStop();

        ((StudentActivity)getActivity()).bookListFragment = null;
    }

    public void displayBookList(boolean isAscending)
    {
        /*Fetches the book list from the backend and displays it*/


        isSortedAscending = isAscending;

        Call<BookListResponse> booksResponse = isAscending ? AuthActivity.retrofitApiInterface.getAscendingBooks(SessionInfo.loggedUser.getToken(), "1") :
                AuthActivity.retrofitApiInterface.getDescendingBooks(SessionInfo.loggedUser.getToken(), "1");
        booksResponse.enqueue(new Callback<BookListResponse>() {
            @Override
            public void onResponse(Call<BookListResponse> call, Response<BookListResponse> response)
            {
                if(response.isSuccessful())
                {
                    //Clearing the previous list
                    adapter.getBooksList().clear();

                    BookListResponse bookListResponse = (BookListResponse) response.body();
                    for(int a = 0; a < bookListResponse.getBooks().length; ++a)
                    {
                        adapter.getBooksList().add(bookListResponse.getBooks()[a]);
                    }

                    adapter.notifyDataSetChanged();
                }
                else
                    Log.e("BL_ERR", response.message());
            }

            @Override
            public void onFailure(Call<BookListResponse> call, Throwable t)
            {
                Log.e("BL_ERR", t.getMessage());
            }
        });
    }



}