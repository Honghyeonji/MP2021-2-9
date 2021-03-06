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
            btn_isSoldOut.setTextColor(Color.RED);    // ????????? ?????? ??????
            Log.d(TAG, "gray->red");
        }else {
            btn_isSoldOut.setTextColor(Color.GRAY);  // ????????? ????????? ?????? ??????
            Log.d(TAG, "red->gray");
        }

        // ???????????? ?????????
        btn_isSoldOut.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                // ?????? ???????????? goodsIsSoldOut ??? ????????? tempValue??? ??????
                tempValue = goodsList.get(position).getIsSoldOut();

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("??????");

                if (tempValue == false) {   // ?????????????????? ???  -> true(??????)???
                    builder.setMessage("???????????? ??????????????????? \n").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            myRef.child(getItem(position).getKey()).child("goodsIsSoldOut").getRef().setValue(true);
                            Toast.makeText(view.getContext().getApplicationContext(), "???????????? ???????????????.", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // ???????????? ??????
                        }
                    })
                            .create()
                            .show();
                }else if(tempValue) {    // ??????????????? ??? -> false(????????????)??????
                    builder.setMessage("?????? ???????????? ??????????????????? \n").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            myRef.child(getItem(position).getKey()).child("goodsIsSoldOut").getRef().setValue(false);
                            Toast.makeText(view.getContext().getApplicationContext(), "?????? ?????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                        }
                    }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            // ???????????? ??????
                        }
                    })
                            .create()
                            .show();
                }

                    notifyDataSetChanged();
            }
        });

        // ?????? ?????? ????????? ?????? ?????? ?????? ??? Adapter??? ??????
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("??????").setMessage("?????? ?????? ?????? ???????????? ?????? ???????????????. \n ?????????????????????????")
                        .setPositiveButton("??????", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                // ?????? ???????????? ????????? ????????? ????????? userId ?????? ???????????? ?????? ?????????
                                myRef.child(getItem(position).getKey()).getRef().setValue(null);
                                goodsList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(view.getContext().getApplicationContext(), "????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Success to delete goods!");
                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // ???????????? ??????
                            }
                        })
                        .create()
                        .show();
            }
        });
        return view;
    }

}
