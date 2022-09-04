package com.androidmasters.femaleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {


    CardView cardView_home, cardView_add_guardian,cardView_tips,
            cardView_view_guardian,cardView_emergency,cardView_logout;
    private onFragmentEmergenceButtonClicked listener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cardView_home=view.findViewById(R.id.card_view_home_user);
        cardView_add_guardian=view.findViewById(R.id.card_view_user_adds);

        cardView_tips=view.findViewById(R.id.card_view_user_tips);

        cardView_view_guardian=view.findViewById(R.id.card_view_user_guardian);

        cardView_emergency=view.findViewById(R.id.send_emergency_user_button);
        cardView_logout=view.findViewById(R.id.card_view_user_logout);

        return view;
    }

}
