package com.example.mp2021_2_9;
//굿즈들의 정보를 입력받아 저장하는 역할


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

public class AddItemFrag extends Fragment  {
    Bundle bundle;
    View view;
    String userid;
    EditText itemname,itemprice, boothlocation, itemdetail;
    ImageButton additem;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    DatabaseReference base = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = base.child("goods"); // "goods"의 하위 값들을 불어오기 위함
    DatabaseReference myRef = base.child("users");
    Button save;
    boolean goodsIsSoldOut = false;
    ActivityResultLauncher resultLauncher;
    Bitmap btm;
    Uri imguri;
    String goodsImgurl ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_additem, container, false);

        // Toolbar, app_info 설정 변경
        app_info.setNowPage("상품등록페이지");
        TextView textView = getActivity().findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));

        additem = (ImageButton) view.findViewById(R.id.selectImageBtn);
        save = (Button) view.findViewById(R.id.savebutton);
        itemname = (EditText) view.findViewById(R.id.item_name);
        itemprice = (EditText) view.findViewById(R.id.item_price);
        boothlocation = (EditText) view.findViewById(R.id.booth_location);
        itemdetail = (EditText)view.findViewById(R.id.item_description);
        userid = getArguments().getString("ID");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = itemname.getText().toString() + ".PNG"; // 굿즈 이름을 파일 이름으로 설정
                StorageReference imgRef = storage.getReference("goods/" + filename);
                UploadTask uploadTask = imgRef.putFile(imguri);         // 아까 갤러리에서 받아온 Uri 레퍼런스에 담아서 업로드
                goodsImgurl = "https://firebasestorage.googleapis.com/v0/b/mp2021-t9.appspot.com/o/goods%2F" + filename + "?alt=media"; //storage에 있는 파일의 토큰형식을 이용하여 데베에 입력

                //받은 부스 정보를 함수를 통해 파이어베이스에 올림
                Additem(goodsImgurl,goodsIsSoldOut, boothlocation.getText().toString(), itemname.getText().toString(),itemprice.getText().toString(),userid ,itemdetail.getText().toString());
                //저장버튼을 누르면 메인화면(부스 메인페이지)으로 전환
                PromoteMainFrag Pf = new PromoteMainFrag();
                Pf.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,Pf).commit();
            }
        });
        additem.setOnClickListener(v -> {
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
                            imguri = intent.getData();

                            // 비트맵 이미지 이미지뷰에 셋 할때 그대로 가져오면 너무 느려서 이렇게 써서 품질 좀 낮춰줘용
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8;

                            try {
                                InputStream in = view.getContext().getContentResolver().openInputStream(imguri);
                                btm = BitmapFactory.decodeStream(in, null, options);
                                additem.setImageBitmap(btm);
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
    public void Additem(String goodsImgurl,boolean goodsIsSoldOut, String goodsLocation, String goodsName, String goodsPrice, String userid, String goodsTxturl){
        DatabaseReference ref = databaseReference.push(); //파이어베이스에 작성한 데이터를 넣는 함수

        AddPromotionData addgoodsdata = new AddPromotionData(goodsImgurl,goodsIsSoldOut, goodsLocation,goodsName,goodsPrice, ref.getKey(), userid,goodsTxturl);
        ref.setValue(addgoodsdata);
    }

}