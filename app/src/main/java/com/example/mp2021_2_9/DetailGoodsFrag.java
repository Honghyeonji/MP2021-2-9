package com.example.mp2021_2_9;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

// 굿즈 상세페이지
public class DetailGoodsFrag extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_goodsdetailed);

        // viewpager 구현 부분 - 최대이미지 3개?

        TextView goodsName = (TextView) findViewById(R.id.goods_name);      // 상품 이름
        TextView goodsPrice = (TextView) findViewById(R.id.goods_price);    // 상품 가격
        TextView goodsDetail = (TextView) findViewById(R.id.goods_detail);  // 상품 홍보 상세글
        ImageView goods_map = (ImageView) findViewById(R.id.promt_map);     // 판매위치 지도 이미지뷰

    }
}
