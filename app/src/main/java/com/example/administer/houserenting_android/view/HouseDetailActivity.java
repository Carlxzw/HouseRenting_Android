package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.constrant.URLConstrant;
import com.example.administer.houserenting_android.model.HouseDetail;
import com.example.administer.houserenting_android.model.RoomDevice;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.example.administer.houserenting_android.model.UserInfo;
import com.example.administer.houserenting_android.utils.CallBackUtil;
import com.example.administer.houserenting_android.utils.OkhttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class HouseDetailActivity extends AppCompatActivity {
    private ViewGroup ll_back_button;//返回按钮
    private ImageView cover;//封面
    private RoomInfo roomInfo;//传递的实体
    private TextView title,price,type,area;//简要信息
    private ImageView bed,washer,ac,fridge,toilet,cook,tv,waterheating,wardrobe,heating,sofa,intentnet;//房屋配置
    private  int checkColor   ;//设备选中的颜色
    private  int uncheckColor ;//设备选中的颜色
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        roomInfo = new Gson().fromJson(getIntent().getStringExtra("roomInfo"),RoomInfo.class);//将传递的字符串转换为实体
        initView();
        initLinstener();
        initData();
        getDetailData();
    }

    private void  initView(){
        ll_back_button = findViewById(R.id.ll_back_button);
        title = findViewById(R.id.tv_house_detail_name);
        price = findViewById(R.id.tv_house_detail_price);
        type = findViewById(R.id.tv_house_detail_type);
        area = findViewById(R.id.tv_house_detail_area);

        checkColor   = getApplicationContext().getResources().getColor(R.color.colorAccent);//设备选中的颜色
        uncheckColor = getApplicationContext().getResources().getColor(R.color.grey);//设备选中的颜色

        bed = findViewById(R.id.iv_house_device_bed);
        washer = findViewById(R.id.iv_house_device_washer);
        ac  = findViewById(R.id.iv_house_device_ac);
        fridge = findViewById(R.id.iv_house_device_fridge);
        toilet = findViewById(R.id.iv_house_device_totle);
        cook = findViewById(R.id.iv_house_device_cook);
        tv = findViewById(R.id.iv_house_device_tv);
        waterheating = findViewById(R.id.iv_house_device_waterheating);
        wardrobe = findViewById(R.id.iv_house_device_wardrobe);
        heating = findViewById(R.id.iv_house_device_heating);
        intentnet = findViewById(R.id.iv_house_device_network);
        sofa = findViewById(R.id.iv_house_device_sofa);


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

    /**
     * 获取数据
     */
    private void getDetailData(){
        UserInfo userInfo = null;
        try {
            SharedPreferences sp = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            String userJson = sp.getString("userJson","");
            if (userJson!=""){
                userInfo = new Gson().fromJson(userJson,UserInfo.class);

            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (userInfo ==null){
            Toast.makeText(getApplicationContext(),"刷新失败",Toast.LENGTH_SHORT).show();//错误提示
            return;
        }
        String listUrl = URLConstrant.urlHead+"roominfoController/queryroominfo/"+roomInfo.getRoomNo();//请求地址
        OkhttpUtil.okHttpGet(listUrl, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                //请求失败
                e.printStackTrace();
            }
            @Override
            public void onResponse(String response) {
                //请求成功
                try {
                    JSONObject jb = new JSONObject(response);//数据转换为jsonObject
                    String result = jb.getString("data");//获取返回的数据内容
                    HouseDetail houseDetail = new Gson().fromJson(result,HouseDetail.class);
                    refreshRoomDevice(houseDetail.getDevice());
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"刷新失败",Toast.LENGTH_SHORT).show();//错误提示
                    e.printStackTrace();
                }


            }
        });
    }

    /**
     * 刷新房屋设备
     * @param roomDevice
     */
    private void refreshRoomDevice(RoomDevice roomDevice){
        try {
            bed.setColorFilter(roomDevice.getBed().equals("0")?checkColor:uncheckColor);
            washer.setColorFilter(roomDevice.getWasher().equals("0")?checkColor:uncheckColor);
            ac.setColorFilter(roomDevice.getConditioning().equals("0")?checkColor:uncheckColor);
            fridge.setColorFilter(roomDevice.getFridge().equals("0")?checkColor:uncheckColor);
            toilet.setColorFilter(roomDevice.getToilte().equals("0")?checkColor:uncheckColor);
            cook.setColorFilter(roomDevice.getCook().equals("0")?checkColor:uncheckColor);
            tv.setColorFilter(roomDevice.getTv().equals("0")?checkColor:uncheckColor);
            waterheating.setColorFilter(roomDevice.getWaterheater().equals("0")?checkColor:uncheckColor);
            wardrobe.setColorFilter(roomDevice.getWardrobe().equals("0")?checkColor:uncheckColor);
            heating.setColorFilter(roomDevice.getCentralheating().equals("0")?checkColor:uncheckColor);
            intentnet.setColorFilter(roomDevice.getIntelnet().equals("0")?checkColor:uncheckColor);
            sofa.setColorFilter(roomDevice.getSofa().equals("0")?checkColor:uncheckColor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
