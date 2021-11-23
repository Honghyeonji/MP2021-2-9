package com.example.mp2021_2_9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter{

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<ListItem> item;

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

        //isSoldOut.setOnClickListener(new View.OnClickListener(){ isSoldOutClicked(isSoldOut)});
        //delete.setOnClickListener(v -> deleteClicked(delete));

        return view;
    }


    public View isSoldOutClicked(View v){
        return (View)v.getParent(); // 해당 버튼의 위젯 반환
    }

    public View deleteClicked(View v){
        return (View)v.getParent(); // 해당 버튼의 위젯 반환
    }

    public void deleteItem(int pos){
        ListItem item = this.getItem(pos);
        this.item.remove(item);
    }


}
