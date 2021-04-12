package com.example.unicodelibraryapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.HashSet;

public class BookListCategoryBottomSheetDialogFragment extends BottomSheetDialogFragment
{
    private HashSet<String> appliedFilters = new HashSet<>(); //The filters that have been applied

    private View.OnClickListener filterChipClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v)
        {
            Chip clickedChip = (Chip)v;
            if((Boolean)clickedChip.getTag())
                deselectChip(clickedChip);
            else
                selectChip(clickedChip);

        }
    }; //Click listener for the chips

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentView = inflater.inflate(R.layout.booklist_category_bottom_sheet, container, false);

        //Setting click listener for close btn
        ((AppCompatImageButton)fragmentView.findViewById(R.id.booklist_bottom_sheet_close_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Closing the bottom sheet
                BookListCategoryBottomSheetDialogFragment.this.dismiss();
            }
        });

        //Initializing the filters chip group
        initializeChipGroup(fragmentView);

        return fragmentView;
    }

    private void initializeChipGroup(View fragmentView)
    {
        /*Adds the filter chips to the chip group*/

        String filters[] = {"Physics", "Chemstry", "CS", "Mathematics", "Electronics"};

        //Adding chips
        ChipGroup chipGroup = fragmentView.findViewById(R.id.book_filters_chip_group);
        for(String filter : filters)
        {
            Chip chip = new Chip(getContext());
            chip.setText(filter);
            ((ChipDrawable)chip.getChipDrawable()).setChipBackgroundColorResource(R.color.white);
            chip.setTextColor(getResources().getColor(R.color.maroon));
            chip.setChipStrokeColorResource(R.color.maroon);
            chip.setChipStrokeWidth(4);
            chip.setOnClickListener(filterChipClickListener);
            chip.setTag(false);
            chipGroup.addView(chip);
        }
    }

    private void deselectChip(Chip chip)
    {
        /*Deselects the given chip and removes the filter from the set of applied filters*/

        //Deselecting the chip
        chip.setTag(false);
        ((ChipDrawable)chip.getChipDrawable()).setChipBackgroundColorResource(R.color.white);
        chip.setTextColor(getResources().getColor(R.color.maroon));

        //Removing the filter
        appliedFilters.remove(chip.getText());
    }

    private void selectChip(Chip chip)
    {
        /*Selects the given chip and adds the filter to the set of applied filters*/

        //Selecting the chip
        chip.setTag(true);
        ((ChipDrawable)chip.getChipDrawable()).setChipBackgroundColorResource(R.color.maroon);
        chip.setTextColor(getResources().getColor(R.color.white));

        //Adding the filter
        appliedFilters.add(chip.getText().toString());
    }

}
