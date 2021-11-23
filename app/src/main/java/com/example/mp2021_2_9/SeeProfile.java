package com.example.mp2021_2_9;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeeProfile extends Fragment {
    String TAG = "SeeProfile";

    ActivityResultLauncher resultLauncher;

    // DataBase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    // Component
    View view;
    Bitmap validImg; // 사용자의 갤러리로부터 가져온 이미지를 저장할 비트맵
    TextView name, phoneNum, student_id, withdraw, logout;
    EditText newPW, checkPW;
    Button changePW;
    ImageButton selectImg;
    String policyPW = "^[a-zA-Z0-9]{8,}$";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String loginID = this.getArguments().getString("ID", "");
        view = inflater.inflate(R.layout.activity_seeprofile, container, false);

        name = view.findViewById(R.id.user_name);
        student_id = view.findViewById(R.id.student_id);
        phoneNum = view.findViewById(R.id.phone_number);
        newPW = view.findViewById(R.id.newPW);
        checkPW = view.findViewById(R.id.checkPW);
        changePW = view.findViewById(R.id.changePW);
        selectImg = view.findViewById(R.id.selectImageBtn);
        withdraw = view.findViewById(R.id.withdraw);
        logout = view.findViewById(R.id.logout);

        // 회원 정보 출력 (DB read)
        myRef.child(loginID).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo_list user = dataSnapshot.getValue(UserInfo_list.class);
                name.setText("이름: " + user.getUserName());
                student_id.setText("아이디: " + user.getId());
                phoneNum.setText("전화번호: " + user.getPhoneNum());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        // 비밀 번호 변경
        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 둘 중 하나라도 null값이라면
                if((newPW.getText() == null)||(checkPW.getText() == null)){
                    Toast.makeText(getActivity(), "올바른 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return ;
                }

                // 두 비밀번호가 일치하지 않을 때
                if(!newPW.getText().toString().equals(checkPW.getText().toString())){
                    Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.\n 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return ;
                }
                Pattern pattern = Pattern.compile(policyPW);
                Matcher matcher = pattern.matcher(newPW.getText().toString());

                // 비밀번호 정책을 부합하지 않을 때
                if(!matcher.matches()){
                    Toast.makeText(getActivity(),
                            "비밀번호는 영대소문자, 숫자로 이루어진 8자 이상의 문자열이어야합니다.",
                            Toast.LENGTH_LONG).show();
                    return ;
                }

                // 현재 로그인된 계정의 비밀번호 변경 (DB write)
                myRef.child(loginID).addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        UserInfo_list user = dataSnapshot.getValue(UserInfo_list.class);
                        user.setPassword(newPW.getText().toString());
                        Toast.makeText(getActivity(), "비밀번호가 정상적으로 바뀌었습니다.", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Changed password is: " + user.getPassword());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });


            }
        });

        selectImg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            resultLauncher.launch(intent);
        });

        // 갤러리로부터 가져온 이미지 저장
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            Uri selectedImageUri = intent.getData();
                            try {
                                InputStream in = view.getContext().getContentResolver().openInputStream(selectedImageUri);
                                validImg = BitmapFactory.decodeStream(in);
                                selectImg.setImageBitmap(validImg);
                                in.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        // 부스 관리자 등록 버튼에 대한 추가 구현 필요.

        // 로그아웃
        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("알림").setMessage("로그아웃하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new PromoteMainFrag()).commit();
                            Toast.makeText(getActivity(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Success logout!");
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // 아무것도 안함
                        }
                    })
                    .create()
                    .show();
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("주의").setMessage("계정을 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // 탈퇴시 데이터 베이스에서 삭제 후 홍보 메인 페이지로 이동. (DB write)
                                myRef.child(loginID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getActivity(), "계정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "Delete account");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "계정 삭제에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                });
                                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new PromoteMainFrag()).commit();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // 아무것도 안함
                            }
                        })
                        .create()
                        .show();
            }
        });

        return view;
    }
}