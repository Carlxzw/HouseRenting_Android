package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.adapter.HouseListAdapter;
import com.example.administer.houserenting_android.adapter.RequestListAdapter;
import com.example.administer.houserenting_android.constrant.URLConstrant;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.example.administer.houserenting_android.utils.CallBackUtil;
import com.example.administer.houserenting_android.utils.OkhttpUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class SearchActivity extends AppCompatActivity {
    private EditText keywordInput ;//输入关键字
    private ImageView backButton;//返回按钮
    private ViewGroup searchButton;//搜索按钮
    private LRecyclerView rentList;//出租结果列表
    private LRecyclerView questList;//求租结果列表
    private List<RoomInfo> roomInfoList = new ArrayList<>();//数据列表
    private HouseListAdapter houseListAdapter;//列表适配器
    private LRecyclerViewAdapter rentRecyclerViewAdapter;//刷新适配器
    private LRecyclerViewAdapter rquestlRecyclerViewAdapter;//刷新适配器
    private RequestListAdapter requestListAdapter;//列表适配器
    private int page = 0;
    private int pageSize = 20;
    private int roomState = 0;//搜索类型
    private TabLayout tabLayout ;
    //分类标签
    private String[] tabName = {
            "出租","求租"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initListener();

    }

    private void initView(){
        keywordInput = findViewById(R.id.et_serach_input);
        tabLayout = findViewById(R.id.tl_search_tab);
        for (String s : tabName) {
            TabLayout.Tab tab =  tabLayout.newTab();
            tab.setText(s);
            tabLayout.addTab(tab);
        }
        backButton = findViewById(R.id.iv_search_back_button);
        searchButton = findViewById(R.id.ll_search_button);
        //出租结果
        rentList = findViewById(R.id.lv_search_rent_list);
        rentList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        houseListAdapter = new HouseListAdapter(getApplicationContext());
        houseListAdapter.setDatalist(roomInfoList,true);
        rentRecyclerViewAdapter = new LRecyclerViewAdapter(houseListAdapter);
        rentList.setPullRefreshEnabled(false);
        rentList.setAdapter(rentRecyclerViewAdapter);
        //求租
        questList = findViewById(R.id.lv_search_request_list);
        requestListAdapter = new RequestListAdapter(getApplicationContext());
        requestListAdapter.setDatalist(roomInfoList,true);
        rquestlRecyclerViewAdapter = new LRecyclerViewAdapter(requestListAdapter);
        questList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        questList.setPullRefreshEnabled(false);
        questList.setAdapter(rquestlRecyclerViewAdapter);

        rentList.setVisibility(View.VISIBLE);
        questList.setVisibility(View.GONE);

    }

    private void initListener(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (keywordInput.getText().toString().equals("")){
                    //非空判断
                    Toast.makeText(getApplicationContext(),"请输入关键字",Toast.LENGTH_SHORT).show();
                }else{
                    getListData();
                }
            }
        });

        //根据选择的标签切换搜索类型
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0://选中出租列表
                        roomState = 0;
                        page = 0;
                        roomInfoList.clear();
                        rentList.setVisibility(View.VISIBLE);
                        questList.setVisibility(View.GONE);
                        break;
                    case 1://选中求租列表
                        roomState = 100;
                        page = 0;
                        roomInfoList.clear();
                        rentList.setVisibility(View.GONE);
                        questList.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rentRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(),HouseDetailActivity.class);
                intent.putExtra("roomInfo",new Gson().toJson(roomInfoList.get(position)));
                startActivity(intent);
            }
        });
    }

    /**
     * 获取数据
     */
    private void getListData(){
        String keyword = keywordInput.getText().toString();
        String listUrl = URLConstrant.urlHead+"roominfoController/search/"+keyword+"/"+roomState+"?start="+page+"&num="+pageSize;//请求地址
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
                    if(roomInfoList.size()==0){
                        Toast.makeText(getApplicationContext(),"查询无结果",Toast.LENGTH_SHORT).show();//错误提示
                        return;
                    }
                    if (roomState==100){
                        requestListAdapter.setDatalist(roomInfoList,true);//设置适配器的数据
                        questList.refreshComplete(pageSize);
                        rquestlRecyclerViewAdapter.notifyDataSetChanged();
                    }else {
                        houseListAdapter.setDatalist(roomInfoList,true);//设置适配器的数据
                        rentList.refreshComplete(pageSize);
                        rentRecyclerViewAdapter.notifyDataSetChanged();
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(keywordInput.getWindowToken(), 0) ;
//                    page+=1;//增加页数
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"查询失败",Toast.LENGTH_SHORT).show();//错误提示
                    e.printStackTrace();
                }


            }
        });


    }
}
