package com.example.administer.houserenting_android.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administer.houserenting_android.R;

import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

public class AddHouseActivity extends AppCompatActivity {
    private ViewGroup ll_back_button;
    private TextView title;//标题栏
    private BGASortableNinePhotoLayout choosePhoto;
    private ViewGroup addressLayout,choosePhotoLayput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);

        initView();
        initLinstener();
    }

    private void initView(){
        title =findViewById(R.id.tv_house_add_title);
        title.setText(getIntent().getStringExtra("title"));
        ll_back_button = findViewById(R.id.ll_back_button);
        choosePhoto = findViewById(R.id.bg_add_house_photo);
        choosePhotoLayput = findViewById(R.id.ll_add_house_photo);
        addressLayout = findViewById(R.id.ll_add_house_address_input);
        if (getIntent().getStringExtra("title").equals("添加求租信息")){
            choosePhotoLayput.setVisibility(View.GONE);
            addressLayout.setVisibility(View.GONE);
        }


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
