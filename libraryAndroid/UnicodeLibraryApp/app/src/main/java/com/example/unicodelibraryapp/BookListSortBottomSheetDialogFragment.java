package com.example.unicodelibraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BookListSortBottomSheetDialogFragment extends BottomSheetDialogFragment
{
    private RadioButton ascSortBtn; //The ascending sort radio button
    private RadioButton dscSortBtn; //The descending sort radio button
    private boolean sortAscending = true; //True is ascending and false if descending

    private View.OnClickListener sortDoneOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            if(((StudentActivity)getActivity()).bookListFragment.isSortedAscending != sortAscending)
                ((StudentActivity)getActivity()).setListSort(sortAscending);
        }
    }; //Click listener for the done button

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentView = inflater.inflate(R.layout.booklist_sort_bottom_sheet, container, false);

        //Setting click listener for close btn
        ((AppCompatImageButton)fragmentView.findViewById(R.id.booklist_bottom_sheet_close_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Closing the bottom sheet
                BookListSortBottomSheetDialogFragment.this.dismiss();
            }
        });

        //Setting click listeners for the radio buttons
        ascSortBtn = fragmentView.findViewById(R.id.asc_sort_rdbtn);
        dscSortBtn = fragmentView.findViewById(R.id.dsc_sort_rdbtn);
        ascSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sortAscending = true;

                //Deselecting the other radio button
                dscSortBtn.setChecked(false);
            }
        });
        dscSortBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sortAscending = false;

                //Deselecting the other radio button
                ascSortBtn.setChecked(false);
            }
        });

        //Setting click listener for the done button
        ((AppCompatButton)fragmentView.findViewById(R.id.book_sort_done_btn)).setOnClickListener(sortDoneOnClickListener);

        return fragmentView;
    }
}
