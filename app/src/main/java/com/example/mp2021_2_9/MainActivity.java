package com.example.mp2021_2_9;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main_Activity";
    private BottomNavigationView mBottomNavigationView;

    LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new testFrag1()).commit();

        mBottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.topromt:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new testFrag1()).commit();
                        break;
                    case R.id.toshop:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new testFrag2()).commit();
                        break;
                    case R.id.toprofile:
                        // 분기문 구현 필
                        Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });
    }

}
