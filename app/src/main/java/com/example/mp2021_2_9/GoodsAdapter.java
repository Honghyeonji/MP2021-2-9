package com.example.mp2021_2_9;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.ViewHolder> {
    private ArrayList<GoodsInfo_list> goodsInfo_lists;
    private Context context;            // 어댑터에서 액티비티 액션 가져올 때 필요

    GoodsAdapter(ArrayList<GoodsInfo_list> list, Context context){
        this.goodsInfo_lists = list;
        this.context = context;
    }

    /* ViewHolder 부분 */
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            this.imageView = itemView.findViewById(R.id.putImage);
        }
    }

    // 뷰와 어댑터 연결 후 뷰홀더 최초 생성
    @NonNull
    @Override
    public GoodsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.image_recycler, parent, false);
        GoodsAdapter.ViewHolder vh = new GoodsAdapter.ViewHolder(view);
        return vh;
    }

    // 각 아이템 매칭
    @Override
    public void onBindViewHolder(@NonNull GoodsAdapter.ViewHolder holder, int position){
        Glide.with(holder.itemView).load(goodsInfo_lists.get(position).getImgUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return (goodsInfo_lists != null ? goodsInfo_lists.size() : 0) ;
    }
}
