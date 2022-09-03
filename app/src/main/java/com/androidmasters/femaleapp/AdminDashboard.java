package com.androidmasters.femaleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminDashboard extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        SecondFragment.onFragmentSelectedBtnSelected, Admin_add_police_fragment.onFragmentSelectedBtnSelected,
        Admin_Home_Fragment.onFragmentEmergenceButtonClicked




{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private ProgressDialog progressDialog;



    private TextInputLayout name_layout,email_layout,phone_layout,password_layout,confirmPassword_layout,name_police_layout,phone_police_layout;
    private TextInputEditText name_editText,email_editText,phone_editText,password_editText,confirmPassword_editText,name_police_editText,phone_police_editText;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        drawerLayout = findViewById(R.id.drawer_dashboard);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigation_admin);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        progressDialog=new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase=FirebaseDatabase.getInstance();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_admin, new Admin_Home_Fragment());
        fragmentTransaction.commit();



        //layouts

        /*if (firebaseUser == null) {
            startActivity(new Intent(getApplicationContext(), Verify_email.class));
            finish();
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.container_admin, new MainFragment());
            fragmentTransaction.commit();

        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId()==R.id.home)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_admin,new Admin_Home_Fragment());
            fragmentTransaction.commit();
        }
        if(item.getItemId()==R.id.add_user)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_admin,new SecondFragment() );
            fragmentTransaction.commit();
        }


        if(item.getItemId()==R.id.add_police_station)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_admin,new Admin_add_police_fragment() );
            fragmentTransaction.commit();
        }
        if(item.getItemId()==R.id.view_police_stations)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_admin,new Admin_View_Police_Station()  );
            fragmentTransaction.commit();
        }

        if(item.getItemId()==R.id.view_user)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_admin,new Admin_View_Registered_User());
            fragmentTransaction.commit();
        }

        if(item.getItemId()==R.id.view_victims)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_admin,new Admin_View_Victims());
            fragmentTransaction.commit();
        }


        if(item.getItemId()==R.id.exit)
        {
            AlertDialog.Builder alert=new AlertDialog.Builder(AdminDashboard.this);
            alert.setMessage("Are you sure you want to logout ?");
            alert.setTitle("END SESSION");
            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // dismiss the alert

                }
            });

            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(AdminDashboard.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(),SignIn.class));

                        }
                    },1000);

                }
            });

            alert.show();


        }





        return true;
    }

    @Override
    public void admin_register_user() {
        //Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();


        name_layout=findViewById(R.id.textInputLayout_user_name);
        email_layout=findViewById(R.id.textInputLayout_user_email);
        phone_layout=findViewById(R.id.textInputLayout_user_phone);
        password_layout=findViewById(R.id.textInputLayout_user_pass);
        confirmPassword_layout=findViewById(R.id.textInputLayout_user_confirmPass);

        // editTexts
        name_editText=findViewById(R.id.textInputEditText_user_name);
        email_editText=findViewById(R.id.textInputEditText_user_email);
        phone_editText=findViewById(R.id.textInputEditText_user_phone);
        password_editText=findViewById(R.id.textInputEditText_user_pass);
        confirmPassword_editText=findViewById(R.id.textInputEditText_user_confirm);


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
                                            Toast.makeText(getApplicationContext(), "Account created successfully. Please click the link sent to "+email +" to verify your account", Toast.LENGTH_SHORT).show();

                                        }


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });



                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }

        }





    @Override
    public void admin_add_police() {
        name_police_layout=findViewById(R.id.textInputLayout_police_name);
        phone_police_layout=findViewById(R.id.textInputLayout_police_phone);

        name_police_editText=findViewById(R.id.textInputEditText_police_name);
        phone_police_editText= findViewById(R.id.textInputEditText_police_phone);


        String name=name_police_editText.getText().toString().toUpperCase().trim();
        String phone=phone_police_editText.getText().toString().toUpperCase().trim();


        if(TextUtils.isEmpty(name)){
            name_police_layout.setError("Please fill this field");
        }
        else if(TextUtils.isEmpty(phone)){
            phone_police_layout.setError("Please fill this field");
        }
        else{
            progressDialog.show();
            progressDialog.setTitle("Please wait");
            progressDialog.setMessage("Adding ...");
            progressDialog.setCanceledOnTouchOutside(false);


            databaseReference=firebaseDatabase.getReference("Police");
            Police police=new Police(name,phone);
            databaseReference.child(phone).setValue(police).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "A police station added successfully. ", Toast.LENGTH_SHORT).show();

                    }


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }





    }

    @Override
    public void load_home_fragment() {

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_admin,new Admin_Home_Fragment());
        fragmentTransaction.commit();

    }

    @Override
    public void load_add_fragment() {
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_admin,new SecondFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void load_victim_fragment() {
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_admin,new Admin_View_Victims() );
        fragmentTransaction.commit();

    }

    @Override
    public void logout_fragment() {

    }

    @Override
    public void load_view_users() {
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_admin,new Admin_View_Registered_User() );
        fragmentTransaction.commit();

    }

    @Override
    public void load_add_police() {


        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_admin,new Admin_add_police_fragment() );
        fragmentTransaction.commit();

    }
}