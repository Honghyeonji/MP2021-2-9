package com.example.mp2021_2_9;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;

// 부스메인페이지에 있는 viewPager를 구현하는 어댑터
public class ImageSliderAdapter extends FragmentStateAdapter {
    private int count;

    public ImageSliderAdapter(FragmentActivity fa, int count){
        super(fa);
        this.count = count;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        // 포지션에 맞는 이미지 프래그먼트 생성
        if(index == 0) return new ViewPagerImages.ViewPageImage1();
        else if(index == 1) return new ViewPagerImages.ViewPageImage2();
        else if(index == 2) return new ViewPagerImages.ViewPageImage3();
        else return new ViewPagerImages.ViewPageImage4();
    }

    // viewPager의 이미지들을 넘길 수 있는 최대 횟수
    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position){
        return position % count;
    }
}
