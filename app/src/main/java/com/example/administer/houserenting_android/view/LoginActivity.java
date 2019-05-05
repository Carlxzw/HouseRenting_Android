package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.constrant.URLConstrant;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.example.administer.houserenting_android.utils.CallBackUtil;
import com.example.administer.houserenting_android.utils.OkhttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {
    private ViewGroup ll_back_button;
    private TextInputEditText usernameInput,pwdInput;
    private ViewGroup loginButton;
    private TextView registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initLinstener();
    }

    private void initView(){
        ll_back_button = findViewById(R.id.ll_back_button);
        usernameInput = findViewById(R.id.tie_login_username);
        pwdInput = findViewById(R.id.tie_login_pwd);
        loginButton = findViewById(R.id.ll_login_btn);
        registerButton = findViewById(R.id.tv_login_register);

    }

    private void initLinstener(){
        ll_back_button.setOnClickListener(onClickListener);
        loginButton.setOnClickListener(onClickListener);
        registerButton.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_back_button:
                    finish();
                    break;
                case R.id.ll_login_btn:
                    login();
                    break;
                case R.id.tv_login_register:
                    startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                    break;
            }
        }
    };

    /**
     * 获取数据
     */
    private void login(){
        String loginName = usernameInput.getText().toString();
        String pwd = pwdInput.getText().toString();
        String listUrl = URLConstrant.urlHead+"userinfoController/applogin?userNo="+loginName+"&loginPassword="+pwd;//请求地址
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
                    String code = jb.getString("status");
                    //登录状态判断
                    switch (code){
                        case "500":
                            Toast.makeText(getApplicationContext(),"账号密码错误",Toast.LENGTH_SHORT).show();
                            break;
                        case "504":
                            Toast.makeText(getApplicationContext(),"登录失败，请检查用户名",Toast.LENGTH_SHORT).show();
                            break;
                        case "200":
                            //登录成功后保存用户信息
                            String result = jb.getString("data");//获取返回的数据内容
                            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                            SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putString("userJson", result);
                            editor.apply();
                            finish();
                            break;
                    }

                } catch (JSONException e) {

                }


            }
        });


    }
}
