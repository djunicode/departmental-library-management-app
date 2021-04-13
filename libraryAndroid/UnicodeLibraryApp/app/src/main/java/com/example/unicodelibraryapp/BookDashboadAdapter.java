package com.example.unicodelibraryapp;

import android.content.Context;
import android.media.tv.TvContentRating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookDashboadAdapter extends RecyclerView.Adapter<BookDashboadAdapter.CardViewHolder> {
    private ArrayList<Book> bookDashboard_list;
    Context ct;

    BookDashboadAdapter(Context ct, ArrayList<Book> bookDashboard_list) {
        this.ct = ct;
        this.bookDashboard_list = bookDashboard_list;
    }

    @NonNull
    @Override
    public BookDashboadAdapter.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);
        View v = inflater.inflate(R.layout.book_dashboard_recyclerview_row, parent, false);
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(v).navigate();
//            }
//        });
        return new BookDashboadAdapter.CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDashboadAdapter.CardViewHolder holder, int position) {
        holder.bookname.setText(bookDashboard_list.get(position).getName());
        holder.author.setText(bookDashboard_list.get(position).getAuthor());
        holder.subject.setText(bookDashboard_list.get(position).getSubject());

    }

    @Override
    public int getItemCount() {
        return bookDashboard_list.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        TextView bookname, author, subject;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            bookname = itemView.findViewById(R.id.book_name);
            author = itemView.findViewById(R.id.book_author);
            subject = itemView.findViewById(R.id.book_subject);
        }
    }

}
