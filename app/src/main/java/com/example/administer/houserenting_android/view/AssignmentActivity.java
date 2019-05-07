package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.adapter.AppointmentListAdapter;
import com.example.administer.houserenting_android.constrant.URLConstrant;
import com.example.administer.houserenting_android.model.AppointmentInfo;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.example.administer.houserenting_android.model.UserInfo;
import com.example.administer.houserenting_android.utils.CallBackUtil;
import com.example.administer.houserenting_android.utils.OkhttpUtil;
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

public class AssignmentActivity extends AppCompatActivity {
    private TabLayout tab_assignment;//分类标签栏
    //标签名
    private String[] tabName = {
            "出租","求租"
    };
    private int state = 0;//查询分类，0代表出租，1代表求租；
    private LRecyclerView lRecyclerView;//预约列表
    private List<AppointmentInfo> appointmentInfos = new ArrayList<>();//预约列表数据
    private AppointmentListAdapter appointmentListAdapter ;//数据列表适配器
    private LRecyclerViewAdapter lRecyclerViewAdapter;//刷新列表适配器
    private ViewGroup ll_back_button;//退出按钮
    private int page = 0;
    private int pageSize = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        initView();
        initListener();
    }

    //初始化视图
    private void initView(){

        ll_back_button = findViewById(R.id.ll_back_button);

        tab_assignment = findViewById(R.id.tab_assignment);
        for (String s : tabName) {
            TabLayout.Tab tab =  tab_assignment.newTab();
            tab.setText(s);
            tab_assignment.addTab(tab);
        }

        lRecyclerView = findViewById(R.id.lv_assignment);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        appointmentListAdapter = new AppointmentListAdapter(getApplicationContext());
        appointmentListAdapter.setDatalist(appointmentInfos,false,state);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(appointmentListAdapter);
        //添加空白view，避免与搜索栏重叠
        View header = LayoutInflater.from(this).inflate(R.layout.empty_title_layout,null, false);
        lRecyclerViewAdapter.addHeaderView(header);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);

        getListData();

    }

    //初始化监听器
    private void initListener(){
        //根据选择的标签切换搜索类型
        tab_assignment.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0://选中出租列表
                        state = 0;
                        page = 0;
                        appointmentInfos.clear();
                        getListData();
                        break;
                    case 1://选中求租列表
                        state = 1;
                        page = 0;
                        appointmentInfos.clear();
                        getListData();
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

        //返回按钮监听
        ll_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }

    /**
     * 获取数据
     */
    private void getListData(){
        SharedPreferences sp = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String userJson = sp.getString("userJson","");
        UserInfo userInfo = new Gson().fromJson(userJson,UserInfo.class);
        if (userInfo==null){
            Toast.makeText(getApplicationContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
            return;
        }
        String listUrl = URLConstrant.urlHead+"appointmentinfoController/queryAppontInfoList/"+userInfo.getUserNo()+"/100?start="+page+"&num="+pageSize+"&type="+state;//请求地址
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
                    appointmentInfos = new Gson().fromJson(result,new TypeToken<List<AppointmentInfo>>(){}.getType());//将获取的json转换为实体集合
                    appointmentListAdapter.setDatalist(appointmentInfos,true,state);//设置适配器的数据
                    lRecyclerView.refreshComplete(pageSize);//刷新完成
                    lRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
//                    page+=1;//增加页数
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"查询失败",Toast.LENGTH_SHORT).show();//错误提示
                    e.printStackTrace();
                }


            }
        });


    }


}
