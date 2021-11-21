package com.example.mp2021_2_9;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ManageGoods extends Fragment {

    String TAG = "ManageGoods";
    // DataBase
    SharedPreferences pref = getActivity().getSharedPreferences("current_info", 0);
    String loginID = pref.getString("ID", "");  // 데이터 베이스에서 검색시 필요
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    ArrayList<ListItem> items;
    ListViewAdapter adapter;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_managegoods, container, false);

        items = new ArrayList<ListItem>();
        adapter = new ListViewAdapter(this.getContext(), items);

        ListView listView = (ListView)view.findViewById(R.id.managegoods_list);
        listView.setAdapter(adapter);

        // 현재 로그인된 계정과 상품을 등록한 userId 값이 일치하는 상품 필터링
        Query query = myRef.child("goods").orderByChild("dimensions/userId").equalTo(loginID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adapter.item.clear();
                for (DataSnapshot item : snapshot.getChildren()){
                    GoodsInfo_list goods = item.getValue(GoodsInfo_list.class);
                    items.add(new ListItem(goods.getName()));
                    //adapter.item.add(new ListItem(item_name));
                }
                adapter.notifyDataSetChanged();
                //listView.setSelection(adapter.getCount() -1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, error.toException());
            }
        });

        return view ;
    }
}