package com.androidmasters.femaleapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Admin_add_police_fragment extends Fragment {

    private onFragmentSelectedBtnSelected listener;
    private Button btn_add_police;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.admin_add_police_fragment,container,false);
        btn_add_police=view.findViewById(R.id.btn_police_add);

        btn_add_police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.admin_add_police();
            }
        });

        return view;
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

    public interface onFragmentSelectedBtnSelected{
        public void admin_add_police();
    }
}
