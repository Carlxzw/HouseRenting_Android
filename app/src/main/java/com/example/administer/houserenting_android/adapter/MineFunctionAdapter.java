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
            R.mipmap.ic_assignment,
            R.mipmap.ic_rent,
            R.mipmap.ic_request
    };
    private  String[] functionName = {
            "预约列表","我的租房","我的求租"
    };

    private Context context;
    private OnClickListener onClickListener;

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
    public void onBindViewHolder(@NonNull MineFunctionAdapter.MineFunctionViewHolder mineFunctionViewHolder, final int i) {
        mineFunctionViewHolder.icon.setBackgroundResource(functionIcon[i]);
        mineFunctionViewHolder.name.setText(functionName[i]);
        mineFunctionViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onClickListener.onClickListener(i);
            }
        });

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

    public void AddOnClickLientener(OnClickListener onClickListener){
        if (onClickListener!=null){
            this.onClickListener = onClickListener;
        }
    }
    public  interface OnClickListener {
        void onClickListener(int position);
    }

}
