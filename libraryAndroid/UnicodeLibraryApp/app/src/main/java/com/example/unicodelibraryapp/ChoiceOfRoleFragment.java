package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ChoiceOfRoleFragment extends Fragment {
    Button teacher_bt,student_bt;

    public ChoiceOfRoleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_choice_of_role, container, false);
        teacher_bt = v.findViewById(R.id.teacher_bt);
        student_bt = v.findViewById(R.id.student_bt);
        teacher_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Navigation.findNavController(v).navigate(R.id.teacher_signup);
            }
        });

        return v;
    }
}