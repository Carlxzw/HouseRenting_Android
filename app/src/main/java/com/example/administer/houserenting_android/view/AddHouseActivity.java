package com.example.administer.houserenting_android.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.constrant.URLConstrant;
import com.example.administer.houserenting_android.model.RoomDevice;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.example.administer.houserenting_android.model.UserInfo;
import com.example.administer.houserenting_android.utils.CallBackUtil;
import com.example.administer.houserenting_android.utils.OkhttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class AddHouseActivity extends AppCompatActivity {
    private ViewGroup ll_back_button;
    private TextView title;//标题栏
    private ViewGroup addressLayout,choosePhotoLayput,deviceLayout,uploadButton;
    private File tempFile;//临时文件
    private Uri imageUri;
    private String TAG = "AddHouseActivity";
    private int[] deviceChosen = {0,0,0,0,0,0,0,0,0,0,0,0};//房屋设备选择情况
    private static final String PHOTO_FILE_NAME = "roomCover.jpg";
    private Button local_photo,camera_photo,cancle;
    private PopupWindow popupWindow;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    private ImageView roomCover;//房屋封面
    private ImageView bed,washer,ac,fridge,totile,cook,tv,wardrobe,waterheating,heating,intentnet,sofa;//房屋设备图片
    private  int checkColor   ;//设备选中的颜色
    private  int uncheckColor ;//设备选中的颜色
    private EditText titileInput,addressInput,areaInput,priceInput;
    private int addType = 0;//添加信息的类型，0为出租，1为求租
    private ProgressDialog progressDialog;
    private Spinner houseType,rentType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        initView();
        initLinstener();
    }

    /**
     * 视图初始化
     */
    private void initView(){
        checkColor   = getApplicationContext().getResources().getColor(R.color.colorAccent);//设备选中的颜色
        uncheckColor = getApplicationContext().getResources().getColor(R.color.grey);//设备选中的颜色

        title= findViewById(R.id.tv_house_add_title);
        title.setText(getIntent().getStringExtra("title"));

        ll_back_button = findViewById(R.id.ll_back_button);
        uploadButton = findViewById(R.id.ll_done_button);
        deviceLayout = findViewById(R.id.ll_add_house_device);

        titileInput = findViewById(R.id.et_add_house_title_input);
        addressInput = findViewById(R.id.et_add_house_address_input);
        areaInput = findViewById(R.id.et_add_house_area_input);
        priceInput = findViewById(R.id.et_add_house_rent_input);

        houseType = findViewById(R.id.sp_add_house_type_input);
        rentType = findViewById(R.id.sp_add_house_rent_type_input);

        roomCover = findViewById(R.id.iv_house_add_cover);

        choosePhotoLayput = findViewById(R.id.ll_add_house_photo);
        addressLayout = findViewById(R.id.ll_add_house_address_input);

        if (getIntent().getStringExtra("title").equals("添加求租信息")){
            addType = 1;
            choosePhotoLayput.setVisibility(View.GONE);
            addressLayout.setVisibility(View.GONE);
            deviceLayout.setVisibility(View.GONE);
        }else {
            addType = 0;
        }

        bed = findViewById(R.id.iv_house_device_bed);
        washer = findViewById(R.id.iv_house_device_washer);
        ac  = findViewById(R.id.iv_house_device_ac);
        fridge = findViewById(R.id.iv_house_device_fridge);
        totile = findViewById(R.id.iv_house_device_totle);
        cook = findViewById(R.id.iv_house_device_cook);
        tv = findViewById(R.id.iv_house_device_tv);
        waterheating = findViewById(R.id.iv_house_device_waterheating);
        wardrobe = findViewById(R.id.iv_house_device_wardrobe);
        heating = findViewById(R.id.iv_house_device_heating);
        intentnet = findViewById(R.id.iv_house_device_network);
        sofa = findViewById(R.id.iv_house_device_sofa);

    }

    /**
     * 点击事件监听
     */
    private  void initLinstener(){

        uploadButton.setOnClickListener(onClickListener);
        ll_back_button.setOnClickListener(onClickListener);
        roomCover.setOnClickListener(onClickListener);
        bed.setOnClickListener(onClickListener);
        washer.setOnClickListener(onClickListener);
        ac.setOnClickListener(onClickListener);
        fridge .setOnClickListener(onClickListener);
        totile.setOnClickListener(onClickListener);
        cook.setOnClickListener(onClickListener);
        tv .setOnClickListener(onClickListener);
        waterheating .setOnClickListener(onClickListener);
        wardrobe .setOnClickListener(onClickListener);
        heating.setOnClickListener(onClickListener);
        intentnet.setOnClickListener(onClickListener);
        sofa.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_back_button:
                    finish();
                    break;
                case R.id.iv_house_add_cover:
                    showPopupWindow();
                    break;
                case R.id.iv_house_device_bed:
                    bed.setColorFilter(deviceChosen[0]==0?checkColor:uncheckColor);
                    deviceChosen[0]= deviceChosen[0]==0?1:0;
                    break;
                case R.id.iv_house_device_washer:
                    washer.setColorFilter(deviceChosen[1]==0?checkColor:uncheckColor);
                    deviceChosen[1]= deviceChosen[1]==0?1:0;
                    break;
                case R.id.iv_house_device_ac:
                    ac.setColorFilter(deviceChosen[2]==0?checkColor:uncheckColor);
                    deviceChosen[2]= deviceChosen[2]==0?1:0;
                    break;
                case R.id.iv_house_device_fridge:
                    fridge.setColorFilter(deviceChosen[3]==0?checkColor:uncheckColor);
                    deviceChosen[3]= deviceChosen[3]==0?1:0;
                    break;
                case R.id.iv_house_device_totle:
                    totile.setColorFilter(deviceChosen[4]==0?checkColor:uncheckColor);
                    deviceChosen[4]= deviceChosen[4]==0?1:0;
                    break;
                case R.id.iv_house_device_cook:
                    cook.setColorFilter(deviceChosen[5]==0?checkColor:uncheckColor);
                    deviceChosen[5]= deviceChosen[5]==0?1:0;
                    break;
                case R.id.iv_house_device_tv:
                    tv.setColorFilter(deviceChosen[6]==0?checkColor:uncheckColor);
                    deviceChosen[6]= deviceChosen[6]==0?1:0;
                    break;
                case R.id.iv_house_device_wardrobe:
                    wardrobe.setColorFilter(deviceChosen[7]==0?checkColor:uncheckColor);
                    deviceChosen[7]= deviceChosen[7]==0?1:0;
                    break;
                case R.id.iv_house_device_waterheating:
                    waterheating.setColorFilter(deviceChosen[8]==0?checkColor:uncheckColor);
                    deviceChosen[8]= deviceChosen[8]==0?1:0;
                    break;
                case R.id.iv_house_device_heating:
                    heating.setColorFilter(deviceChosen[9]==0?checkColor:uncheckColor);
                    deviceChosen[9]= deviceChosen[9]==0?1:0;
                    break;
                case R.id.iv_house_device_network:
                    intentnet.setColorFilter(deviceChosen[10]==0?checkColor:uncheckColor);
                    deviceChosen[10]= deviceChosen[10]==0?1:0;
                    break;
                case R.id.iv_house_device_sofa:
                    sofa.setColorFilter(deviceChosen[11]==0?checkColor:uncheckColor);
                    deviceChosen[11]= deviceChosen[11]==0?1:0;
                    break;
                case R.id.ll_done_button:
                    if (isCompeleted()){
                        if(addType==0){
                            addNewRoom();
                        }else {
                            addNewRequest();
                        }

                    }else {
                        Toast.makeText(getApplicationContext(),"请填写必要信息",Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }
    };


    /**
     * 判断是否有SDCard
     * @return
     */
    private boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 返回的数据
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Uri uri = null;
        switch (requestCode) {
            case PHOTO_REQUEST_CAREMA:
                popupWindow.dismiss();

                if (data!=null){
                    uri = data.getData();
                }else {
                    uri = imageUri;
                }
                crop(uri);

                break;
            case PHOTO_REQUEST_GALLERY:
                popupWindow.dismiss();
                if (data == null) {
                    Toast.makeText(getApplicationContext(), "数据为空", Toast.LENGTH_LONG).show();
                }
                else {

                    uri = data.getData();
                    crop(uri);

                }
                break;
            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    try{
                        tempFile = new File(Environment.getExternalStorageDirectory(),PHOTO_FILE_NAME);
                        if (tempFile.exists()){
                            tempFile.delete();
                            Log.d(TAG,"删除文件");
                        }tempFile.createNewFile();
                        Log.d(TAG,"创建文件");
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Bitmap bitmap = data.getParcelableExtra("data");
                    roomCover.setImageBitmap(bitmap);
                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                        bos.flush();
                        bos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();

                }
                break;


        }
    }

    //显示选择相片的弹窗
    @AfterPermissionGranted(PHOTO_REQUEST_CAREMA)
    public void showPopupWindow(){
        View view = View.inflate(this,R.layout.popup_window_layout,null);
        local_photo = view.findViewById(R.id.btn_pop_album);
        camera_photo = view.findViewById(R.id.btn_pop_camera);
        cancle = view.findViewById(R.id.btn_pop_cancel);

        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels*1/3;
        popupWindow = new PopupWindow(view,weight,height);
//        popupWindow.setAnimationStyle(R.anim.push_up_in);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        local_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,PHOTO_REQUEST_GALLERY);
                popupWindow.dismiss();
            }
        });
        camera_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT>=23){
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(AddHouseActivity.this, Manifest.permission.CAMERA);
                    if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(AddHouseActivity.this,new String[]{Manifest.permission.CAMERA},222);
                        return;
                    }else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (hasSdcard()){
                            tempFile = new File(Environment.getExternalStorageDirectory(),PHOTO_FILE_NAME);
                            Uri uri = Uri.fromFile(tempFile);
                            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                                    "yyyy_MM_dd_HH_mm_ss");
                            String filename = timeStampFormat.format(new Date());
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE,filename);
                            imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                        }
                        startActivityForResult(intent,PHOTO_REQUEST_CAREMA);
                        popupWindow.dismiss();
                    }
                }


            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha =1.0f;
                getWindow().setAttributes(lp);
            }
        });
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.alpha =0.5f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);

    }

    /**
     * 获取新建信息
     * @return
     */
    private RoomInfo getNewRoom(){
        RoomInfo roomInfo = new RoomInfo();
        roomInfo.setRoomNo(getNo());
        roomInfo.setRoomTitle(titileInput.getText().toString());
        roomInfo.setRoomAddress(addressInput.getText().toString());
        roomInfo.setRoomArea(areaInput.getText().toString());
        roomInfo.setRoomType(houseType.getSelectedItem().toString());
        roomInfo.setRoomKind(rentType.getSelectedItem().toString());
        roomInfo.setRoomPrice(priceInput.getText().toString());
        roomInfo.setRoomBackup("");
        SharedPreferences sp = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String userJson = sp.getString("userJson","");
        UserInfo userInfo = new Gson().fromJson(userJson,UserInfo.class);
        roomInfo.setUserNo(userInfo);
//        roomInfo.setRoomCover("");
        if (addType==0){
            roomInfo.setRoomState("-1");
        }else {
            roomInfo.setRoomState("100");
        }

        return roomInfo;
    }


    private RoomDevice getRoomDevice(){
        RoomDevice roomDevice = new RoomDevice();
        roomDevice.setBed(String.valueOf(deviceChosen[0]));
        roomDevice.setWasher(String.valueOf(deviceChosen[1]));
        roomDevice.setConditioning(String.valueOf(deviceChosen[2]));
        roomDevice.setFridge(String.valueOf(deviceChosen[3]));
        roomDevice.setToilte(String.valueOf(deviceChosen[4]));
        roomDevice.setCook(String.valueOf(deviceChosen[5]));
        roomDevice.setTv(String.valueOf(deviceChosen[6]));
        roomDevice.setWardrobe(String.valueOf(deviceChosen[7]));
        roomDevice.setWaterheater(String.valueOf(deviceChosen[8]));
        roomDevice.setWaterheater(String.valueOf(deviceChosen[9]));
        roomDevice.setIntelnet(String.valueOf(deviceChosen[10]));
        roomDevice.setSofa(String.valueOf(deviceChosen[11]));
        return roomDevice;
    }

    /**
     * 生成roomNo
     * @return
     */
    public String getNo() {
        String chars = "abc";
        SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");//�������ڸ�ʽ
        String date = df.format(new Date());
        char num =chars.charAt((int)(Math.random() * 3));
        String no = date+num;
        return no;
    }

    /**
     * 是否有未添加信息
     * @return
     */
    private boolean isCompeleted(){
        if(titileInput.getText().toString().equals("")||
                areaInput.getText().toString().equals("")||
                priceInput.getText().toString().equals("")){
            return false;
        }else if (addType ==0&&addressInput.getText().toString().equals("")){
            return false;
        }
        else {
            return true;
        }

    }
    /**
     * 添加新出租信息
     */
    private void addNewRoom(){
        progressDialog = new ProgressDialog(AddHouseActivity.this);
        progressDialog.show();
        final RoomInfo room = getNewRoom();
//        if (tempFile!=null){
//           room.setRoomCover(room.getRoomNo()+".jpg");
//        }
        String roomInfo = new Gson().toJson(room);
        String roomDevice = new Gson().toJson(getRoomDevice());
        String listUrl = URLConstrant.urlHead+"roominfoController/insertroominfoFromJson?roominfojsonString="+roomInfo+"&roomdevicejsonString="+roomDevice;//请求地址
        OkhttpUtil.okHttpGet(listUrl, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                //请求失败
                Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
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
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                            break;
                        case "200":
                            if (tempFile!=null){
                                uploadPicture(room.getRoomNo());
                            }else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }
//
                            break;
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        });

    }


    private void addNewRequest(){
        progressDialog = new ProgressDialog(AddHouseActivity.this);
        progressDialog.show();
        String roomInfo = new Gson().toJson(getNewRoom());
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("roominfojsonString",roomInfo);
        paramsMap.put("roomdevicejsonString",null);
//        String listUrl = URLConstrant.urlHead+"roominfoController/insertroominfoFromJson";//请求地址
        String listUrl = URLConstrant.urlHead+"roominfoController/insertroominfoFromJson?roominfojsonString="+roomInfo;//请求地址
//        String listUrl = URLConstrant.urlHead+"roominfoController/insertroominfoFromJson";//请求地址
        OkhttpUtil.okHttpGet(listUrl,new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                //请求失败
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
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
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"登录失败，请检查用户名",Toast.LENGTH_SHORT).show();
                            break;
                        case "200":
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                            finish();
                            break;
                    }

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


            }
        });
    }

    private void uploadPicture(final String roomNo){
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                        try{
                            String listUrl = URLConstrant.urlHead+"roominfoController/uploadPortrait";//请求地址
                            okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient();
                            MultipartBody.Builder builder = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("fileToUpload",roomNo, okhttp3.RequestBody.create(okhttp3.MediaType.parse("image/*"),tempFile))
                                    .addFormDataPart("roomNo",roomNo);
                            okhttp3.RequestBody requestBody = builder.build();
                            okhttp3.Request request = new okhttp3.Request.Builder().url(listUrl).post(requestBody).build();
                            okhttp3.Call call = okHttpClient.newCall(request);
                            call.enqueue(new okhttp3.Callback() {
                                @Override
                                public void onFailure(okhttp3.Call call,final IOException e) {
                                    Log.d(TAG,e.getMessage().toString());
                                    if(progressDialog!=null){
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"上传图片失败",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                                    Log.d(TAG,response.toString());
                                    if (progressDialog!=null){
                                        progressDialog.dismiss();

                                    }
                                    Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                                    finish();

                                }
                            });
                        }catch (Exception e){
                            if (progressDialog!=null){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getApplicationContext(),"添加失败",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

            };
            new Thread(){
                public void run(){
                    new Handler(Looper.getMainLooper()).post(runnable);
                }
            }.start();

    }



}
