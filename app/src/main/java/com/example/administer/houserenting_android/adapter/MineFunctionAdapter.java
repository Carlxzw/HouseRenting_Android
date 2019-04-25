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

public class MineFunctionAdapter extends RecyclerView.Adapter<MineFunctionAdapter.MineFunctionViewHolder> {
    private int[] functionIcon = {
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_home_black_24dp,
            R.drawable.ic_home_black_24dp
    };
    private  String[] functionName = {
            "预约列表","我的租房","我的租房"
    };

    private Context context;

    public MineFunctionAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public MineFunctionAdapter.MineFunctionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_mine_fun_list, viewGroup, false);
        return new MineFunctionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MineFunctionAdapter.MineFunctionViewHolder mineFunctionViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return functionName.length;
    }
    class MineFunctionViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private ImageView icon;

        public MineFunctionViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_item_fun_list_name);
            icon = itemView.findViewById(R.id.iv_item_fun_list_icon);
        }
    }

}
