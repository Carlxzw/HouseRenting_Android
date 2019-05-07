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
import com.example.administer.houserenting_android.model.AppointmentInfo;
import com.example.administer.houserenting_android.model.RoomInfo;

import java.util.ArrayList;
import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.HouseListViewHolder> {
    private Context context;
    private List<AppointmentInfo> mDataList;
    private int state;

    public AppointmentListAdapter(Context context){
        this.context = context;
        mDataList = new ArrayList<>();
    }


    /**
     * 插入数据
     * @param list
     * @param isRefresh 是否为刷新数据还是加载更多
     */
    public void setDatalist(List<AppointmentInfo> list,boolean isRefresh,int type){
        if (isRefresh){
            mDataList.clear();
        }
        state = type;
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清除所有数据
     */
    public void clearData(){
        mDataList.clear();
    }


    /**
     * 确定布局文件
     * @param viewGroup
     * @param i
     * @return
     */
    @NonNull
    @Override
    public AppointmentListAdapter.HouseListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_appointment_info_list, viewGroup, false);
        return new HouseListViewHolder(v);
    }

    /**
     * 为每个列表项插入数据
     * @param houseListViewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull AppointmentListAdapter.HouseListViewHolder houseListViewHolder, int i) {
        AppointmentInfo appointmentInfo  = mDataList.get(i);

        houseListViewHolder.address.setText(appointmentInfo.getRoomNo().getRoomAddress());
        houseListViewHolder.time.setText(appointmentInfo.getAppointmentDate());
        switch (state) {
            case 0:
                if (appointmentInfo.getAppointmenter()!=null){
                    houseListViewHolder.name.setText(appointmentInfo.getSaler().getUserName());
                    houseListViewHolder.phone.setText(appointmentInfo.getSaler().getPhone());
                }
                break;
            case 1:
                if (appointmentInfo.getAppointmenter()!=null){
                    houseListViewHolder.name.setText(appointmentInfo.getAppointmenter().getUserName());
                    houseListViewHolder.phone.setText(appointmentInfo.getAppointmenter().getPhone());
                }
                break;
        }
        switch (appointmentInfo.getAppointmentState()){
            case "0":
                houseListViewHolder.state.setText("预约成功");
                break;
            case "1":
                houseListViewHolder.state.setText("已拒绝");
                break;
            case "-1":
                houseListViewHolder.state.setText("已申请");
                break;

        }



    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    //初始化视图
    public class HouseListViewHolder extends RecyclerView.ViewHolder  {

        TextView name,state,time,phone,address;

        public HouseListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_appointment_name);
            state = itemView.findViewById(R.id.tv_appointment_state);
            time = itemView.findViewById(R.id.tv_appointment_time);
            phone = itemView.findViewById(R.id.tv_appointment_phone);
            address = itemView.findViewById(R.id.tv_appointment_address);
        }
    }
}
