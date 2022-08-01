package com.androidmasters.femaleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
private FirebaseAuth firebaseAuth;
private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();





        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firebaseUser!=null){
                    String email=firebaseAuth.getCurrentUser().getEmail();
                    if(email.equals("bakhuyamilka@gmail.com")){
                        startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                        finish();
                    }


                }

                else
                {
                    startActivity(new Intent(getApplicationContext(),SignIn.class));
                    finish();
                }

                startActivity(new Intent(getApplicationContext(),SignIn.class));
                finish();
            }
        },3000);





    }
}