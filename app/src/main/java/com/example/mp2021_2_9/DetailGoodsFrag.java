package com.example.mp2021_2_9;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// 굿즈 상세페이지
public class DetailGoodsFrag extends Fragment{
    ViewGroup v;
    GoodsMainFrag returnFrag;

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.activity_goodsdetailed, container, false);

        returnFrag = new GoodsMainFrag();

        app_info.setNowPage("굿즈세부페이지");
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));

        Bundle bundle = getArguments();
        GoodsInfo_list goodsinfo = (GoodsInfo_list) bundle.getSerializable("GoodsInfo_list");
        Log.w("test", "id " + goodsinfo.getGoodsName() + ", " +goodsinfo.getUserid());

        TextView goods_name = v.findViewById(R.id.goods_name);
        TextView goods_price = v.findViewById(R.id.goods_price);
        TextView goods_location = v.findViewById(R.id.goods_location);

        goods_name.setText(goodsinfo.getGoodsName());
        goods_price.setText("가격: " + goodsinfo.getGoodsPrice() + "원");
        goods_location.setText(goodsinfo.getGoodsLocation());

        ImageView goodsImg = (ImageView) v.findViewById(R.id.goods_image);
        Glide.with(goodsImg).load(goodsinfo.getGoodsImgUrl()).into(goodsImg);


//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference pathReference = storage.getReferenceFromUrl(goodsinfo.getGoodsTxtUrl());
//
//        try{
//            File localFile = File.createTempFile("detail", "txt");
//            try{
////                if(!path.exists()){
////                    path.mkdirs();
////                }
////                file.createNewFile();
//
//                final FileDownloadTask fileDownloadTask = pathReference.getFile(localFile);
//                fileDownloadTask.addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                        //다운로드 성공 후 할 일
////                        FileInputStream fis;
////                        try{
////                            FileReader fr = new FileReader(goodsinfo.getGoodsName() + ".txt");
////                            BufferedReader buffer = new BufferedReader(fr);
////
////                            String line;
////                            ArrayList<String> lines = new ArrayList<>();
////                            while((line = buffer.readLine()) != null){
////                                lines.add(line);
////                            }
////                            buffer.close();
////                        }catch (Exception e){
////                            e.printStackTrace();
////                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        //다운로드 실패 후 할 일
//                    }
//                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
//                    @Override
//                    //진행상태 표시
//                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
//
//                    }
//                });
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        return v;
    }
}
