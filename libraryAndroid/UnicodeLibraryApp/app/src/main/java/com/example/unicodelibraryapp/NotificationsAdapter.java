package com.example.unicodelibraryapp;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsViewHolder>
{
    class NotificationsViewHolder extends RecyclerView.ViewHolder
    {
        CardView notificationsCardview;

        NotificationsViewHolder(View view)
        {
            super(view);

            //Setting the cardview
            notificationsCardview = (CardView)view;
        }
    }

    private ArrayList<String> notifications;

    NotificationsAdapter(ArrayList<String> bookNotifications)
    {
        notifications = bookNotifications;
    }

    @NonNull
    @Override
    public NotificationsAdapter.NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //Inflating the cardview
        View cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_cardview, parent, false);

        //Returning a new view Holder
        return new NotificationsAdapter.NotificationsViewHolder(cardView);
    }



    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.NotificationsViewHolder holder, int position)
    {
        //Getting the cardview layout
        LinearLayout cardviewLayout = holder.notificationsCardview.findViewById(R.id.notifications_cardview_layout);

        //Setting book status
        ((ImageView)cardviewLayout.findViewById(R.id.image)).setImageResource(R.drawable.overdue_icon);

        //((TextView)cardviewLayout.findViewById(R.id.status)).setText(notifications.get(position));
        ((TextView)cardviewLayout.findViewById(R.id.status)).setText(Html.fromHtml( "<font><b>" + notifications.get(position) + "</b></font>" + " is 1 day overdue"));
    }

    @Override
    public int getItemCount()
    {
        return notifications.size();
    }
}