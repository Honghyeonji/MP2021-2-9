package com.example.mp2021_2_9;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
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

import org.w3c.dom.Text;

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
        TextView textView = getActivity().findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));

        Bundle bundle = getArguments();
        GoodsInfo_list goodsinfo = (GoodsInfo_list) bundle.getSerializable("GoodsInfo_list");
        Log.w("test", "id " + goodsinfo.getGoodsName() + ", " +goodsinfo.getUserid());

        TextView goods_name = v.findViewById(R.id.goods_name);
        TextView goods_price = v.findViewById(R.id.goods_price);
        TextView goods_location = v.findViewById(R.id.goods_location);
        TextView goods_detail = v.findViewById(R.id.goods_detail);
        TextView goods_soldOutText = v.findViewById(R.id.soldOutText);

        boolean goodsIsSoldout = goodsinfo.getGoodsIsSoldOut();

        goods_name.setText(goodsinfo.getGoodsName());
        if(goodsIsSoldout){
            goods_name.setTextColor(Color.GRAY);
            goods_name.setPaintFlags(goods_name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            goods_soldOutText.setVisibility(View.VISIBLE);
        }

        goods_price.setText("가격: " + goodsinfo.getGoodsPrice() + "원");
        goods_location.setText(goodsinfo.getGoodsLocation());
        ImageView goodsImg = (ImageView) v.findViewById(R.id.goods_image);
        Glide.with(goodsImg).load(goodsinfo.getGoodsImgUrl()).into(goodsImg);
        goods_detail.setText(goodsinfo.getGoodsTxtUrl());


        return v;
    }
}
