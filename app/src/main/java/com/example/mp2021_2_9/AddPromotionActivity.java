package com.example.mp2021_2_9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPromotionActivity extends Fragment {
    Bundle bundle;
    String userid;
    View view;
    EditText boothname, boothlocation,boothtime;
    Button save;
    DatabaseReference base = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = base.child("booth");

//    String loginID;
//    TextInputEditText boothname, boothlocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_addpromotion, container, false);

//        loginID = getArguments().getString("ID");

        save = (Button)view.findViewById(R.id.savebutton);
        boothname= (EditText) view.findViewById(R.id.booth_name);
        boothlocation = (EditText) view.findViewById(R.id.booth_location);
        boothtime = (EditText) view.findViewById(R.id.booth_time);
        userid = getArguments().getString("ID");


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addbooth(boothlocation.getText().toString(), boothname.getText().toString(),boothtime.getText().toString(), userid);
                PromoteMainFrag Pf = new PromoteMainFrag();
                Pf.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,Pf).commit();
            }
        });
        return view;
    }
    public void addbooth(String boothLocation, String boothName,String boothOpenTime, String userid){
        AddPromotionData addPromotionData = new AddPromotionData(boothLocation, boothName,boothOpenTime,userid);
        databaseReference.push().setValue(addPromotionData);

    }

}