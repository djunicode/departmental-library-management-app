package com.example.unicodelibraryapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LibrarianFineAdapter  extends RecyclerView.Adapter<LibrarianFineAdapter.CardViewHolder> {
    Context ct;
//    List<String> bookname,bookid,username,usersap, fineamount,overdue;


   private ArrayList<User> user_fineList=new ArrayList<User>();
    LibrarianFineAdapter(Context ct,ArrayList<User> user_fineList){
        this.ct = ct;
        this.user_fineList = user_fineList;
    }
    @NonNull
    @Override
    public LibrarianFineAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View v = inflater.inflate(R.layout.fine_bookcardview,parent ,false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LibrarianFineAdapter.CardViewHolder holder, int position) {
        holder.name.setText(user_fineList.get(position).getBookname());
        holder.id.setText(user_fineList.get(position).getBookid());
        holder.username.setText(user_fineList.get(position).getUsername());
        holder.usersap.setText(user_fineList.get(position).getUsersap());
        holder.fineamt.setText(user_fineList.get(position).getFine_amount());
        holder.overduedays.setText(user_fineList.get(position).getOverdue());
        holder.img.setImageResource(Integer.parseInt(user_fineList.get(position).getBook_image()));

    }

    @Override
    public int getItemCount() {
        return user_fineList.size();
    }
    public class CardViewHolder extends  RecyclerView.ViewHolder{
        TextView name,id,username,usersap,fineamt,overduedays;
        Button status;
        ImageView img;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.book_cv_title);
            id = itemView.findViewById(R.id.fine_bookid);
            username = itemView.findViewById(R.id.fine_sname);
            usersap = itemView.findViewById(R.id.fine_sapid);
            name = itemView.findViewById(R.id.book_cv_title);
            fineamt =  itemView.findViewById(R.id.fine_amount);
            overduedays= itemView.findViewById(R.id.fine_overdue);
            status =itemView.findViewById(R.id.status_button);
            img = itemView.findViewById(R.id.book_cv_thumbnail);
        }
    }
}

