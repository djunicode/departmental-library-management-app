package com.example.unicodelibraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooklistAdapter extends RecyclerView.Adapter<BooklistAdapter.BooklisViewHolder>
{
    class BooklisViewHolder extends RecyclerView.ViewHolder
    {
        CardView bookCardview;

        BooklisViewHolder(View view)
        {
            super(view);

            //Setting the cardview
            bookCardview = (CardView)view;
        }
    }


    private ArrayList<Book> books;

    BooklistAdapter(ArrayList<Book> bookList)
    {
        books = bookList;
    }

    @NonNull
    @Override
    public BooklisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Inflating the cardview
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cardview, parent, false);

        //Returning a new view Holder
        return new BooklisViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull BooklisViewHolder holder, int position)
    {
        //Getting the cardview layout
        RelativeLayout cardviewLayout = holder.bookCardview.findViewById(R.id.book_cardview_layout);

        //Setting book thumbnail
        ((ImageView)cardviewLayout.findViewById(R.id.book_cv_thumbnail)).setImageResource(R.drawable.txt_bk);

        //Setting the book title
        ((TextView)cardviewLayout.findViewById(R.id.book_cv_title)).setText(books.get(position).getName());

        //Setting the book author
        ((TextView)cardviewLayout.findViewById(R.id.book_cv_author)).setText(books.get(position).getAuthor());

        //Setting the book category
        ((TextView)cardviewLayout.findViewById(R.id.book_cv_category)).setText(books.get(position).getSubject());

        //Setting click listener on the card
        cardviewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Navigating to the book details fragment
                Navigation.findNavController(cardviewLayout).navigate(R.id.book_details_fragment);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return books.size();
    }

    public ArrayList<Book> getBooksList()
    {
        return books;
    }
}
