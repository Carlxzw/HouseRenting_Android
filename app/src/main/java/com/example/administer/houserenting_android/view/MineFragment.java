package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.adapter.MineFunctionAdapter;
import com.example.administer.houserenting_android.model.UserInfo;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;


public class MineFragment extends Fragment {
    private RecyclerView functionList;//功能列表
    private MineFunctionAdapter mineFunctionAdapter;//功能列表适配器
    private TextView userName;//用户名
    private ImageView userIcon;//用户头像
    private TextView logoutButton;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        functionList = view.findViewById(R.id.rv_mine_function_list);
        userIcon = view.findViewById(R.id.iv_user_icon);
        userName = view.findViewById(R.id.tv_mine_user_name);
        mineFunctionAdapter = new MineFunctionAdapter(getContext());
        logoutButton = view.findViewById(R.id.tv_mine_log_out);
        functionList.setLayoutManager(new LinearLayoutManager(getContext()));
        functionList.setAdapter(mineFunctionAdapter);
        // Inflate the layout for this fragment
        initListener();
        refreshUserName();
        return view;
    }

    private void initListener(){
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().equals("请登录")){
                    startActivity(new Intent(getContext(),LoginActivity.class));
                    return;
                }

            }
        });
        //功能列表监听器
        mineFunctionAdapter.AddOnClickLientener(new MineFunctionAdapter.OnClickListener() {
            @Override
            public void onClickListener(int position) {
                if(userName.getText().equals("请登录")){
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getContext(),LoginActivity.class));
                    return;
                }
                switch (position){
                    //预约列表
                    case 0:
                        startActivity(new Intent(getContext(),AssignmentActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getContext(),MyHouseActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getContext(),MyRequestActivity.class));
                        break;
                }

            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                String userJson = sp.getString("userJson","");
                if (userJson!=""){
                    if (userName!=null){
                        userName.setText("请登录");
                        sp.edit().clear();
                        logoutButton.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void refreshUserName(){
        try {
            SharedPreferences sp = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            String userJson = sp.getString("userJson","");
            if (userJson!=""){
                UserInfo userInfo = new Gson().fromJson(userJson,UserInfo.class);
                if (userName!=null){
                    userName.setText(userInfo.getUserName()+"，欢迎您");
                    logoutButton.setVisibility(View.VISIBLE);
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUserName();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        refreshUserName();
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
