package com.example.mp2021_2_9;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

public class TermsDialog extends Dialog { // 이용약관 다이얼로그 설정
    private Context context;
    public TermsDialog(@NonNull Context context){
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Button join_ok = (Button) findViewById(R.id.join_ok);
        join_ok.setOnClickListener((new View.OnClickListener(){ // OK버튼을 누르면 이용약관 자동 체크
            @Override
            public void onClick(View v) {
                dismiss();
            }
        }));

    }
}
