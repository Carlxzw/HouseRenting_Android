package com.example.administer.houserenting_android.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.google.gson.Gson;

public class HouseDetailActivity extends AppCompatActivity {
    private ViewGroup ll_back_button;//返回按钮
    private ImageView cover;//封面
    private RoomInfo roomInfo;//传递的实体
    private TextView title,price,type,area;//简要信息
    private ImageView bed,washer,ac,fridge,toilet,cook,tv,waterheating,wardrobe,heating,network,sofa;//房屋配置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        roomInfo = new Gson().fromJson(getIntent().getStringExtra("roomInfo"),RoomInfo.class);//将传递的字符串转换为实体
        initView();
        initLinstener();
        initData();
    }

    private void  initView(){
        ll_back_button = findViewById(R.id.ll_back_button);
        title = findViewById(R.id.tv_house_detail_name);
        price = findViewById(R.id.tv_house_detail_price);
        type = findViewById(R.id.tv_house_detail_type);
        area = findViewById(R.id.tv_house_detail_area);
    }

    private void initLinstener(){
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

    /**
     * 加载数据
     */
    private void initData(){
        if (roomInfo!=null){
            title.setText(roomInfo.getRoomTitle());
            price.setText("价格："+roomInfo.getRoomPrice()+"元");
            type.setText("户型："+roomInfo.getRoomType());
            area.setText("面积："+roomInfo.getRoomArea()+"平方");

        }
    }
}
