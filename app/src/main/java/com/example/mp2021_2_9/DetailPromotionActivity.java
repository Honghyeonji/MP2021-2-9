package com.example.mp2021_2_9;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
// 부스 홍보 상세페이지

public class DetailPromotionActivity extends AppCompatActivity {
    //textview들
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = mDatabase.child("booth").child("booth2");

    FirebaseStorage storage = FirebaseStorage.getInstance("gs://mp2021-t9.appspot.com");
    StorageReference storageRef = storage.getReference();
    ImageView poster,boothMap;
    TextView boothName, boothLocation, boothTime, boothDetail;              // 동아리 or 부스 이름


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promtdetailed);
        poster  = (ImageView) findViewById(R.id.promt_poster);// 동아리 포스터 띄울 이미지뷰
        boothName  = (TextView) findViewById(R.id.promt_name);
        boothLocation = (TextView) findViewById(R.id.promt_location);      // 부스 상세위치 텍스트노출
        boothTime = (TextView) findViewById(R.id.promt_time);              // 부스운영시간
        boothDetail = (TextView) findViewById(R.id.promt_detail);          // 홍보글
        boothMap = (ImageView) findViewById(R.id.promt_map);              // 위치표시한 지도 이미지
    }
    @Override
    protected void onStart(){
        super.onStart();
        databaseReference.child("boothName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue(String.class);
                boothName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("boothLocation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String location = snapshot.getValue(String.class);
                boothLocation.setText(location);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReference.child("boothOpemTime").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Time = snapshot.getValue(String.class);
                boothTime.setText(Time);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        storageRef.child("booth/미리별/booth_미리별.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri).into(poster);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
