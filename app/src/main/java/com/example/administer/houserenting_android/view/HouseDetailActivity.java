package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.adapter.JudgeInfoAdapter;
import com.example.administer.houserenting_android.constrant.URLConstrant;
import com.example.administer.houserenting_android.model.AppointmentInfo;
import com.example.administer.houserenting_android.model.HouseDetail;
import com.example.administer.houserenting_android.model.JudgeInfo;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class HouseDetailActivity extends AppCompatActivity {
    private ViewGroup ll_back_button;//返回按钮
    private ImageView cover,image;//封面
    private RoomInfo roomInfo;//传递的实体
    private HouseDetail houseDetail;
    private TextView title,price,type,area,info;//简要信息
    private ImageView bed,washer,ac,fridge,toilet,cook,tv,waterheating,wardrobe,heating,sofa,intentnet;//房屋配置
    private  int checkColor   ;//设备选中的颜色
    private  int uncheckColor ;//设备选中的颜色
    private ViewGroup appointBtn;
    private List<JudgeInfo> judgeInfoList;//评价数据列表
    private RecyclerView judgeList;//评价列表
    private JudgeInfoAdapter judgeInfoAdapter;//评价列表适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_detail);
        roomInfo = new Gson().fromJson(getIntent().getStringExtra("roomInfo"),RoomInfo.class);//将传递的字符串转换为实体
        initView();
        initLinstener();
        initData();
        getDetailData();
        getJudgeList();
    }

    private void  initView(){
        ll_back_button = findViewById(R.id.ll_back_button);
        title = findViewById(R.id.tv_house_detail_name);
        price = findViewById(R.id.tv_house_detail_price);
        type = findViewById(R.id.tv_house_detail_type);
        area = findViewById(R.id.tv_house_detail_area);
        cover = findViewById(R.id.iv_house_detail_cover);
        image = findViewById(R.id.iv_house_detail_image);
        info = findViewById(R.id.tv_house_detail_info);
        appointBtn = findViewById(R.id.ll_apointment_btn);
        judgeList = findViewById(R.id.rv_house_detail_evaluate);
        judgeInfoAdapter = new JudgeInfoAdapter(getApplicationContext());
        judgeList.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        judgeList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        judgeList.setAdapter(judgeInfoAdapter);


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
        appointBtn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_back_button:
                    finish();
                    break;
                case R.id.ll_apointment_btn:
                    addNewAppointment();
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
            info.setText(roomInfo.getRoomAddress());
        }
    }

    /**
     * 获取数据
     */
    private void getDetailData(){

        String listUrl = URLConstrant.urlHead+"roominfoController/queryroominfo/"+roomInfo.getRoomNo();//请求地址
        Log.e("houseDetail:",listUrl);
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
                    houseDetail  = new Gson().fromJson(result,HouseDetail.class);
                    refreshRoomDevice(houseDetail.getDevice());
                    if (houseDetail.getPicture().size()>0&&houseDetail.getPicture().get(0)!=null){
                        if (!houseDetail.getPicture().get(0).getPicture().equals("")){
                            //封面加载
                            String coverUrl = URLConstrant.url+"files/"+houseDetail.getPicture().get(0).getPicture();
                            Glide.with(getApplicationContext())
                                    .load(coverUrl)
                                    .into(cover);
                            Glide.with(getApplicationContext())
                                    .load(coverUrl)
                                    .into(image);
                        }
                    }

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

    /**
     * 生成roomNo
     * @return
     */
    public String getNo() {
        String chars = "abcd";
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");//�������ڸ�ʽ
        String date = df.format(new Date());
        char num =chars.charAt((int)(Math.random() * 3));
        String no = date+num;
        return no;
    }

    /**
     * 生成预约信息
     * @return
     */
    private AppointmentInfo getAppointment(){
        UserInfo userInfo = null;
        SharedPreferences sp = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userJson = sp.getString("userJson","");
        if (userJson!=""){
            userInfo = new Gson().fromJson(userJson,UserInfo.class);
        }

        if (userInfo ==null){
            Toast.makeText(getApplicationContext(),"请登录",Toast.LENGTH_SHORT).show();//错误提示
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            return null;
        }

        if (houseDetail == null){
            Toast.makeText(getApplicationContext(),"发生未知错误",Toast.LENGTH_SHORT).show();//错误提示
            return null;
        }

        AppointmentInfo appointmentInfo = new AppointmentInfo();
        appointmentInfo.setAppointmentState("-1");
        appointmentInfo.setAppointmenter(userInfo);
        appointmentInfo.setRoomNo(houseDetail.getRoom());
        appointmentInfo.setSaler(houseDetail.getRoom().getUserNo());
        appointmentInfo.setAppointmentNo(getNo());
        if (houseDetail.getRoom().getUserNo().getUserNo().equals(userInfo.getUserNo())){
            Toast.makeText(getApplicationContext(),"你无法预定自己的房源",Toast.LENGTH_SHORT).show();
            return null;
        }else {
            return  appointmentInfo;
        }

    }

    /**
     * 添加新的预约信息
     */
    private void addNewAppointment(){
        AppointmentInfo appointmentInfo = getAppointment();
        String appointmentinfoJson = new Gson().toJson(appointmentInfo);
        if (appointmentInfo!=null){
            String listUrl = URLConstrant.urlHead+"appointmentinfoController/insertAppontInfoFromJson?appointmentinfoJson="+appointmentinfoJson;//请求地址
            OkhttpUtil.okHttpGet(listUrl, new CallBackUtil.CallBackString() {
                @Override
                public void onFailure(Call call, Exception e) {
                    Toast.makeText(getApplicationContext(),"预约失败",Toast.LENGTH_SHORT).show();//错误提示
                    //请求失败
                    e.printStackTrace();
                }
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jb = new JSONObject(response);//数据转换为jsonObject
                        String code = jb.getString("status");
                        //登录状态判断
                        switch (code){
                            case "500":
                                Toast.makeText(getApplicationContext(),"预约失败",Toast.LENGTH_SHORT).show();
                                break;
                            case "200":
                                AlertDialog.Builder builder = new AlertDialog.Builder(HouseDetailActivity.this);
                                builder.setMessage("预约成功").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                                builder.create().show();
//                                Toast.makeText(getApplicationContext(),"预约成功",Toast.LENGTH_SHORT).show();
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            });
        }

    }

    /**
     * 获取评价数据数据
     */
    private void getJudgeList(){

        String listUrl = URLConstrant.urlHead+"judgeinfoCotroller/queryroomJudgeList/"+roomInfo.getRoomNo()+"?start=0&num=20";//请求地址
        Log.e("houseDetail:",listUrl);
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
                   List<JudgeInfo> judgeInfoList = new Gson().fromJson(result,new TypeToken<List<JudgeInfo>>(){}.getType());
                   if (judgeInfoAdapter!=null){
                       judgeInfoAdapter.setDataList(judgeInfoList,true);
                   }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"刷新失败",Toast.LENGTH_SHORT).show();//错误提示
                    e.printStackTrace();
                }


            }
        });
    }
}
