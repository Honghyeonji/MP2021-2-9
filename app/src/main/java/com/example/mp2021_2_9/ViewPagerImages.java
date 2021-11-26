package com.example.mp2021_2_9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


// ViewPager 안에 들어가는 이미지 프래그먼트들 (이미지 클릭시 공지글(외부 앱 글)로 이동)
public class ViewPagerImages {
    public static class ViewPageImage1 extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.item_slider1, container, false);
            ImageView toPost = (ImageView) v.findViewById(R.id.main_promo1);
            toPost.setOnClickListener(new View.OnClickListener() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                @Override
                public void onClick(View v) {
                    intent.setData(Uri.parse("https://www.instagram.com/p/CWjPkGDh_2D/?utm_medium=copy_link"));
                    startActivity(intent);
                }
            });
            return v;
        }
    }

    public static class ViewPageImage2 extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.item_slider2, container, false);
            ImageView toPost = (ImageView) v.findViewById(R.id.main_promo2);
            toPost.setOnClickListener(new View.OnClickListener() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                @Override
                public void onClick(View v) {
                    intent.setData(Uri.parse("https://www.instagram.com/p/CWjPmJnhHsj/?utm_medium=copy_link"));
                    startActivity(intent);
                }
            });
            return v;
        }
    }
    public static class ViewPageImage3 extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.item_slider3, container, false);
            ImageView toPost = (ImageView) v.findViewById(R.id.main_promo3);
            toPost.setOnClickListener(new View.OnClickListener() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                @Override
                public void onClick(View v) {
                    intent.setData(Uri.parse("https://www.instagram.com/p/CWjPqSoBD1r/?utm_medium=copy_link"));
                    startActivity(intent);
                }
            });
            return v;
        }
    }
    public static class ViewPageImage4 extends Fragment{
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            ViewGroup v = (ViewGroup) inflater.inflate(R.layout.item_slider4, container, false);
            ImageView toPost = (ImageView) v.findViewById(R.id.main_promo4);
            toPost.setOnClickListener(new View.OnClickListener() {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                @Override
                public void onClick(View v) {
                    intent.setData(Uri.parse("https://www.instagram.com/p/CWjPs4ehHDS/?utm_medium=copy_link"));
                    startActivity(intent);
                }
            });
            return v;
        }
    }
}
