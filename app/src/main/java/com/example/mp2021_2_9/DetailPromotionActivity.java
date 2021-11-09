package com.example.mp2021_2_9;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;
// 부스 홍보 상세페이지

public class DetailPromotionActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_promtdetailed);

        ImageView poster = (ImageView) findViewById(R.id.promt_poster);             // 동아리 포스터 띄울 이미지뷰
        TextView boothName = (TextView) findViewById(R.id.promt_name);              // 동아리 or 부스 이름
        TextView boothLocation = (TextView) findViewById(R.id.promt_location);      // 부스 상세위치 텍스트노출
        TextView boothTime = (TextView) findViewById(R.id.promt_time);              // 부스운영시간
        TextView boothDetail = (TextView) findViewById(R.id.promt_detail);          // 홍보글
        ImageView boothMap = (ImageView) findViewById(R.id.promt_map);              // 위치표시한 지도 이미지뷰



    }
}
