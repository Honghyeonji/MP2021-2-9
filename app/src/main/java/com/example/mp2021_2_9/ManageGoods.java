package com.example.mp2021_2_9;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("goods");

    ArrayList<ListItem> goodsList;
    ListViewAdapter adapter;

    View view;
    String loginID;
    ImageButton searchBtn;
    EditText searchTxt;
    TextView text_no_goods;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_managegoods, container, false);

        loginID = getArguments().getString("ID");
        text_no_goods = view.findViewById(R.id.text_no_goods);

        goodsList = new ArrayList<ListItem>();
        adapter = new ListViewAdapter(this, goodsList);

        // Toolbar, app_info 설정 변경
        app_info.setNowPage("등록상품관리페이지");
        TextView textView = getActivity().findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));

        ListView listView = (ListView) view.findViewById(R.id.managegoods_list);
        searchBtn = (ImageButton) view.findViewById(R.id.searchBtn);
        searchTxt = (EditText) view.findViewById(R.id.searchTxt);
        listView.setAdapter(adapter);

        // 현재 로그인된 계정과 상품을 등록한 userId 값이 일치하는 상품 필터링
        Query query = myRef.orderByChild("userid").equalTo(loginID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                goodsList.clear();
                for (DataSnapshot item : snapshot.getChildren()) {
                    GoodsInfo_list goods = item.getValue(GoodsInfo_list.class);
                    goodsList.add(new ListItem(goods.getGoodsName(), goods.getGoodsIsSoldOut(), goods.getKey()));
                }
                adapter.notifyDataSetChanged();

                if(goodsList.isEmpty()){        // 등록한 상품이 하나도 없을 때
                    text_no_goods.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, error.toException());
            }
        });

        // 서치 버튼 클릭시 goodslist 변경경
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodsList.clear();
                Query query = myRef.orderByChild("userid").equalTo(loginID);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        goodsList.clear();
                        String keyword = searchTxt.getText().toString();
                        for (DataSnapshot item : snapshot.getChildren()) {
                            GoodsInfo_list goods = item.getValue(GoodsInfo_list.class);
                            if(goods.getGoodsName().contains(keyword)) {
                                goodsList.add(new ListItem(goods.getGoodsName(), goods.getGoodsIsSoldOut(), goods.getKey()));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, error.toException());
                    }
                });
            }
        });

       return view;
    }

}