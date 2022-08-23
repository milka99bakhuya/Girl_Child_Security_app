package com.androidmasters.femaleapp;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecondFragment  extends Fragment

{
    private TextInputLayout name_layout,email_layout,phone_layout,password_layout,confirmPassword_layout;
    private TextInputEditText name_editText,email_editText,phone_editText,password_editText,confirmPassword_editText;
    private Button btnSignUp;
    private onFragmentSelectedBtnSelected listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_second,container,false);


        name_layout=view.findViewById(R.id.textInputLayout_user_name);
        email_layout=view.findViewById(R.id.textInputLayout_user_email);
        phone_layout=view.findViewById(R.id.textInputLayout_user_phone);
        password_layout=view.findViewById(R.id.textInputLayout_user_pass);
        confirmPassword_layout=view.findViewById(R.id.textInputLayout_user_confirmPass);

        // editTexts
        name_editText=view.findViewById(R.id.textInputEditText_user_name);
        email_editText=view.findViewById(R.id.textInputEditText_user_email);
        phone_editText=view.findViewById(R.id.textInputEditText_user_phone);
        password_editText=view.findViewById(R.id.textInputEditText_user_pass);
        confirmPassword_editText=view.findViewById(R.id.textInputEditText_user_confirm);

        btnSignUp=view.findViewById(R.id.btn_user_signUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.admin_register_user();
            }
        });







        return  view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentSelectedBtnSelected){
            listener= (onFragmentSelectedBtnSelected) context;
        }
        else
        {
            throw new ClassCastException(context.toString()+ "must implement listener");
        }
    }

    public  interface  onFragmentSelectedBtnSelected{
        public void admin_register_user();
    }
}
