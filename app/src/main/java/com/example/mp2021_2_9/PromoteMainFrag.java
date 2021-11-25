package com.example.mp2021_2_9;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class PromoteMainFrag extends Fragment{
    private ViewGroup viewGroup;
    private int page_num = 4;

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

        setInit();

        app_info.setNowPage("부스메인페이지");

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

        adapter.setOnItemClickListener(new BoothAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos){
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                BoothInfo_list tempList = boothInfo_lists.get(pos);
                DetailPromotionActivity f = new DetailPromotionActivity();
                Bundle bundle = new Bundle();
                bundle.putSerializable("BoothInfo_list", tempList);
                f.setArguments(bundle);
                transaction.replace(R.id.frame_container, f).commit();
                Log.v("test", "pos:" + pos);
            }
        });


        return viewGroup;
    }

    private void setInit(){
        ViewPager2 viewPageSetUp = (ViewPager2) viewGroup.findViewById(R.id.viewpager);

        ImageSliderAdapter setUpPagerAdapter = new ImageSliderAdapter(getActivity(), page_num);
        viewPageSetUp.setAdapter(setUpPagerAdapter);

        CircleIndicator3 indicator = (CircleIndicator3) viewGroup.findViewById(R.id.indicator);
        indicator.setViewPager(viewPageSetUp);
        indicator.createIndicators(page_num, 0);

        viewPageSetUp.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPageSetUp.setOffscreenPageLimit(page_num);
        viewPageSetUp.setCurrentItem(1000);

        viewPageSetUp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels == 0){
                    viewPageSetUp.setCurrentItem(position);
                }
            }

            @NonNull
            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                indicator.animatePageSelected(position%page_num);
            }
        });

        final float pageMargin = (float) getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = (float) getResources().getDimensionPixelOffset(R.dimen.offset);
        viewPageSetUp.setPageTransformer(new ViewPager2.PageTransformer(){
            @Override
            public void transformPage(@NonNull View page, float position){
                float myOffset = (float) (position * -(2*pageOffset+pageMargin));
                if(viewPageSetUp.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL){
                    if(ViewCompat.getLayoutDirection(viewPageSetUp) == ViewCompat.LAYOUT_DIRECTION_RTL){
                        page.setTranslationX(-myOffset);
                    }else{
                        page.setTranslationX(myOffset);
                    }
                }else{
                    page.setTranslationY(myOffset);
                }
            }
        });
    }

}
