package com.example.mp2021_2_9;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PromoteMainFrag extends Fragment {
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private ViewGroup viewGroup;

    private RecyclerView recyclerView;
    private BoothAdapter adapter;
    private ArrayList<BoothInfo_list> boothInfo_lists;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    String TAG = "PromotionMainFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.promotion_main_screen, container, false);
        /* ViewPager 부분 */
        //setInit();



        /* 상품 목록 부분 */
        boothInfo_lists = new ArrayList<>();    // 부스 정보 담을 arraylist

        // DB 부분
        database = FirebaseDatabase.getInstance();                  // DB 루트 연결
        databaseReference = database.getReference("booth");    // booth 최상위 노드 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스의 데이터 받아오기
                boothInfo_lists.clear();    // 초기화
                for(DataSnapshot snapshot1 : snapshot.getChildren()){                   // 반복문 돌면서 데이터list 추출
                    BoothInfo_list tempList = snapshot1.getValue(BoothInfo_list.class); // 만들어둔 객체에 데이터 담기
                    boothInfo_lists.add(tempList);                                      // 리스트 저장
                }
                adapter.notifyDataSetChanged();     // 새로고침 - 반영
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, String.valueOf(error.toException()));
            }
        });

        // 리사이클러뷰에 GridLayoutManager 객체 지정
        recyclerView = viewGroup.findViewById(R.id.putRcv);
        recyclerView.setHasFixedSize(true);         // 리사이클러뷰 기존 성능 강화
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));      // 그리드 레이아웃으로 설정

        // 리사이클러뷰에 BoothAdapter 객체 지정
        adapter = new BoothAdapter(boothInfo_lists, getContext());
        recyclerView.setAdapter(adapter);

        return viewGroup;
    }

    /*
    private void setInit(){
        sliderViewPager = viewGroup.findViewById(R.id.sliderViewPager);
        FragmentStateAdapter SetupPagerAdapter = new FragmentStateAdapter(getActivity()) {
            @Override
            public int getItemCount() {
                return 0;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return null;
            }
        };
    }

     */
}
