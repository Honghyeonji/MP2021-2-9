package com.example.mp2021_2_9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class AddItem extends AppCompatActivity {
    Bundle bundle;
    String userid;
    EditText itemname,itemprice, boothlocation, itemdetail;
    ImageButton additem;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    DatabaseReference base = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = base.child("goods"); // "goods"의 하위 값들을 불어오기 위함
    DatabaseReference myRef = base.child("users");
    Button save;
    boolean goodsIsSoldOut = false;
    ActivityResultLauncher resultLauncher;
    Bitmap btm;
    Uri imguri;
    String goodsImgurl ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        bundle = getIntent().getBundleExtra("bundle");

        // Toolbar 설정
        TextView textView = findViewById(R.id.mp_toolbar_text);
        textView.setText("상품 홍보하기");

        Toolbar toolbar = (Toolbar) findViewById(R.id.mp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바의 디폴트 백버튼 이미지 변경
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.xbutton);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        additem = (ImageButton) findViewById(R.id.selectImageBtn);
        itemname = (EditText)findViewById(R.id.item_name);
        itemprice = (EditText)findViewById(R.id.item_price);
        boothlocation = (EditText)findViewById(R.id.booth_location);
        itemdetail = (EditText)findViewById(R.id.item_description);
        userid = bundle.getString("ID");

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
                                InputStream in = getContentResolver().openInputStream(imguri);
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
    }
    public void Additem(String goodsImgurl,boolean goodsIsSoldOut, String goodsLocation, String goodsName, String goodsPrice, String userid, String goodsTxturl){
        DatabaseReference ref = databaseReference.push(); //파이어베이스에 작성한 데이터를 넣는 함수

        AddPromotionData addgoodsdata = new AddPromotionData(goodsImgurl,goodsIsSoldOut, goodsLocation,goodsName,goodsPrice, ref.getKey(), userid,goodsTxturl);
        ref.setValue(addgoodsdata);
    }


    // 툴바에 '등록' 메뉴 추가 및 리스너 지정
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addpost_toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 뒤로가기 버튼 눌렀을 때
                finish();
                return true;
            }
            case R.id.postBtn: {
                String filename = itemname.getText().toString() + ".PNG"; // 굿즈 이름을 파일 이름으로 설정
                StorageReference imgRef = storage.getReference("goods/" + filename);
                UploadTask uploadTask = imgRef.putFile(imguri);         // 아까 갤러리에서 받아온 Uri 레퍼런스에 담아서 업로드
                goodsImgurl = "https://firebasestorage.googleapis.com/v0/b/mp2021-t9.appspot.com/o/goods%2F" + filename + "?alt=media"; //storage에 있는 파일의 토큰형식을 이용하여 데베에 입력

                //받은 부스 정보를 함수를 통해 파이어베이스에 올림
                Additem(goodsImgurl,goodsIsSoldOut, boothlocation.getText().toString(), itemname.getText().toString(),itemprice.getText().toString(),userid ,itemdetail.getText().toString());
                //저장버튼을 누르면 메인화면(상품 메인페이지)으로 전환
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);

            }
        }
        return super.onOptionsItemSelected(item);
    }


}