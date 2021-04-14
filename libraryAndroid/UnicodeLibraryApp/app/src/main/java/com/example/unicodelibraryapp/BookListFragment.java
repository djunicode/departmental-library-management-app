package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookListFragment extends Fragment
{
    enum ListStates {Ascending, Descending, Search};

    private RecyclerView.OnScrollListener booksListRvScrollListener = new RecyclerView.OnScrollListener()
    {
        @Override
        public void onScrolled(RecyclerView rv, int dx, int dy)
        {
            super.onScrolled(rv,dx,dy);

            //Checking if fetch request has already been sent
            if(!fetchingNextPage)
            {
                LinearLayoutManager layoutManager = (LinearLayoutManager) booklistRecyclerView.getLayoutManager();
                int totalItems = adapter.getItemCount(); //Getting the total number of items in the list
                if (layoutManager != null && totalItems > 0 && layoutManager.findLastVisibleItemPosition() == totalItems - 10)
                    displayBookList(currentState, currentPageId + 1);
            }

        }

    }; //The scroll listener for the books list recycler view

    private RecyclerView booklistRecyclerView; //The recycler view used for displaying the list of books
    private BooklistAdapter adapter; //Adapter for the booklist recycler view
    private ChipGroup appliedFiltersChipGroup; //The chip group for the applied filters chips
    private int currentPageId = 1; //The id of the currently loaded page
    private boolean fetchingNextPage = false; //Tells if the next page is already being fetched
    public ListStates currentState = ListStates.Ascending; //The current state of the list

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_book_list, container, false);

        //Initializing the recycler view
        booklistRecyclerView = fragmentView.findViewById(R.id.book_list_rv);
        booklistRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        booklistRecyclerView.addItemDecoration(new DividerItemDecoration(booklistRecyclerView.getContext(), ((LinearLayoutManager)booklistRecyclerView.getLayoutManager()).getOrientation()));
        booklistRecyclerView.addOnScrollListener(booksListRvScrollListener);

        //Initialize recycler adapter
        adapter = new BooklistAdapter(new ArrayList<Book>());
        booklistRecyclerView.setAdapter(adapter);

        displayBookList(ListStates.Ascending, currentPageId);

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

    public void displayBookList(ListStates newState, int pageId)
    {
        /*Fetches the book list from the backend and displays it*/

        fetchingNextPage = true;

        //Creating the api request
        Call<BookListResponse> bookListResponseCall = (newState == ListStates.Ascending) ? AuthActivity.retrofitApiInterface.getAscendingBooks(SessionInfo.loggedUser.getToken(), pageId) :
                AuthActivity.retrofitApiInterface.getDescendingBooks(SessionInfo.loggedUser.getToken(), pageId);
        bookListResponseCall.enqueue(new Callback<BookListResponse>() {
            @Override
            public void onResponse(Call<BookListResponse> call, Response<BookListResponse> response)
            {
                if(response.isSuccessful())
                {
                    if(currentState != newState)
                        adapter.getBooksList().clear(); //Clearing the list as sorting order has changed

                    //Adding the books to the list
                    for(Book book : response.body().getBooks())
                    {
                        adapter.getBooksList().add(book);
                    }

                    adapter.notifyDataSetChanged();
                    currentState = newState;
                    currentPageId = pageId;
                }
                else
                {
                    Toast.makeText(getContext(), "Failed to retrieve books list", Toast.LENGTH_SHORT).show();
                    Log.e("BOOK_LIST_ERROR", response.message());
                }

                fetchingNextPage = false;
            }

            @Override
            public void onFailure(Call<BookListResponse> call, Throwable t)
            {
                Toast.makeText(getContext(), "Failed to retrieve books list", Toast.LENGTH_SHORT).show();
                Log.e("BOOK_LIST_ERROR", t.getMessage());
                fetchingNextPage = false;
            }
        });
    }

    public void searchAndDisplayBook(String title)
    {
        /*Searches for and displays the book with the given title*/

        Call<BookListResponse> searchCall = AuthActivity.retrofitApiInterface.searchForBook(SessionInfo.loggedUser.getToken(), title);
        searchCall.enqueue(new Callback<BookListResponse>() {
            @Override
            public void onResponse(Call<BookListResponse> call, Response<BookListResponse> response)
            {
                if(response.isSuccessful())
                {
                    //Clearing the previous results
                    adapter.getBooksList().clear();

                    for(Book searchResult : response.body().getBooks())
                    {
                        adapter.getBooksList().add(searchResult);
                    }

                    adapter.notifyDataSetChanged();
                    currentState = ListStates.Search;
                }
                else
                {
                    Toast.makeText(getActivity(), "Failed to retrieve search results", Toast.LENGTH_SHORT).show();
                    Log.e("BOOK_SEARCH_ERROR", response.message());
                }
            }

            @Override
            public void onFailure(Call<BookListResponse> call, Throwable t)
            {
                Toast.makeText(getActivity(), "Failed to retrieve search results", Toast.LENGTH_SHORT).show();
                Log.e("BOOK_SEARCH_ERROR", t.getMessage());
            }
        });

    }

}