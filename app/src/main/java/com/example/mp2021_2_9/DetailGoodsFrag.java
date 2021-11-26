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
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

// 굿즈 상세페이지
public class DetailGoodsFrag extends Fragment{
    ViewGroup v;
    GoodsMainFrag returnFrag;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.activity_goodsdetailed, container, false);

        returnFrag = new GoodsMainFrag();

        app_info.setNowPage("굿즈세부페이지");
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));

        Bundle bundle = getArguments();
        GoodsInfo_list goodsinfo = (GoodsInfo_list) bundle.getSerializable("GoodsInfo_list");
        Log.w("test", "id " + goodsinfo.getGoodsName() + ", " +goodsinfo.getUserid());

        TextView goods_name = v.findViewById(R.id.goods_name);
        TextView goods_price = v.findViewById(R.id.goods_price);
        TextView goods_location = v.findViewById(R.id.goods_location);
        TextView goods_detail = v.findViewById(R.id.goods_detail);

        goods_name.setText(goodsinfo.getGoodsName());
        goods_price.setText("가격: " + goodsinfo.getGoodsPrice() + "원");
        goods_location.setText(goodsinfo.getGoodsLocation());
        ImageView goodsImg = (ImageView) v.findViewById(R.id.goods_image);
        Glide.with(goodsImg).load(goodsinfo.getGoodsImgUrl()).into(goodsImg);
        goods_detail.setText(goodsinfo.getGoodsTxtUrl());


        return v;
    }
}
