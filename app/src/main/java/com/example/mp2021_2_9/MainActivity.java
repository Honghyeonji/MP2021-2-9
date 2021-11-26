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
        if(!app_info.isLoginBack()){
            Intent intent = new Intent(this, LoadingActivity.class);
            startActivity(intent);
        }
        app_info.setLoginBack(false);

        app_info.setKeyMap();
        app_info.setPageMap();
        app_info.setNowPage("부스메인페이지");
        toolbar = findViewById(R.id.mp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));
        getSupportActionBar().setTitle("");

        Log.v("test1", "isEmptyStack?" + app_info.isEmptyStack() + ", nowPage?" + app_info.getNowPage());

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
                        } else {              // 로그인상태 - 개인정보화면
                            Bundle bundle = new Bundle();
                            bundle.putString("ID", preferences.getString("ID", ""));
                            UserPage userpage = new UserPage();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                String layout = app_info.getPrevPage();
                if(app_info.isEmptyStack() && (app_info.getNowPage().equals("굿즈메인페이지")
                        || app_info.getNowPage().equals("부스메인페이지")
                        || app_info.getNowPage().equals("개인페이지"))){
                    createDialog();
                }else if(!app_info.isEmptyStack()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
                }else if(layout.equals("굿즈메인페이지")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GoodsMainFrag()).commit();
                }else if(layout.equals("부스메인페이지")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
                }else if(layout.equals("개인페이지")){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new UserPage()).commit();
                }
                app_info.setPrevPage(null);
                return true;
        }
        return onOptionsItemSelected(item);
    }

    private void createDialog(){
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
    public void onBackPressed() {
        String layout = app_info.getPrevPage();
        if(app_info.isEmptyStack() && (app_info.getNowPage().equals("굿즈메인페이지")
                || app_info.getNowPage().equals("부스메인페이지")
                || app_info.getNowPage().equals("개인페이지"))){
            createDialog();
        }else if(!app_info.isEmptyStack()){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
        }else if(layout.equals("굿즈메인페이지")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new GoodsMainFrag()).commit();
        }else if(layout.equals("부스메인페이지")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
        }else if(layout.equals("개인페이지")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new UserPage()).commit();
        }
        app_info.setPrevPage(null);
    }
}
