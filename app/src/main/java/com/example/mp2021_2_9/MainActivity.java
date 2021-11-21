package com.example.mp2021_2_9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";
    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new PromoteMainFrag()).commit();

        mBottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.topromt:  // 홍보 메인
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
                        break;
                    case R.id.toshop:   // 상품 메인
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GoodsMainFrag()).commit();
                        break;
                    case R.id.toprofile:    // 로그인 or 개인정보화면
                        SharedPreferences preferences = getSharedPreferences("current_info", 0);
                        String Id = preferences.getString("ID", "");
                        if(Id.equals("")){  // 비로그인상태 - 로그인 액티비티
                            Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }else{              // 로그인상태 - 개인정보화면
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new UserPage()).commit();
                        }

                        break;
                }
                return true;
            }
        });
    }

}
