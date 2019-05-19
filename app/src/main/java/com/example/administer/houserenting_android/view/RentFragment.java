package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.adapter.HouseListAdapter;
import com.example.administer.houserenting_android.constrant.URLConstrant;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.example.administer.houserenting_android.utils.CallBackUtil;
import com.example.administer.houserenting_android.utils.OkhttpUtil;
import com.github.jdsjlzx.ItemDecoration.LuGridItemDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.support.constraint.Constraints.TAG;

public class RentFragment extends Fragment {

    //网络请求地址

    private LRecyclerView recyclerView;//数据列表
    private List<RoomInfo> roomInfoList;//房屋数据
    private HouseListAdapter houseListAdapter;//列表适配器
    private LRecyclerViewAdapter lRecyclerViewAdapter;//刷新适配器
    private int page = 0;
    private int pageSize = 20;

    public RentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RentFragment newInstance() {
        RentFragment fragment = new RentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rent, container, false);//声明指定布局
        recyclerView = view.findViewById(R.id.lv_rent_list);
        // Inflate the layout for this fragment
        initData();
        recyclerView.setAdapter(lRecyclerViewAdapter);
        //添加空白view，避免与搜索栏重叠
        View header = LayoutInflater.from(getContext()).inflate(R.layout.empty_title_layout,container, false);
        lRecyclerViewAdapter.addHeaderView(header);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置布局，LinearLayout为纵向列表布局
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));//添加每个列表项的分割线
        return view;
    }

    //数据化初始化
    private void initData(){
        roomInfoList = new ArrayList<>();
        houseListAdapter = new HouseListAdapter(getContext());
        houseListAdapter.setDatalist(roomInfoList,true);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(houseListAdapter);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(),HouseDetailActivity.class);
                intent.putExtra("roomInfo",new Gson().toJson(roomInfoList.get(position)));
                startActivity(intent);
            }
        });

        //列表刷新操作
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;//刷新时重置已加载的页数
                getListData();
            }
        });

        //第一次加载
        getListData();//获取数据
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    /**
     * 获取数据
     */
    private void getListData(){
        String listUrl = URLConstrant.urlHead+"roominfoController/queryroominfoList?start="+page+"&num="+pageSize;//请求地址
        Log.d(TAG, "getListData: "+listUrl);
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
                    JSONObject  jb = new JSONObject(response);//数据转换为jsonObject
                    String result = jb.getString("data");//获取返回的数据内容
                    roomInfoList = new Gson().fromJson(result,new TypeToken<List<RoomInfo>>(){}.getType());//将获取的json转换为实体集合
                    Toast.makeText(getContext(),"刷新成功",Toast.LENGTH_SHORT).show();//刷新完成提示
                    houseListAdapter.setDatalist(roomInfoList,true);//设置适配器的数据
                    recyclerView.refreshComplete(pageSize);//刷新完成
                    lRecyclerViewAdapter.notifyDataSetChanged();//必须调用此方法
                    page+=1;//增加页数
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"刷新失败",Toast.LENGTH_SHORT).show();//错误提示
                    e.printStackTrace();
                }


            }
        });


    }
}
