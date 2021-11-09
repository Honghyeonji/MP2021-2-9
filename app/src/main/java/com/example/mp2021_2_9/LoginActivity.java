package com.example.mp2021_2_9;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_login);

        EditText inputId = (EditText) findViewById(R.id.inputIdnum);    // 아이디(학번)
        EditText inputPw = (EditText) findViewById(R.id.inputPW);       // 비밀번호
        CheckBox remainLogin = (CheckBox) findViewById(R.id.login_checkBox);        // 로그인상태유지 체크박스
        Button login = (Button) findViewById(R.id.button_login);                    // 로그인버튼 - 메인화면으로
        TextView join = (TextView) findViewById(R.id.login_join);                   // 회원가입텍스트뷰 - 회원가입으로


        // 체크박스 체크시 로그인유지 구현 - preference




        // 로그인 버튼 - 데이터베이스에서 학번, 비밀번호 확인 후 일치하면 홍보메인화면으로
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 일치 확인

                // 메인화면


            }
        });


        // 회원가입 텍스트뷰
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입으로 이동
            }
        });


    }
}