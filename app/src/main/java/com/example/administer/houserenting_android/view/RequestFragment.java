package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.adapter.HouseListAdapter;
import com.example.administer.houserenting_android.adapter.RequestListAdapter;
import com.example.administer.houserenting_android.model.RoomInfo;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class RequestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LRecyclerView recyclerView;//数据列表
    private List<RoomInfo> roomInfoList;//房屋数据
    private RequestListAdapter houseListAdapter;//列表适配器
    private LRecyclerViewAdapter lRecyclerViewAdapter;//刷新适配器
    private int pageSize;

//    private OnFragmentInteractionListener mListener;

    public RequestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestFragment newInstance() {
        RequestFragment fragment = new RequestFragment();
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
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView = view.findViewById(R.id.lv_request_list);
        // Inflate the layout for this fragment
        initData();
        houseListAdapter = new RequestListAdapter(getContext());
        houseListAdapter.setDatalist(roomInfoList,false);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(houseListAdapter);
        //添加空白view，避免与搜索栏重叠
        View header = LayoutInflater.from(getContext()).inflate(R.layout.empty_title_layout,container, false);
        lRecyclerViewAdapter.addHeaderView(header);

        recyclerView.setAdapter(lRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        return view;
    }


    //初始化数据
    private void initData(){
        roomInfoList = new ArrayList<>();
        fakeData();


        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.refreshComplete(pageSize);
            }
        });

    }

    private void fakeData(){
        for (int i=0;i<10;i++){
            RoomInfo roomInfo = new RoomInfo();
            roomInfo.setRoomAddress("地址"+i);
            roomInfo.setRoomTitle("求租信息"+i);
            roomInfo.setRoomArea("面积"+i);
            roomInfo.setRoomType("户型"+i);
            roomInfo.setRoomPrice("1000-2000元");
            roomInfoList.add(roomInfo);
        }
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
}
