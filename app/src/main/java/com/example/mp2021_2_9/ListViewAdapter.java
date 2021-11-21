package com.example.mp2021_2_9;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ListItem> item;

    // DataBase
    SharedPreferences pref = mContext.getSharedPreferences("current_info", 0);
    String loginID = pref.getString("ID", "");  // 데이터 베이스에서 검색시 필요
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public ListViewAdapter(Context context, ArrayList<ListItem> data) {
        mContext = context;
        item = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ListItem getItem(int position) {
        return item.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.layout_listview, null);

        TextView itemName = (TextView)view.findViewById(R.id.item_name);
        TextView isSoldOut = (TextView)view.findViewById(R.id.isSoldOut);
        TextView delete = (TextView)view.findViewById(R.id.delete);

        itemName.setText(item.get(position).getName());

        if(item.get(position).getIsSoldOut())
            isSoldOut.setTextColor(ContextCompat.getColor(this.mContext, R.color.gray));    // 재고가 있을땐 회색 글씨
        else
            isSoldOut.setTextColor(ContextCompat.getColor(this.mContext, R.color.red)); // 품절시 빨간 글씨

        return view;
    }

    public void deleteItem(int pos){
        ListItem item = this.getItem(pos);
        this.item.remove(item);
    }

}
