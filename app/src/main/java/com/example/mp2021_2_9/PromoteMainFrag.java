package com.example.mp2021_2_9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import me.relex.circleindicator.CircleIndicator3;

public class PromoteMainFrag extends Fragment {
    private ViewGroup v;
    private int page_num = 4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        v = (ViewGroup) inflater.inflate(R.layout.promotion_main_screen, container, false);
        setInit();
        return v;
    }

    private void setInit(){
        ViewPager2 viewPageSetUp = (ViewPager2) v.findViewById(R.id.viewpager);

        ImageSliderAdapter setUpPagerAdapter = new ImageSliderAdapter(getActivity(), page_num);
        viewPageSetUp.setAdapter(setUpPagerAdapter);

        CircleIndicator3 indicator = (CircleIndicator3) v.findViewById(R.id.indicator);
        indicator.setViewPager(viewPageSetUp);
        indicator.createIndicators(page_num, 0);

        viewPageSetUp.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPageSetUp.setOffscreenPageLimit(page_num);
        viewPageSetUp.setCurrentItem(1000);

        viewPageSetUp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if(positionOffsetPixels == 0){
                    viewPageSetUp.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position){
                super.onPageSelected(position);
                indicator.animatePageSelected(position%page_num);
            }
        });

        final float pageMargin = (float) getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = (float) getResources().getDimensionPixelOffset(R.dimen.offset);
        viewPageSetUp.setPageTransformer(new ViewPager2.PageTransformer(){
            @Override
            public void transformPage(@NonNull View page, float position){
                float myOffset = (float) (position * -(2*pageOffset+pageMargin));
                if(viewPageSetUp.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL){
                    if(ViewCompat.getLayoutDirection(viewPageSetUp) == ViewCompat.LAYOUT_DIRECTION_RTL){
                        page.setTranslationX(-myOffset);
                    }else{
                        page.setTranslationX(myOffset);
                    }
                }else{
                    page.setTranslationY(myOffset);
                }
            }
        });
    }
}
