package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.adapter.HouseListAdapter;
import com.example.administer.houserenting_android.constrant.URLConstrant;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.example.administer.houserenting_android.model.UserInfo;
import com.example.administer.houserenting_android.utils.CallBackUtil;
import com.example.administer.houserenting_android.utils.OkhttpUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MyHouseActivity extends AppCompatActivity {
    private ViewGroup backButton;
    private LRecyclerView lRecyclerView;
    private List<RoomInfo> roomInfoList;//房屋数据
    private HouseListAdapter houseListAdapter;//列表适配器
    private LRecyclerViewAdapter lRecyclerViewAdapter;//刷新适配器
    private int page = 0;
    private int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_house);
        initView();
    }

    private void initView(){
        backButton = findViewById(R.id.ll_back_button);
        lRecyclerView = findViewById(R.id.lv_myhouse_list);
        roomInfoList = new ArrayList<>();
        houseListAdapter = new HouseListAdapter(getApplicationContext());
        houseListAdapter.setDatalist(roomInfoList,true);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(houseListAdapter);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //添加空白view，避免与搜索栏重叠
        View header = LayoutInflater.from(getApplicationContext()).inflate(R.layout.empty_title_layout,null, false);
        lRecyclerViewAdapter.addHeaderView(header);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(),HouseDetailActivity.class);
                intent.putExtra("roomInfo",new Gson().toJson(roomInfoList.get(position)));
                startActivity(intent);
            }
        });

        //列表刷新操作
        lRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;//刷新时重置已加载的页数
                getListData();
            }
        });

        //第一次加载
        getListData();//获取数据
    }
    /**
     * 获取数据
     */
    private void getListData(){
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
        String listUrl = URLConstrant.urlHead+"roominfoController/queryuserapp/"+userInfo.getUserNo()+"?start="+page+"&num="+pageSize;//请求地址
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
                    roomInfoList = new Gson().fromJson(result,new TypeToken<List<RoomInfo>>(){}.getType());//将获取的json转换为实体集合
                    Toast.makeText(getApplicationContext(),"刷新成功",Toast.LENGTH_SHORT).show();//刷新完成提示
                    houseListAdapter.setDatalist(roomInfoList,true);//设置适配器的数据
                    lRecyclerView.refreshComplete(pageSize);//刷新完成
                    lRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
                    page+=1;//增加页数
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"刷新失败",Toast.LENGTH_SHORT).show();//错误提示
                    e.printStackTrace();
                }


            }
        });


    }
}
