package com.example.administer.houserenting_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.model.RoomInfo;

import java.util.ArrayList;
import java.util.List;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.HouseListViewHolder> {
    private Context context;
    private List<RoomInfo> mDataList;

    public RequestListAdapter(Context context){
        this.context = context;
        mDataList = new ArrayList<>();
    }

    public void setDatalist(List<RoomInfo> list,boolean isRefresh){
        if (isRefresh){
            mDataList.clear();
        }
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void clearData(){
        mDataList.clear();
    }


    @NonNull
    @Override
    public RequestListAdapter.HouseListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_request_info_list, viewGroup, false);
        return new HouseListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestListAdapter.HouseListViewHolder houseListViewHolder, int i) {
        RoomInfo roomInfo = mDataList.get(i);
        houseListViewHolder.title.setText(roomInfo.getRoomTitle());
        houseListViewHolder.address.setText(roomInfo.getRoomAddress());
        houseListViewHolder.type.setText(roomInfo.getRoomType());
        houseListViewHolder.price.setText(roomInfo.getRoomPrice());
        houseListViewHolder.area.setText(roomInfo.getRoomArea());

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public class HouseListViewHolder extends RecyclerView.ViewHolder  {

        private TextView title,type,area,price,address;
        public HouseListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_house_title);
            type = itemView.findViewById(R.id.tv_house_type);
            area = itemView.findViewById(R.id.tv_house_area);
            price = itemView.findViewById(R.id.tv_house_price);
            address = itemView.findViewById(R.id.tv_house_address);
        }
    }
}
