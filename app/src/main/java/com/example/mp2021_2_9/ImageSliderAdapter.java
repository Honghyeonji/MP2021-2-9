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

        if(index == 0) return new ViewPagerImages.ViewPageImage1();
        else if(index == 1) return new ViewPagerImages.ViewPageImage2();
        else if(index == 2) return new ViewPagerImages.ViewPageImage3();
        else return new ViewPagerImages.ViewPageImage4();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position){
        return position % count;
    }
}
