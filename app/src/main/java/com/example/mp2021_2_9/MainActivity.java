package com.example.mp2021_2_9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements onBackPressedListener {
    private static final String TAG = "Main_Activity";
    private BottomNavigationView mBottomNavigationView;
    private long pressedTime = 0;

    private boolean isManager;

    // 리스너 객체 생성
    private onBackPressedListener mBackListener;

    // DataBase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app_info.setKeyMap();
        app_info.setPageMap();

        Toolbar toolbar = findViewById(R.id.mp_toolbar);
        setSupportActionBar(toolbar);
        if (app_info.isEmptyStack())
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        else Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        app_info.setNowPage("부스메인페이지");
        getSupportActionBar().setTitle(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));

        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new PromoteMainFrag()).commit();

        mBottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.topromt:  // 홍보 메인
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
                        break;
                    case R.id.toshop:   // 상품 메인
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GoodsMainFrag()).commit();
                        break;
                    case R.id.toprofile:    // 로그인 or 개인정보화면
                        SharedPreferences preferences = getSharedPreferences("current_info", 0);
                        String Id = preferences.getString("ID", "");
                        if (Id.equals("")) {  // 비로그인상태 - 로그인 액티비티
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {              // 로그인상태 - 개인정보화면
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", preferences.getString("ID", ""));
                            UserPage userpage = new UserPage();
                            myRef.child(preferences.getString("ID", "")).child("isManager").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    isManager = dataSnapshot.getValue(boolean.class);
                                    preferences.edit().putBoolean("isManager", isManager);
                                    bundle.putBoolean("isManager", isManager);
                                    userpage.setArguments(bundle);
                                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, userpage).commit();
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    Log.w(TAG, "Failed to read value.", error.toException());
                                }
                            });
                        }
                        break;
                }
                return true;
            }
        });

    }

    // 리스너 설정 메소드
    public void setOnBackPressedListener(onBackPressedListener listener) {
        mBackListener = listener;
    }

    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    @Override
    public void onBackPressed() {

        // 다른 Fragment 에서 리스너를 설정했을 때 처리됩니다.
        if (mBackListener != null) {
            mBackListener.onBackPressed();
            Log.e("!!!", "Listener is not null");
            // 리스너가 설정되지 않은 상태라면 뒤로가기 버튼을 연속적으로 두번 눌렀을 때 앱이 종료
        } else {
            Log.e("!!!", "Listener is null");
            if (pressedTime == 0) {
                Snackbar.make(findViewById(R.id.main_layout),
                        " 한 번 더 누르면 종료됩니다.", Snackbar.LENGTH_LONG).show();
                pressedTime = System.currentTimeMillis();
            } else {
                int seconds = (int) (System.currentTimeMillis() - pressedTime);

                if (seconds > 2000) {
                    Snackbar.make(findViewById(R.id.main_layout),
                            " 한 번 더 누르면 종료됩니다.", Snackbar.LENGTH_LONG).show();
                    pressedTime = 0;
                } else {
                    super.onBackPressed();
                    Log.e("!!!", "onBackPressed : finish, killProcess");
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        }
    }
}
