package com.example.mp2021_2_9;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class GoodsMainFrag extends Fragment {
    private RecyclerView recyclerView;
    private GoodsAdapter adapter;
    private ArrayList<GoodsInfo_list> goods_lists;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    String TAG = "GoodsMainActivity";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.shopping_main_screen, container, false);

        app_info.setNowPage("굿즈메인페이지");
        TextView textView = getActivity().findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));
        app_info.setPrevPage("굿즈메인페이지");

        goods_lists = new ArrayList<>();        // 상품 정보 담을 arrayList

        // DB 부분
        database = FirebaseDatabase.getInstance();      // DB 루트 연결
        databaseReference = database.getReference("goods");     // goods 최상위 노드 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스의 데이터 받아오기
                goods_lists.clear();        // 리스트 초기화
                for(DataSnapshot snapshot1 : snapshot.getChildren()){                   // 반복문으로 데이터 list 추출
                    GoodsInfo_list tempList = snapshot1.getValue(GoodsInfo_list.class); // 만들어둔 객체에 데이터 담기
                    goods_lists.add(tempList);                                          // 데이터 리스트에 추가
                }
                adapter.notifyDataSetChanged();     // 새로고침해야 반영
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, String.valueOf(error.toException()));
            }
        });

        // 리사이클러뷰에 GridLayoutManager 객체지정
        recyclerView = rootView.findViewById(R.id.putRecyclerView);
        recyclerView.setHasFixedSize(true);     // 리사이클러뷰 기존 성능 강화
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // 리사이클러뷰에 GoodsAdapter 객체 지정
        adapter = new GoodsAdapter(goods_lists, getContext());
        recyclerView.setAdapter(adapter);

        // 리사이클러뷰의 아이템 선택시 발생하는 이벤트
        adapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos){
                GoodsInfo_list tempList = goods_lists.get(pos); // 클릭한 굿즈 아이템의 정보 읽기
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DetailGoodsFrag f = new DetailGoodsFrag();
                Bundle bundle = new Bundle();// bundle에 tempList 담아 굿즈세부페이지 프래그먼트로 전달
                bundle.putSerializable("GoodsInfo_list", tempList);
                f.setArguments(bundle);
                transaction.replace(R.id.frame_container, f).commit();
            }
        });

        return rootView;
    }


}
