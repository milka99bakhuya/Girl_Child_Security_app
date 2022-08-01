package com.androidmasters.femaleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    // instance fields

    private TextInputLayout name_layout,email_layout,phone_layout,password_layout,confirmPassword_layout;
    private TextInputEditText name_editText,email_editText,phone_editText,password_editText,confirmPassword_editText;
    private Button btnSignUp;
    private TextView txtSignIn;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private  ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //layouts
        name_layout=findViewById(R.id.textInputLayout_name);
        email_layout=findViewById(R.id.textInputLayout_email);
        phone_layout=findViewById(R.id.textInputLayout_phone);
        password_layout=findViewById(R.id.textInputLayout_pass);
        confirmPassword_layout=findViewById(R.id.textInputLayout_confirmPass);

        // editTexts
        name_editText=findViewById(R.id.textInputEditText_name);
        email_editText=findViewById(R.id.textInputEditText_email);
        phone_editText=findViewById(R.id.textInputEditText_phone);
        password_editText=findViewById(R.id.textInputEditText_pass);
        confirmPassword_editText=findViewById(R.id.textInputEditText_confirm);

        btnSignUp=findViewById(R.id.btn_signUp);
        txtSignIn=findViewById(R.id.textSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_details();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
            }
        });


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);









    }

    private void validate_details(){

        String name=name_editText.getText().toString().toUpperCase().trim();
        String email=email_editText.getText().toString().toLowerCase().trim();
        String phone=phone_editText.getText().toString().trim();
        String password=password_editText.getText().toString().trim();
        String confirm_password=confirmPassword_editText.getText().toString().trim();
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(TextUtils.isEmpty(name)){
            name_layout.setError("Please fill this field");
        }
        else if(TextUtils.isEmpty(email)){
            email_layout.setError("Please fill this field");
        }
        else if(TextUtils.isEmpty(phone)){
            phone_layout.setError("Please fill this field");
        }
        else if(TextUtils.isEmpty(password)){
            password_layout.setError("Please fill this field");
        }
        else if(!password.equals(confirm_password))
        {
            password_layout.setError("Password mismatch ");
            confirmPassword_editText.setText(null);
            confirmPassword_layout.setError("password mismatch");
        }
        else if(!email.matches(emailPattern)){
            email_layout.setError("Please enter a valid email address");
        }
        else if(!password.matches(PASSWORD_PATTERN)){
            password_layout.setError("Please enter a strong password");
        }


        // phone number pattern
        // email pattern
        else {
            //Firebase Code goes here
            progressDialog.show();
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("signing Up ...");
            progressDialog.setCanceledOnTouchOutside(false);

            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    firebaseUser=firebaseAuth.getCurrentUser();
                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            databaseReference=firebaseDatabase.getReference("Users");
                            User user=new User(email,phone,name);
                            databaseReference.child(phone).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        progressDialog.dismiss();
                                        Toast.makeText(SignUp.this, "Account created successfully. Please click the link sent to "+email +" to verify your account", Toast.LENGTH_SHORT).show();

                                    }


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(SignUp.this, "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this, "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this, "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });


        }

    }
}