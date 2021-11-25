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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    TextView isSoldOut;
    TextView delete;

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
        isSoldOut = (TextView)view.findViewById(R.id.isSoldOut);
        delete = (TextView)view.findViewById(R.id.delete);

        itemName.setText(goodsList.get(position).getName());

        if(goodsList.get(position).getIsSoldOut()) {
            isSoldOut.setTextColor(Color.RED);    // 품절시 빨간 글씨
            Log.d(TAG, "gray->red");
        }else {
            isSoldOut.setTextColor(Color.GRAY);  // 재고가 있을시 회색 글씨
            ;
            Log.d(TAG, "red->gray");
        }

        isSoldOut.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                    // 현재 로그인된 계정과 상품을 등록한 userId 값이 일치하는 상품 필터링
                if(isSoldOut.getCurrentTextColor() == Color.RED){
                    myRef.child(getItem(position).getKey()).child("goodsIsSoldOut").getRef().setValue(false);
                    //getItem(position).setIsSoldOut(false);
                    //isSoldOut.setTextColor(R.color.gray);    // 재고가 있을땐 회색 글씨
                    Log.d(TAG+"(onClick)", "red->gray");
                }else{
                    myRef.child(getItem(position).getKey()).child("goodsIsSoldOut").getRef().setValue(true);
                    //getItem(position).setIsSoldOut(true);
                    //isSoldOut.setTextColor(R.color.red);    // 재고가 있을땐 회색 글씨
                    //notifyDataSetChanged();
                    Log.d(TAG+"(onClick)", "gray->red");

                    }
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
