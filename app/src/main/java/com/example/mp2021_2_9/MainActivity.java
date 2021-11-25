package com.example.mp2021_2_9;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

        app_info.setKeyMap();
        app_info.setPageMap();

        toolbar = findViewById(R.id.mp_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        app_info.setNowPage("부스메인페이지");
        getSupportActionBar().setTitle("대동대동");
        Log.v("test1", "isEmptyStack?" + app_info.isEmptyStack() + ", nowPage?" + app_info.getNowPage());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case android.R.id.home :
                        if(app_info.isEmptyStack() && (app_info.getNowPage().equals("굿즈메인페이지")
                                || app_info.getNowPage().equals("부스메인페이지")
                                || app_info.getNowPage().equals("개인페이지"))){
                            createDialog();
                        }else if(!app_info.isEmptyStack()){
                            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
                        }
                        return true;
                }
                return onOptionsItemSelected(item);
            }
        });

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                if(app_info.isEmptyStack() && (app_info.getNowPage().equals("굿즈메인페이지")
                        || app_info.getNowPage().equals("부스메인페이지")
                        || app_info.getNowPage().equals("개인페이지"))){
                    createDialog();
                }else if(!app_info.isEmptyStack()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new PromoteMainFrag()).commit();
                }
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
}
