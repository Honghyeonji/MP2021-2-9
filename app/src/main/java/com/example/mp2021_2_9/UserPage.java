package com.example.mp2021_2_9;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class UserPage extends Fragment {
    View view;
    Button infoCheck, goodsPost, promotionPost, goodsManage;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_userpage, container, false);

        infoCheck = view.findViewById(R.id.information_check);
        goodsPost = view.findViewById(R.id.goods_posting);
        promotionPost = view.findViewById(R.id.promotion_posting);
        goodsManage = view.findViewById(R.id.goods_management)

        // 개인정보 확인 페이지로 전환
        infoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new SeeProfile());
                transaction.commit();
            }
        });

        // 상품 등록 페이지로 전환
        goodsPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new AddItemActivity());
                transaction.commit();
            }
        });

        // 홍보글 등록 페이지 전환
        promotionPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new AddPromotionActivity());
                transaction.commit();
            }
        });

        // 등록 상품 관리 페이지 연결
        goodsManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 데이터 베이스에서 판매자인지를 확인하는 코드 필요 */
                /* 판매자 인증이 안된 사용자라면 */
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("알림").setMessage("판매자 인증이 안된 계정입니다. 판매자 인증하시겠습니까?");
                AlertDialog alertDialog = builder.create();

                builder.setPositiveButton("인증하기", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_container, new ManageGoods()).commit();
                }
                });

                builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // 아무것도 안함
                        }
                });
                alertDialog.show();

                /* 판매자 인증이 된 사용자라면 */
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, new AddPromotionActivity());
                transaction.commit();
            }
        });
        return view;
    }
}

