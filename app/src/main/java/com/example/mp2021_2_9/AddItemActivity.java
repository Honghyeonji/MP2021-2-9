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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddItemActivity extends Fragment {
    View view;
    String userid;
    EditText itemname,itemprice, boothlocation;
    DatabaseReference base = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = base.child("goods");
    DatabaseReference myRef = base.child("users");
    Button save;
    String goodsIsSoldout = "false";

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference databaseReference = database.getReference();
//    String loginID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_additem, container, false);

//        loginID = getArguments().getString("ID");

        save = (Button) view.findViewById(R.id.savebutton);
        itemname = (EditText) view.findViewById(R.id.item_name);
        itemprice = (EditText) view.findViewById(R.id.item_price);
        boothlocation = (EditText) view.findViewById(R.id.booth_location);
        String loginID = this.getArguments().getString("ID", "");
        myRef.child(loginID).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo_list user = dataSnapshot.getValue(UserInfo_list.class);
                userid = user.getId();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem(goodsIsSoldout, boothlocation.getText().toString(), itemname.getText().toString(),itemprice.getText().toString(),userid );
            }
        });
        return view;
    }
    public void additem(String goodsIsSoldout, String goodsLocation, String goodsName, String goodsPrice, String userid){
        DatabaseReference ref = databaseReference.push();
        AddPromotionData addgoodsdata = new AddPromotionData(goodsIsSoldout, goodsLocation,goodsName,goodsPrice, ref.getKey(), userid);
        ref.setValue(addgoodsdata);
    }

}

