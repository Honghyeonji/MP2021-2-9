package com.example.mp2021_2_9;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class UserPageFrg extends Fragment {
    View view;
    Button infoCheck, goodsPost, promotionPost, goodsManage;
    Bundle bundle;
    String loginID; Boolean isManager;
    TextView developer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_userpage, container, false);

        app_info.setNowPage("개인페이지");
        TextView textView = getActivity().findViewById(R.id.mp_toolbar_text);
        textView.setText(app_info.getKeyMap(app_info.getPageMap(app_info.getNowPage())));
        app_info.setPrevPage("개인페이지");

        infoCheck = view.findViewById(R.id.information_check);
        goodsPost = view.findViewById(R.id.goods_posting);
        promotionPost = view.findViewById(R.id.promotion_posting);
        goodsManage = view.findViewById(R.id.goods_management);

        SharedPreferences preferences = getActivity().getSharedPreferences("current_info", 0);

        loginID = preferences.getString("ID", "");
        isManager = preferences.getBoolean("isManager", false);

        // 각각의 프래그먼트로 이동시 현재 로그인된 계정의 아이디 전달 객체 Bundle
        bundle = new Bundle();
        bundle.putString("ID", loginID);
        bundle.putBoolean("isManager", isManager);

        // 개인정보 확인 페이지로 전환
        infoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SeeProfileFrag sp = new SeeProfileFrag();
                sp.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, sp).commit();
            }
        });

        // 상품 등록 페이지로 전환
        goodsPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 판매자 인증이 안된 사용자라면 */
                if(!isManager) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("알림").setMessage("판매자 인증이 안된 계정입니다.\n판매자 인증하시겠습니까?")
                        .setPositiveButton("인증하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                SeeProfileFrag sp = new SeeProfileFrag();
                                sp.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, sp).commit();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // 아무것도 안함
                            }
                        })
                        .create()
                        .show();
                }else{
                    AddItemFrag ai = new AddItemFrag();
                    ai.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ai).commit();
                }
            }
        });

        // 홍보글 등록 페이지 전환
        promotionPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 판매자 인증이 안된 사용자라면 */
                if(!isManager) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("알림").setMessage("판매자 인증이 안된 계정입니다.\n판매자 인증하시겠습니까?")
                        .setPositiveButton("인증하기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                SeeProfileFrag sp = new SeeProfileFrag();
                                sp.setArguments(bundle);
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, sp).commit();
                            }
                        })

                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // 아무것도 안함
                            }
                        })
                        .create()
                        .show();
                }else {
                    /* 판매자 인증이 된 사용자라면 */
                    AddPromotionFrag ap = new AddPromotionFrag();
                    ap.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, ap).commit();
                }
            }
        });
        // 등록 상품 관리 페이지 연결
        goodsManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* 판매자 인증이 안된 사용자라면 */
                if (!isManager) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("알림")
                            .setMessage("판매자 인증이 안된 계정입니다.\n판매자 인증하시겠습니까?")
                            .setPositiveButton("인증하기", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    SeeProfileFrag sp = new SeeProfileFrag();
                                    sp.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, sp).commit();
                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // 아무것도 안함
                                }
                            })
                            .create()
                            .show();
                } else {
                    /* 판매자 인증이 된 사용자라면 */
                    ManageGoods mg = new ManageGoods();
                    mg.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, mg).commit();
                }
            }
        });
        return view;
    }
}
