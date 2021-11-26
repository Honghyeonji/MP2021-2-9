package com.example.mp2021_2_9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String TAG = "LoginActivity";

        app_info.setNowPage("로그인페이지");

        EditText inputId = (EditText) findViewById(R.id.inputIdnum);    // 아이디(학번)
        EditText inputPw = (EditText) findViewById(R.id.inputPW);       // 비밀번호
        Button login = (Button) findViewById(R.id.button_login);                    // 로그인버튼 - 메인화면으로
        TextView join = (TextView) findViewById(R.id.login_join);                   // 회원가입텍스트뷰 - 회원가입으로

        /* 데이터베이스부분 */
        FirebaseDatabase database = FirebaseDatabase.getInstance();         // db의 루트 주소 가져와서 database 객체에 연결

        // 로그인 버튼 - 데이터베이스에서 id, 비밀번호 확인 후 일치하면 홍보메인화면으로
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력한 id의 데이터목록 불러오기
                String tempId = inputId.getText().toString();
                String tempPw = inputPw.getText().toString();

                if (tempId.equals("") && tempPw.equals(""))      // 아이디, 비번 둘 다 빈칸일때
                    Toast.makeText(getApplicationContext(), "학번을 입력해주세요", Toast.LENGTH_SHORT).show();
                else if (tempPw.equals(""))      // 비번 빈칸일 때
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                else {
                    // user 노드에 연결
                    database.getReference("users").child(tempId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // user 값 가져오기
                            UserInfo_list user = snapshot.getValue(UserInfo_list.class);
                            String userId;
                            String userPw;
                            String userName;
                            String phoneNum;
                            boolean isManager;

                            if (user == null) {         // 해당 유저 정보 없을때
                                // user is null
                                Log.e(TAG, "User" + tempId + "is unexpectedly null");
                                Toast.makeText(getApplicationContext(), "일치하는 정보가 없습니다", Toast.LENGTH_SHORT).show();

                            } else {                    // user exists
                                // 비밀번호 일치 확인
                                if (user.getPassword().equals(tempPw)) {    // 비밀번호 일치할 때
                                    // 정보 받아오기
                                    userId = user.getId();
                                    userPw = user.getPassword();
                                    userName = user.getUserName();
                                    phoneNum = user.getPhoneNum();
                                    isManager = user.getIsManager();

                                    // 로그인유지 - preference에 저장
                                    SharedPreferences preferences = getSharedPreferences("current_info", 0);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    userName = user.getUserName();
                                    phoneNum = user.getPhoneNum();
                                    isManager = user.getIsManager();

                                    editor.putString("ID", userId);
                                    editor.putString("PW", userPw);
                                    editor.putString("name", userName);
                                    editor.putString("phoneNum", phoneNum);
                                    editor.putBoolean("isManager", isManager);
                                    editor.apply();


                                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                    // 메인화면 이동
                                    finish();

                                } else {                // 비밀번호 일치하지 않을 때
                                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                            Log.d(TAG, "user info : " + snapshot.getValue());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "getUser:onCancelled", error.toException());
                        }
                    });
                }
            }
        });


        // 회원가입 텍스트뷰
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 회원가입으로 이동
                Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        app_info.setLoginBack(true);
        finish();
    }
}