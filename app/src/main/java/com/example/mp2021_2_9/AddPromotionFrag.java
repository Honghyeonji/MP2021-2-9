package com.example.mp2021_2_9;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;


public class AddPromotionFrag extends Fragment {
    Bundle bundle;
    ActivityResultLauncher resultLauncher;
    String userid;
    View view;
    EditText boothname, boothlocation,boothtime, boothdetail;
    Button save;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    DatabaseReference base = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = base.child("booth");
    ImageButton addposter;

    Bitmap btm;
    Uri posteruri;
    String boothImgurl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_addpromotion, container, false);

        // Toolbar, app_info 설정 변경
        app_info.setNowPage("부스등록페이지");
        TextView textView = getActivity().findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));

        save = (Button)view.findViewById(R.id.savebutton);
        boothname= (EditText) view.findViewById(R.id.booth_name);
        boothlocation = (EditText) view.findViewById(R.id.booth_location);
        boothtime = (EditText) view.findViewById(R.id.booth_time);
        userid = getArguments().getString("ID");
        addposter = (ImageButton)view.findViewById(R.id.selectImageBtn);
        boothdetail = (EditText) view.findViewById(R.id.booth);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = boothname.getText().toString() + ".PNG"; //부스이름으로 파일 이름 설정
                StorageReference imgRef = storage.getReference("booth/" + filename);
                UploadTask uploadTask = imgRef.putFile(posteruri);         // 아까 갤러리에서 받아온 Uri 레퍼런스에 담아서 업로드
                boothImgurl = "https://firebasestorage.googleapis.com/v0/b/mp2021-t9.appspot.com/o/booth%2F" + filename + "?alt=media"; // 아까 갤러리에서 받아온 Uri 레퍼런스에 담아서 업로드
                //받은 부스 정보를 함수를 통해 파이어베이스에 올림
                addbooth(boothImgurl,boothlocation.getText().toString(), boothname.getText().toString(),boothtime.getText().toString(), userid,boothdetail.getText().toString());
                //저장버튼을 누르면 메인화면(부스 메인페이지)으로 전환
                PromoteMainFrag Pf = new PromoteMainFrag();
                Pf.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,Pf).commit();
            }
        });
        addposter.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            resultLauncher.launch(intent);
        });
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            posteruri = intent.getData();

                            // 비트맵 이미지 이미지뷰에 셋 할때 그대로 가져오면 너무 느려서 이렇게 써서 품질 좀 낮춰줘용
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8;

                            try {
                                InputStream in = view.getContext().getContentResolver().openInputStream(posteruri);
                                btm = BitmapFactory.decodeStream(in, null, options);
                                addposter.setImageBitmap(btm);
                                in.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        return view;

    }
    public void addbooth(String boothImgurl,String boothLocation, String boothName,String boothOpenTime, String userid, String boothTxturl){
        DatabaseReference ref = databaseReference.push(); //정보를 저장할때 랜덤한 값을 key로 설정하기 위해 push()로 저장
        AddPromotionData addPromotionData = new AddPromotionData(boothImgurl,boothLocation, boothName,boothOpenTime,userid,boothTxturl);
        ref.setValue(addPromotionData);

    }

}