package com.example.administer.houserenting_android.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.administer.houserenting_android.R;

public class AddHouseActivity extends AppCompatActivity {
    private ViewGroup ll_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);
        initView();
        initLinstener();
    }

    private void initView(){
        ll_back_button = findViewById(R.id.ll_back_button);
    }

    private  void initLinstener(){
        ll_back_button.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_back_button:
                    finish();
                    break;
            }
        }
    };
}
