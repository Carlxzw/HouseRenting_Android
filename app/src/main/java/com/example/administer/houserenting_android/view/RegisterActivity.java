package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity {
    private ViewGroup backButton,registerButton;
    private TextInputEditText phoneInput,pwdInput,pwdConfirm;
    private String phone,pwd,confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }

    private void initView(){
        backButton = findViewById(R.id.ll_back_button);
        registerButton  = findViewById(R.id.ll_register_btn);
        phoneInput = findViewById(R.id.tie_register_phone);
        pwdInput = findViewById(R.id.tie_register_pwd);
        pwdConfirm = findViewById(R.id.tie_register_pwd_confirm);


    }

    private void initListener(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 phone = phoneInput.getText().toString();
                 pwd = pwdInput.getText().toString();
                 confirm = pwdConfirm.getText().toString();

                if (phone.equals("")|| pwd.equals("")|| confirm.equals("")){
                    Toast.makeText(getApplicationContext(),"输入内容为空",Toast.LENGTH_SHORT).show();
                }else if (!pwd.equals(confirm)){
                    Toast.makeText(getApplicationContext(),"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                }else {
                    register();
                }
            }
        });

    }

    /**
     * 生成userNo
     * @return
     */
    public String getNo() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");//�������ڸ�ʽ
        String date = df.format(new Date());
        char num =chars.charAt((int)(Math.random() * 26));
        String no = date+num;
        return no;
    }
    /**
     * 获取数据
     */
    private void register(){
        String loginName = getNo();
        String listUrl = URLConstrant.urlHead+"/userinfoController/regist?userNo="+loginName+"&loginPassword="+pwd+"&phone="+phone+"&userType="+"0";//请求地址
        OkhttpUtil.okHttpGet(listUrl, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                //请求失败
                Toast.makeText(getApplicationContext(),"网络错误",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }

                } catch (JSONException e) {

                }


            }
        });


    }
}

