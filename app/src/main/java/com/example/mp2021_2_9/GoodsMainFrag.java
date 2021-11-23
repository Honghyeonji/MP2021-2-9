package com.example.mp2021_2_9;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


        adapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos){
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                GoodsInfo_list tempList = goods_lists.get(pos);
                DetailGoodsFrag f = new DetailGoodsFrag();
                Bundle bundle = new Bundle();
                bundle.putSerializable("GoodsInfo_list", tempList);
                f.setArguments(bundle);
                transaction.replace(R.id.frame_container, f).commit();
                Log.v("test", "pos:" + pos);
//                Log.w("test", tempList.getUserId() + ", " + tempList.getGoodsPrice());
            }
        });

        return rootView;
    }

    // 이미지 클릭시 상세페이지 연결 구현 필
}
