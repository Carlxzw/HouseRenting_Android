package com.example.administer.houserenting_android.view;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.adapter.AppointmentListAdapter;
import com.example.administer.houserenting_android.model.AppointmentInfo;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.example.administer.houserenting_android.model.UserInfo;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AssignmentActivity extends AppCompatActivity {
    private TabLayout tab_assignment;//分类标签栏
    //标签名
    private String[] tabName = {
            "出租","求租"
    };
    private LRecyclerView lRecyclerView;//预约列表
    private List<AppointmentInfo> appointmentInfos = new ArrayList<>();//预约列表数据
    private AppointmentListAdapter appointmentListAdapter ;//数据列表适配器
    private LRecyclerViewAdapter lRecyclerViewAdapter;//刷新列表适配器
    private ViewGroup ll_back_button;//退出按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        fakeData();
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
        //添加空白view，避免与搜索栏重叠
        View header = LayoutInflater.from(this).inflate(R.layout.empty_title_layout,null, false);
        lRecyclerViewAdapter.addHeaderView(header);
        lRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        lRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        appointmentListAdapter = new AppointmentListAdapter(getApplicationContext());
        appointmentListAdapter.setDatalist(appointmentInfos,false);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(appointmentListAdapter);
        lRecyclerView.setAdapter(lRecyclerViewAdapter);

    }

    //初始化监听器
    private void initListener(){
        tab_assignment.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0://选中出租列表
                        appointmentListAdapter.setDatalist(appointmentInfos,false);
                        break;
                    case 1://选中求租列表
                        appointmentListAdapter.setDatalist(appointmentInfos,false);
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
    }


    private void fakeData(){
        for (int i = 0;i<10;i++){
            AppointmentInfo appointmentInfo = new AppointmentInfo();
            appointmentInfo.setAppointmentDate("2018-09-28 15：00：00");
            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setRoomAddress("地址");
            appointmentInfo.setRoomNo(roomInfo);
            appointmentInfo.setAppointmentState("待审核");
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName("预约人"+i);
            userInfo.setPhone("123455677823");
            appointmentInfo.setAppointmenter(userInfo);
            appointmentInfos.add(appointmentInfo);
        }
    }
}
