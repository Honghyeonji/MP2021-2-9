package com.example.mp2021_2_9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class PromoteMainFrag extends Fragment {
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.promotion_main_screen, container, false);

        setInit();

        return viewGroup;
    }

    private void setInit(){
        sliderViewPager = viewGroup.findViewById(R.id.sliderViewPager);
        FragmentStateAdapter SetupPagerAdapter = new FragmentStateAdapter(getActivity()) {
            @Override
            public int getItemCount() {
                return 0;
            }

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return null;
            }
        };
    }
}
