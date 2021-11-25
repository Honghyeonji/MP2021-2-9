package com.example.mp2021_2_9;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    String TAG = "ListViewAdapter";

    // Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("goods");

    ArrayList<ListItem> goodsList;

    TextView itemName;
    TextView btn_isSoldOut;
    TextView delete;

    boolean tempValue;


    public ListViewAdapter(ManageGoods page, ArrayList<ListItem> data) {
        mContext = page.getContext();
        goodsList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ListItem getItem(int position) {
        return goodsList.get(position);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ListViewAdapter/getView", "execute");
        View view = mLayoutInflater.inflate(R.layout.layout_listview, null);

        itemName = (TextView)view.findViewById(R.id.item_name);
        btn_isSoldOut = (TextView)view.findViewById(R.id.isSoldOut);
        delete = (TextView)view.findViewById(R.id.delete);

        itemName.setText(goodsList.get(position).getName());

        if(goodsList.get(position).getIsSoldOut()) {    //true
            btn_isSoldOut.setTextColor(Color.RED);    // 품절시 빨간 글씨
            Log.d(TAG, "gray->red");
        }else {
            btn_isSoldOut.setTextColor(Color.GRAY);  // 재고가 있을시 회색 글씨
            Log.d(TAG, "red->gray");
        }

        // 품절버튼 이벤트
        btn_isSoldOut.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                // 해당 데이터의 goodsIsSoldOut 값 받아서 tempValue에 저장
                tempValue = goodsList.get(position).getIsSoldOut();

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("확인");

                if (tempValue == false) {   // 품절아니었을 때  -> true(품절)로
                    builder.setMessage("품절처리 하시겠습니까? \n").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            myRef.child(getItem(position).getKey()).child("goodsIsSoldOut").getRef().setValue(true);
                            Toast.makeText(view.getContext().getApplicationContext(), "품절처리 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // 아무것도 안함
                        }
                    })
                            .create()
                            .show();
                }else if(tempValue) {    // 품절이었을 때 -> false(품절아님)으로
                    builder.setMessage("품절 취소처리 하시겟습니까? \n").setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            myRef.child(getItem(position).getKey()).child("goodsIsSoldOut").getRef().setValue(false);
                            Toast.makeText(view.getContext().getApplicationContext(), "품절 취소 처리 되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // 아무것도 안함
                        }
                    })
                            .create()
                            .show();
                }

                    notifyDataSetChanged();
            }
        });

        // 삭제 버튼 클릭시 해당 상품 삭제 후 Adapter에 반영
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("주의").setMessage("해당 상품 관련 데이터가 모두 삭제됩니다. \n 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // 현재 로그인된 계정과 상품을 등록한 userId 값이 일치하는 상품 필터링
                                myRef.child(getItem(position).getKey()).getRef().setValue(null);
                                goodsList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(view.getContext().getApplicationContext(), "상품이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Success to delete goods!");
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
