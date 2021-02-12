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


    private ArrayList<String> books;

    BooklistAdapter(ArrayList<String> bookList)
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
        ((TextView)cardviewLayout.findViewById(R.id.book_cv_title)).setText(books.get(position));
        String bk_title = books.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("book_title",bk_title);
        holder.bookCardview.setOnClickListener(v -> Navigation.findNavController(cardviewLayout).navigate(R.id.choiceOfRole,bundle));

        
    }

    @Override
    public int getItemCount()
    {
        return books.size();
    }
}
