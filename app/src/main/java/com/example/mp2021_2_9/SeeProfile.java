package com.example.mp2021_2_9;

import static android.app.Activity.RESULT_OK;

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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeeProfile extends Fragment {
    View view;

    ActivityResultLauncher resultLauncher;
    SharedPreferences pref = getContext().getSharedPreferences("current_info", 0);
    Bitmap validImg;

    TextView name, phoneNum, student_id, withdraw;
    EditText newPW, checkPW;
    Button changePW, selectImg;
    String policyPW = "((?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9가-힣]).{8,})";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_seeprofile, container, false);
        name = getActivity().findViewById(R.id.user_name);
        student_id = getActivity().findViewById(R.id.student_id);
        phoneNum = getActivity().findViewById(R.id.phone_number);
        newPW = getActivity().findViewById(R.id.newPW);
        checkPW = getActivity().findViewById(R.id.checkPW);
        changePW = getActivity().findViewById(R.id.changePW);
        withdraw = getActivity().findViewById(R.id.withdraw);

        // 회원 정보 출력
        name.setText(pref.getString("name", ""));
        student_id.setText(pref.getInt("student_id", 0));
        phoneNum.setText(pref.getString("phoneNum", ""));

        // 비밀 번호 변경
        changePW.setOnClickListener(v -> {
            if(newPW.getText().toString() != checkPW.getText().toString()){
                Toast.makeText(getActivity().getApplication(), "비밀번호가 일치하지 않습니다.\n 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                return ;
            }
            Pattern pattern = Pattern.compile(policyPW);
            Matcher matcher = pattern.matcher(newPW.getText().toString());
            if(!matcher.matches()){
               Toast.makeText(getActivity().getApplication(),
                       "비밀번호는 영소문자, 숫자, 특수문자가 반드시 1개 이상 들어간 8자 이상의 문자열이어야합니다.",
                       Toast.LENGTH_LONG).show();
               return ;
            }
        });
        selectImg.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            resultLauncher.launch(intent);
        });

        // 갤러리로부터 가져온 이미지 저장 및 버튼에 노출
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            Uri selectedImageUri = intent.getData();
                            try {
                                InputStream in = getActivity().getContentResolver().openInputStream(selectedImageUri);
                                validImg = BitmapFactory.decodeStream(in);
                                in.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );

        // 부스 관리자 등록 버튼에 대한 추가 구현 필요.

        //
        withdraw.setOnClickListener( v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("주의").setMessage("계정을 삭제하시겠습니까?");
            AlertDialog alertDialog = builder.create();

            builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    // 탈퇴시 데이터 베이스에서 삭제 후 홍보 메인 페이지로 이동.
                    /* 데이터 베이스에서 삭제 구현 필요 */
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new PromoteMainFrag()).commit();
                }
            });

            builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // 아무것도 안함
                }
            });
            alertDialog.show();
        });
        return view;
    }
}