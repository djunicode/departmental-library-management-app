package com.example.unicodelibraryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FinesAdapter extends RecyclerView.Adapter<FinesAdapter.FinesViewHolder>
{
    class FinesViewHolder extends RecyclerView.ViewHolder
    {
        CardView finesCardview;

        FinesViewHolder(View view)
        {
            super(view);

            //Setting the cardview
            finesCardview = (CardView)view;
        }
    }

    private ArrayList<String> fines;

    FinesAdapter(ArrayList<String> bookFines)
    {
        fines = bookFines;
    }

    @NonNull
    @Override
    public FinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Inflating the cardview
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fines_cardview, parent, false);

        //Returning a new view Holder
        return new FinesViewHolder(cardView);
    }



    @Override
    public void onBindViewHolder(@NonNull FinesViewHolder holder, int position)
    {
        //Getting the cardview layout
        RelativeLayout cardviewLayout = holder.finesCardview.findViewById(R.id.fines_cardview_layout);

        //Setting book thumbnail
        ((ImageView)cardviewLayout.findViewById(R.id.book_thumbnail)).setImageResource(R.drawable.txt_bk);

        //Setting the book title
        ((TextView)cardviewLayout.findViewById(R.id.book_title)).setText(fines.get(position));
    }

    @Override
    public int getItemCount()
    {
        return fines.size();
    }
}