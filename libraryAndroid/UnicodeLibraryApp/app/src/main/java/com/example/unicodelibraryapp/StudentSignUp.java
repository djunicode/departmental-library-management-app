package com.example.unicodelibraryapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class StudentSignUp extends Fragment {

    TextView fname_tv,lname_tv,year_tv, sap_tv, email_tv,pass_tv;
    EditText fname_edit,lname_edit,year_edit, sap_edit, email_edit,pass_edit;
    Button student_register;
    public StudentSignUp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_student_sign_up, container, false);

        fname_tv = v.findViewById(R.id.student_fname_tv);
        fname_edit = v.findViewById(R.id.student_fname_edit);
        lname_tv = v.findViewById(R.id.student_lname_tv);
        lname_edit = v.findViewById(R.id.student_lname_edit);
        year_tv = v.findViewById(R.id.year_tv);
        year_edit = v.findViewById(R.id.year_edit);
        sap_tv = v.findViewById(R.id.sap_id_tv);
        sap_edit = v.findViewById(R.id.sap_id_edit);
        email_tv = v.findViewById(R.id.student_email_tv);
        email_edit = v.findViewById(R.id.student_email_edit);
        pass_tv = v.findViewById(R.id.student_password_tv);
        pass_edit = v.findViewById(R.id.student_pass_edit);
        student_register=v.findViewById(R.id.student_register_bt);
        student_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.login_fragment);
            }
        });
        return v;
    }
}