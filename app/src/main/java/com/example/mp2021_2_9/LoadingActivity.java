package com.example.mp2021_2_9;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

public class LoadingActivity extends Activity { // 로딩페이지 설정
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        startLoading();
    }

    private void startLoading(){ // 로딩페이지를 열고 2초 뒤 종료
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },2000);
    }
}
