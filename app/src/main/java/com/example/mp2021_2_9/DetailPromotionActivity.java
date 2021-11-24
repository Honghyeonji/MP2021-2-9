package com.example.mp2021_2_9;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
// 부스 홍보 상세페이지
public class DetailPromotionActivity extends Fragment {
    ViewGroup v;
    TextView boothName,boothLocation,boothTime,boothDetail;
    ImageView poster;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.activity_promtdetailed, container, false);

        app_info.setNowPage("부스세부페이지");

        Bundle bundle = getArguments();
        BoothInfo_list boothinfo = (BoothInfo_list) bundle.getSerializable("BoothInfo_list");
        Log.w("test", "id " + boothinfo.getBoothName() + ", " +boothinfo.getUserId());

        poster  = (ImageView) v.findViewById(R.id.promt_poster);// 동아리 포스터 띄울 이미지뷰
        boothName  = (TextView)v.findViewById(R.id.promt_name);
        boothLocation = (TextView)v.findViewById(R.id.promt_location);      // 부스 상세위치 텍스트노출
        boothTime = (TextView)v.findViewById(R.id.promt_time);              // 부스운영시간
        boothDetail = (TextView)v.findViewById(R.id.promt_detail);          // 홍보글


        boothName.setText(boothinfo.getBoothName());
        boothLocation.setText(boothinfo.getBoothLocation());
        boothTime.setText(boothinfo.getBoothOpenTime());

        return v;
    }
}
