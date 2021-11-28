package com.example.mp2021_2_9;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

class LoadingDialog  {
    private final Activity activity;
    private AlertDialog dialog;

    LoadingDialog(Activity myActivity){
        activity = myActivity;
    }

    void startLoadingDialog(String txt){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_progress, null);
        // 이동하는 페이지별로 문구는 custom 할 수 있도록 입력값을 받는다.
        TextView message = view.findViewById(R.id.progress_message);
        message.setText(txt);
        builder.setView(view);
        builder.setCancelable(true);    // 로딩 창 밖의 영역 클릭되어도 나가지지 않도록 설정

        dialog = builder.create();
        dialog.show();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 배경 투명하게(로고와 글씨만 보이도록)
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finishDialog();
            }
        },1000);
    }


    void finishDialog(){
        dialog.cancel();
    }
}