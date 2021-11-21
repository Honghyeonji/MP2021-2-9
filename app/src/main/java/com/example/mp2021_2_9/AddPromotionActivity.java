package com.example.mp2021_2_9;

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

public class AddPromotionActivity extends Fragment {
    View view;
    TextInputEditText boothname, boothlocation;
    Button save;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_addpromotion, container, false);
        save = (Button)view.findViewById(R.id.savebutton);
        boothname = (TextInputEditText) view.findViewById(R.id.booth_name);
        boothlocation = (TextInputEditText) view.findViewById(R.id.booth_location);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addbooth(boothname.getText().toString(), boothlocation.getText().toString());
            }
        });
        return view;
    }
    public void addbooth(String name, String location){

    }

}