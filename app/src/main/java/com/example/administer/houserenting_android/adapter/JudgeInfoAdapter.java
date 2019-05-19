package com.example.administer.houserenting_android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administer.houserenting_android.R;
import com.example.administer.houserenting_android.model.JudgeInfo;

import java.util.ArrayList;
import java.util.List;

public class JudgeInfoAdapter extends RecyclerView.Adapter<JudgeInfoAdapter.ViewHolder> {
    private Context mContext;
    private List<JudgeInfo> judgeInfoList = new ArrayList<>();

    public JudgeInfoAdapter (Context context){
        this.mContext = context;
    }

   public void setDataList(List<JudgeInfo> judgeInfos,boolean isClear){
        if (isClear){
            judgeInfoList.clear();
        }
        this.judgeInfoList.addAll(judgeInfos);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public JudgeInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_judgeinfo_list_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JudgeInfoAdapter.ViewHolder viewHolder, int i) {
        try {
            JudgeInfo judgeInfo = judgeInfoList.get(i);
            if (judgeInfo!=null){
                viewHolder.content.setText(judgeInfo.getJudge());
                viewHolder.time.setText(judgeInfo.getJudgeDate());
                if (judgeInfo.getUserNo().getUserName().equals("")){
                    viewHolder.name.setText("用户无具体评价");
                }else {
                    viewHolder.name.setText(judgeInfo.getUserNo().getUserName());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return judgeInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name,time,content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.tv_judgeinfo_username);
            time = itemView.findViewById(R.id.tv_judgeinfo_time);
            content = itemView.findViewById(R.id.tv_judgeinfo_content);
        }
    }
}
