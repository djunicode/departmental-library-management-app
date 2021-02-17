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

public class TeacherSignUp extends Fragment {

    TextView fname_tv,lname_tv,dept_tv,email_tv,pass_tv;
    EditText fname_edit,lname_edit,dept_edit,email_edit,pass_edit;
    Button teacher_register;
    public TeacherSignUp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_teacher_sign_up, container, false);

        fname_tv = v.findViewById(R.id.fname_tv);
        fname_edit = v.findViewById(R.id.fname_edit);
       lname_tv = v.findViewById(R.id.lname_tv);
        lname_edit = v.findViewById(R.id.lname_edit);
        dept_tv = v.findViewById(R.id.dept_tv);
        dept_edit = v.findViewById(R.id.dept_edit);
        email_tv = v.findViewById(R.id.email_tv);
        email_edit = v.findViewById(R.id.email_edit);
        pass_tv = v.findViewById(R.id.password_tv);
        pass_edit = v.findViewById(R.id.pass_edit);
        teacher_register=v.findViewById(R.id.fac_register_bt);
        teacher_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.login_fragment);
            }
        });
        return v;
    }
}