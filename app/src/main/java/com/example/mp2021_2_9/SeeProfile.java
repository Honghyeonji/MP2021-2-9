package com.example.mp2021_2_9;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeeProfile extends Fragment{
    String TAG = "SeeProfile";

    ActivityResultLauncher resultLauncher;

    // DataBase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    // Storage 객체 생성 및 참조
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    // Component
    View view;
    Bitmap validImg; // 사용자의 갤러리로부터 가져온 이미지를 저장할 비트맵
    TextView name, phoneNum, student_id, withdraw, logout;
    EditText newPW, checkPW;
    Button changePW;
    ImageButton selectImg;
    String policyPW = "^[a-zA-Z0-9]{8,}$";
    Button register;
    Uri selectedImgUri;                 // Uri 갤러리에서 부른거 바로 스토리지 저장할 때 써야하니까 필드로 선언

    boolean isManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String loginID = this.getArguments().getString("ID", "");
        view = inflater.inflate(R.layout.activity_seeprofile, container, false);

        app_info.setNowPage("개인정보수정페이지");
        TextView textView = getActivity().findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));

        name = view.findViewById(R.id.user_name);
        student_id = view.findViewById(R.id.student_id);
        phoneNum = view.findViewById(R.id.phone_number);
        newPW = view.findViewById(R.id.newPW);
        checkPW = view.findViewById(R.id.checkPW);
        changePW = view.findViewById(R.id.changePW);
        selectImg = view.findViewById(R.id.selectImageBtn);
        withdraw = view.findViewById(R.id.withdraw);
        logout = view.findViewById(R.id.logout);
        register = view.findViewById(R.id.registerAdminBtn);


        // 회원 정보 출력 (DB read)
        myRef.child(loginID).addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserInfo_list user = dataSnapshot.getValue(UserInfo_list.class);
                name.setText("이름: " + user.getUserName());
                student_id.setText("학번: " + user.getId());
                phoneNum.setText("전화번호: " + user.getPhoneNum());
                isManager = user.getIsManager();
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
                        if(user != null) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("password", newPW.getText().toString());
                            myRef.child(loginID).updateChildren(map);
                        }
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
                            selectedImgUri = intent.getData();

                            // 비트맵 이미지 이미지뷰에 셋 할때 그대로 가져오면 너무 느려서 이렇게 써서 품질 좀 낮춰줘용
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8;

                            try {
                                InputStream in = view.getContext().getContentResolver().openInputStream(selectedImgUri);
                                validImg = BitmapFactory.decodeStream(in, null, options);
                                selectImg.setImageBitmap(validImg);
                                in.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        // 부스 관리자 등록 버튼 - > uri 파이어베이스 스토리지에 저장
        if(isManager){      // 이미 관리자인경우 클릭불가
            register.setClickable(false);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isManager){
                    Toast.makeText(getContext().getApplicationContext(), "이미 부스 관리자로 등록되어있습니다", Toast.LENGTH_SHORT).show();

                }else{
                    if(validImg == null){       // 이미지 선택 안 했을 경우
                        Toast.makeText(getContext().getApplicationContext(), "사진을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }else{      // 이미지 존재 -> 파이어베이스에 사진 저장
                        // 파일명 스트링으로 저장 (.PNG)
                        // 매니저는 아이디당 하나씩 올릴테니까 아이디로 올렸는데, goods나 booth는 키값같은 중복되지 않는 값으로 잘 맞춰서 올려야 함!
                        String filename = loginID + ".PNG";

                        // location값에 상위파일명 (goods/, booth/ 중 하나) + 정한 파일이름으로 스토리지레퍼런스 참조
                        StorageReference imgRef = storage.getReference("manager/" + filename);
                        UploadTask uploadTask = imgRef.putFile(selectedImgUri);         // 아까 갤러리에서 받아온 Uri 레퍼런스에 담아서 업로드
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            // 이건 성공했는지 아닌지 받아오는거. 근데 이게 보니까 success가 느리게 뜨더라~!~! 직접 새로고침해서 확인해봐
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.d(TAG, "UploadTask - success");
                                Toast.makeText(getContext().getApplicationContext(), "등록되었습니다", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext().getApplicationContext(), "등록 실패", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "UploadTask - onFailure() called");
                            }
                        });
                        //Toast.makeText(getContext().getApplicationContext(), "success upload", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        // 로그아웃
        logout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("알림").setMessage("로그아웃하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            // 프리퍼런스 저장된 로그인정보 null로 수정
                            editPrefs();

                            newActivity();
                            getActivity().finish();
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

        // 회원탈퇴
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("주의").setMessage("계정을 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // 현재 로그인 정보 삭제
                                editPrefs();

                                // 탈퇴시 데이터 베이스에서 삭제 후 홍보 메인 페이지로 이동. (DB write)
                                myRef.child(loginID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        newActivity();
                                        getActivity().finish();
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

    public void editPrefs(){
        SharedPreferences preferences = getActivity().getSharedPreferences("current_info", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", "");
        editor.putString("PW","");
        editor.putString("ID","");
        editor.putString("phoneNum","");
        editor.putBoolean("isManager",false);
        editor.apply();
    }

    private void newActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        app_info.setLoading(true);
    }
}