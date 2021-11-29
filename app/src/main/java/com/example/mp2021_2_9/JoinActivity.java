package com.example.mp2021_2_9;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    private DatabaseReference rDatabase;
    private FirebaseAuth aFirebaseAuth;
    boolean id_check, id_dupCheck, pw_check, pw_dupCheck, name_check, phone_check, terms_check;
    Dialog terms_dialog;
    CheckBox terms_checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        // app_info 설정 변경
        app_info.setNowPage("회원가입페이지");

        EditText join_id = (EditText) findViewById(R.id.join_id);
        EditText join_name = (EditText) findViewById(R.id.join_name);
        EditText join_pw = (EditText) findViewById(R.id.join_password);
        EditText join_pw_check = (EditText) findViewById(R.id.join_passwordCheck);
        EditText join_phone = (EditText) findViewById(R.id.join_phone);
        TextView id_text= (TextView) findViewById(R.id.join_id_text);
        TextView pw_text= (TextView) findViewById(R.id.join_password_text);
        TextView name_text = (TextView) findViewById(R.id.join_name_text);
        TextView pwCheck_text = (TextView) findViewById(R.id.join_passwordCheck_text);
        TextView phone_text = (TextView) findViewById(R.id.join_phone_text);
        terms_checkBox = findViewById(R.id.join_termCheck);


        // 자바 정규식을 이용하여 정보를 입력할 때마다 양식에 맞는지 체크
        join_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                id_text.setTextColor(Color.RED);
                if(Pattern.matches("^\\d{8}$", s.toString())){
                    id_text.setText("아이디 중복확인해주세요.");
                    id_check = true;
                }else{
                    id_text.setText("학번양식에 맞춰주세요.");
                    id_check = false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        join_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                pw_text.setText("영어 대소문자, 숫자를 포함하여 8자 이상");
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Pattern.matches("^[a-zA-Z0-9]{8,}$", s.toString())){
                    pw_check = true;
                    pw_text.setText("");
                }else{
                    pw_check = false;
                    pw_text.setTextColor(Color.RED);
                    pw_text.setText("비밀번호 양식에 맞춰주세요.");
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        join_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Pattern.matches("^[a-zA-Z가-힣]{2,}$", s.toString())) {
                    name_check = true;
                    name_text.setText("");
                }else{
                    name_check = false;
                    name_text.setText("올바른 이름을 입력해주세요.");
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        join_pw_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(join_pw.getText().toString().equals(s.toString())){
                    pwCheck_text.setTextColor(Color.BLUE);
                    pwCheck_text.setText("비밀번호가 일치합니다.");
                    pw_dupCheck=true;
                }else{
                    pwCheck_text.setTextColor(Color.RED);
                    pwCheck_text.setText("비밀번호가 일치하지 않습니다.");
                    pw_dupCheck=false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(join_pw.getText().toString().equals(s.toString())){
                    pwCheck_text.setText("");
                }
            }
        });
        join_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", s.toString())){
                    phone_check = true;
                    phone_text.setText("");
                }else{
                    phone_text.setTextColor(Color.RED);
                    phone_text.setText("올바른 휴대폰번호를 입력해주세요.");
                    phone_check = false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });



        // 이용약관 코드
        terms_dialog = new Dialog(JoinActivity.this);
        terms_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        terms_dialog.setContentView(R.layout.activity_terms);
        TextView show_terms = (TextView) findViewById(R.id.join_showTerms);
        show_terms.setOnClickListener(new View.OnClickListener() { // 보기 버튼 -> 이용약관 다이얼로그
            @Override
            public void onClick(View v) {
                showTerms();
            }
        });
        terms_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(terms_checkBox.isChecked()){
                    terms_check = true;
                }else{
                    terms_check = false;
                }
            }
        });


        // 아이디 중복체크 버튼
        rDatabase = FirebaseDatabase.getInstance().getReference("users");
        Button idCheckBtn = (Button) findViewById(R.id.join_id_check);
        idCheckBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(id_check) {
                    String tempId = join_id.getText().toString();
                    rDatabase.child(tempId) // 입력한 아이디와 일치하는 users 하위의 파일들이 있는지 확인
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            UserInfo_list value = snapshot.getValue(UserInfo_list.class);
                            if(value != null){ // 있다면 아이디 중복
                                id_text.setTextColor(Color.RED);
                                id_text.setText("이미 존재하는 아이디입니다.");
                                id_dupCheck = false;
                            }
                            else{ // 없다면 아이디 중복아님
                                id_text.setTextColor(Color.BLUE);
                                id_text.setText("사용가능한 아이디입니다.");
                                id_dupCheck = true;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(),
                                    "아이디 정보를 불러오기에 실패했습니다. 에러사항은 daedong2109@gmail.com으로 보내주시길 바랍니다."
                                    ,Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        aFirebaseAuth = FirebaseAuth.getInstance();

        // 회원가입 버튼 구현
        Button join = findViewById(R.id.join_join);
        TextView join_text = (TextView) findViewById(R.id.join_join_text);
        join_text.setTextColor(Color.RED);
        join.setOnClickListener(new View.OnClickListener() {
            // false인 요소들에 맞는 오류 메시지 뜨게 함
            @Override
            public void onClick(View v) {
                if(!id_check){
                    join_text.setText("아이디를 다시 확인해주세요.");
                }else if(!id_dupCheck){
                    join_text.setText("중복된 아이디인지 확인해주세요.");
                }else if(!name_check){
                    join_text.setText("이름을 다시 확인해주세요.");
                }else if(!phone_check){
                    join_text.setText("휴대폰 번호를 다시 확인해주세요.");
                }else if(!pw_check){
                    join_text.setText("비밀번호를 다시 확인해주세요.");
                }else if(!pw_dupCheck){
                    join_text.setText("비밀번호가 일치하는지 확인해주세요.");
                }else if(!terms_check){
                    join_text.setText("이용약관에 동의해주세요.");
                }else{ // 회원가입 시 필요한 모든 요소들이 다 체크되면 firebase에 유저 저장
                    String strid = join_id.getText().toString();
                    String strname = join_name.getText().toString();
                    String strphone = join_phone.getText().toString();
                    String strpw = join_pw.getText().toString();

                    // 유저 정보를 담는 객체 생성 (key, value)
                    Map<String, Object> tempuser = new HashMap<>();
                    tempuser.put("id", strid);
                    tempuser.put("isManager", false);
                    tempuser.put("password", strpw);
                    tempuser.put("phoneNum", strphone);
                    tempuser.put("userName", strname);
                    Map<String, Object> user = new HashMap<>();
                    user.put(strid, tempuser);

                    // 유저 정보를 DB 에 저장 (update 형식)
                    rDatabase.updateChildren(user);
                    Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                    // 회원가입 완료시 회원가입 페이지 종료 -> 로그인 페이지로 자동 이동
                    finish();
                }
            }
        });


        // 로그인 글씨 누르면 로그인 화면으로
        TextView goToLogin = (TextView) findViewById(R.id.id_toLogin);   // 로그인 텍스트뷰
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showTerms(){ // 이용약관 다이얼로그를 여는 메소드
        terms_dialog.show();

        Button okBtn = terms_dialog.findViewById(R.id.join_ok);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terms_checkBox.setChecked(true);
                terms_dialog.dismiss();
            }
        });
    }

}
