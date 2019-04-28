package com.example.administer.houserenting_android.view;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administer.houserenting_android.R;

public class AssignmentActivity extends AppCompatActivity {
    private TabLayout tab_assignment;
    private String[] tabName = {
            "出租","求租"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        initView();
        initListener();
    }

    private void initView(){
        tab_assignment = findViewById(R.id.tab_assignment);
        for (String s : tabName) {
            TabLayout.Tab tab =  tab_assignment.newTab();
            tab.setText(s);
            tab_assignment.addTab(tab);
        }
    }

    private void initListener(){

    }
}
