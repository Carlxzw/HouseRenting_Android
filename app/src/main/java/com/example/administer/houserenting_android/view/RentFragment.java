package com.example.administer.houserenting_android.view;

import android.content.Context;
import android.content.Intent;
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
import com.example.administer.houserenting_android.model.RoomInfo;
import com.github.jdsjlzx.ItemDecoration.LuGridItemDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class RentFragment extends Fragment {

    private LRecyclerView recyclerView;//数据列表
    private List<RoomInfo> roomInfoList;//房屋数据
    private HouseListAdapter houseListAdapter;//列表适配器
    private LRecyclerViewAdapter lRecyclerViewAdapter;//刷新适配器
    private int pageSize;

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
        View view = inflater.inflate(R.layout.fragment_rent, container, false);
        recyclerView = view.findViewById(R.id.lv_rent_list);
        // Inflate the layout for this fragment
        initData();
        recyclerView.setAdapter(lRecyclerViewAdapter);
        //添加空白view，避免与搜索栏重叠
        View header = LayoutInflater.from(getContext()).inflate(R.layout.empty_title_layout,container, false);
        lRecyclerViewAdapter.addHeaderView(header);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        return view;
    }

    private void initData(){

        roomInfoList = new ArrayList<>();
        fakeData();
        houseListAdapter = new HouseListAdapter(getContext());
        houseListAdapter.setDatalist(roomInfoList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(houseListAdapter);
        lRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(),HouseDetailActivity.class));
            }
        });

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
            roomInfo.setRoomTitle("标题"+i);
            roomInfo.setRoomArea("面积"+i);
            roomInfo.setRoomType("户型"+i);
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
