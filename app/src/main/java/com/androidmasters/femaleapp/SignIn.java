package com.androidmasters.femaleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

    public class SignIn extends AppCompatActivity {
    private TextInputLayout email_layout,password_layout;
    private TextInputEditText email_editText,password_editText;
    private Button btnSignIn;
    private TextView txtSignup,forgot_password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    public String email,password;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email_layout=findViewById(R.id.textInputLayout_email_login);
        password_layout=findViewById(R.id.textInputLayout_pass_login);



        email_editText=findViewById(R.id.textInputEditText_email_login);
        password_editText=findViewById(R.id.textInputEditText_pass_login);
        btnSignIn=findViewById(R.id.btn_signIn);

        txtSignup=findViewById(R.id.textSignUp);
        forgot_password=findViewById(R.id.forgot_password);
        firebaseAuth=FirebaseAuth.getInstance();


        //button signIn clicked
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_details();
            }
        });

        // text signup clicked

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SignUp.class));
                finish();
            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDialogue();
            }
        });

        progressDialog=new ProgressDialog(this);

    }

    private void resetDialogue() {
        EditText resetEmail=new EditText(this);
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Enter your email and a password reset link wil be sent ");
        alertDialog.setView(resetEmail);
        alertDialog.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.show();
                progressDialog.setMessage("sending ..");
                String email=resetEmail.getText().toString().toLowerCase().trim();
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(SignIn.this, "Reset link sent successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(SignIn.this, "error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();



    }

    private void validate_details(){

        email=email_editText.getText().toString().toLowerCase().trim();

         password=password_editText.getText().toString().trim();
           if(TextUtils.isEmpty(email)){
            email_layout.setError("Please fill this field");
           }
           else if(TextUtils.isEmpty(password)){
               password_layout.setError("Please fill this field");
           }

           else {

               log_in_user();
               //Firebase Code goes here
           }
    }

    private void log_in_user() {
        progressDialog.show();
        progressDialog.setMessage("Signing in ...");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.dismiss();


                    if(email.equals("bakhuyamilka@gmail.com")){
                        startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                        finish();

                    }
                    else{
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        finish();


                    }







            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignIn.this, "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}