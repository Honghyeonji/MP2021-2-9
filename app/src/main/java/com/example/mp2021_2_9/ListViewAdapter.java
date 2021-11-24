package com.example.mp2021_2_9;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;

    // Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("goods");

    ArrayList<ListItem> goodsList;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.layout_listview, null);

        TextView itemName = (TextView)view.findViewById(R.id.item_name);
        TextView isSoldOut = (TextView)view.findViewById(R.id.isSoldOut);
        TextView delete = (TextView)view.findViewById(R.id.delete);

        itemName.setText(goodsList.get(position).getName());

        if(goodsList.get(position).getIsSoldOut())
            isSoldOut.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray));    // 재고가 있을땐 회색 글씨
        else
            isSoldOut.setTextColor(ContextCompat.getColor(this.mContext, R.color.red));  // 품절시 빨간 글씨


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
                                Log.d("ListViewAdapter: ", "Success to delete goods!");
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
