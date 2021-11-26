package com.example.mp2021_2_9;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "Main_Activity";
    private BottomNavigationView mBottomNavigationView;
    Toolbar toolbar;

    private boolean isManager;

    private long backKeyPressedTime = 0;

    // DataBase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // app_info의 loading값이 false일 경우 로딩페이지를 내보냄
        if(!app_info.isLoading()){
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
        }
        // app_info의 loading값 reset
        app_info.setLoading(false);

        // app_info의 Map값들 세팅 + 현재 페이지 세팅
        app_info.setKeyMap();
        app_info.setPageMap();
        app_info.setNowPage("부스메인페이지");

        // custom Toolbar 세팅
        toolbar = findViewById(R.id.mp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));
        getSupportActionBar().setTitle("");

        // 하단 메뉴바 설정(BottomNavigationBar)
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new PromoteMainFrag()).commit();
        mBottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.topromt:  // 홍보 메인
                        app_info.setPrevPage(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
                        break;
                    case R.id.toshop:   // 상품 메인
                        app_info.setPrevPage(null);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GoodsMainFrag()).commit();
                        break;
                    case R.id.toprofile:    // 로그인 or 개인정보화면
                        app_info.setPrevPage(null);
                        SharedPreferences preferences = getSharedPreferences("current_info", 0);
                        SharedPreferences.Editor editor = preferences.edit();
                        String Id = preferences.getString("ID", "");
                        if (Id.equals("")) {  // 비로그인상태 - 로그인 액티비티
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {              // 로그인상태 - 개인정보화면
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", preferences.getString("ID", ""));
                            UserPageFrg userpage = new UserPageFrg();
                            myRef.child(preferences.getString("ID", "")).child("isManager").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    isManager = dataSnapshot.getValue(boolean.class);
                                    editor.putBoolean("isManager", isManager);
                                    editor.apply();
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // Toolbar의 BackButton 로직 구현
        switch (item.getItemId()) {
            case android.R.id.home :
                String layout = app_info.getPrevPage();
                // 현재 화면 = 굿즈메인페이지, 부스메인페이지, 개인페이지 -> 앱종료를 묻는 다이얼로그
                if(app_info.getNowPage().equals("굿즈메인페이지")
                        || app_info.getNowPage().equals("부스메인페이지")
                        || app_info.getNowPage().equals("개인페이지")){
                    createDialog();
                }else if(layout.equals("굿즈메인페이지")){ // 굿즈메인페이지 전환
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GoodsMainFrag()).commit();
                }else if(layout.equals("부스메인페이지")){ // 부스메인페이지 전환
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
                }else if(layout.equals("개인페이지")){ // 개인페이지 전환
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new UserPageFrg()).commit();
                }
                // 전화면 정보 초기화
                app_info.setPrevPage(null);
                return true;
        }
        return onOptionsItemSelected(item);
    }

    private void createDialog(){ // 앱종료를 묻는 다이얼로그
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("대동대동 종료").setMessage("어플을 종료하시겠습니까?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() { // 휴대폰에 내장된 BackButton 로직 커스텀 (toolbar의 BackButton과 동일)
        String layout = app_info.getPrevPage();
        if(app_info.getNowPage().equals("굿즈메인페이지")
                || app_info.getNowPage().equals("부스메인페이지")
                || app_info.getNowPage().equals("개인페이지")){
            createDialog();
        }else if(layout.equals("굿즈메인페이지")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GoodsMainFrag()).commit();
        }else if(layout.equals("부스메인페이지")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
        }else if(layout.equals("개인페이지")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new UserPageFrg()).commit();
        }
        app_info.setPrevPage(null);
    }
}
