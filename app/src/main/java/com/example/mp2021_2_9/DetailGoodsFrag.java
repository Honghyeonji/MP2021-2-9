package com.example.mp2021_2_9;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

// 굿즈 상세페이지
public class DetailGoodsFrag extends Fragment {
    ViewGroup v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = (ViewGroup) inflater.inflate(R.layout.activity_goodsdetailed, container, false);
        Bundle bundle = getArguments();
        GoodsInfo_list goodsinfo = (GoodsInfo_list) bundle.getSerializable("GoodsInfo_list");
        Log.w("test", "id " + goodsinfo.getGoodsName() + ", " +goodsinfo.getUserId());

        TextView goods_name = v.findViewById(R.id.goods_name);
        TextView goods_price = v.findViewById(R.id.goods_price);
        TextView goods_location = v.findViewById(R.id.goods_location);

        goods_name.setText(goodsinfo.getGoodsName());
        goods_price.setText(goodsinfo.getGoodsPrice()+"원");
        goods_location.setText(goodsinfo.getGoodsLocation());

        return v;
    }
}
