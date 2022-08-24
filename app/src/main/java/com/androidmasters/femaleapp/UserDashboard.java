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
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentHome.onfragmentBtnSelected,FragmentEmergence.onFragmentEmergenceButtonClicked,
        FragmentProfile.onFragmentProfileButtonClicked{

    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LocationManager locationManager;




    DrawerLayout drawerLayout;

    //LongOperation recordAudioSync=null;


    Button stop;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView mic;
    /*  LongOperation recordAudioSyc;*/
    MediaRecorder recorder;


    EditText profileName,guardianName,profilePhone,guardianPhone;
    ProgressDialog progressDialog;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer);

        navigationView=findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(this);


        actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //  initialize fused components
        // fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);


        // default Fragment
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_user,new FragmentHome());
        fragmentTransaction.commit();

        firebaseAuth=FirebaseAuth.getInstance();

        rootNode=FirebaseDatabase.getInstance();
        progressDialog=new ProgressDialog(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // check which Item is selected
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId()==R.id.home)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_user,new FragmentHome());
            fragmentTransaction.commit();
        }

        else if(item.getItemId()==R.id.profile)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_user,new FragmentProfile());
            fragmentTransaction.commit();
        }
        else if(item.getItemId()==R.id.emergence)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_user,new FragmentEmergence());
            fragmentTransaction.commit();
        }
        else  if(item.getItemId()==R.id.reports)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_user,new FragmentReport());
            fragmentTransaction.commit();
        }
        else  if(item.getItemId()==R.id.share)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            //fragmentTransaction.replace(R.id.container_fragment,new FragmentShare());
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String body="Download this App";
            String sub="https/play.google.com/FemaleApp";
            intent.putExtra(Intent.EXTRA_TEXT,body);
            intent.putExtra(Intent.EXTRA_TEXT,sub);
            startActivity(Intent.createChooser(intent,"share using"));

            fragmentTransaction.commit();

        }
        else  if(item.getItemId()==R.id.help)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_user,new FragmentHelp());
            fragmentTransaction.commit();

        }
        else  if(item.getItemId()==R.id.logout){

            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Are you sure You want to log out?");
            alert.setPositiveButton("YES", (dialog, which) -> {
                try {
                    firebaseAuth.getInstance().signOut();
                    Toast.makeText(getApplicationContext(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(),SignIn.class)),3000);
                    startActivity(new Intent(getApplicationContext(),SignIn.class));

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            });
            alert.setNegativeButton("NO", (dialog, which) -> {

                //cancel log out

            });
            alert.create().show();




        }
        else
        {

            // no code here
        }
        return true;
    }

    @Override
    public void saveGuardian() {

    }

    @Override
    public void record_method() {

    }

    @Override
    public void stop_recording() {

    }

    @Override
    public void getLocation() {

    }

    @Override
    public void saveProfile() {

    }
}


