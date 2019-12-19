package com.example.work1;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    public List<Bean.DataBean.DatasBean> bean;
    private Context context;
    private Map<Integer, Boolean> map = new HashMap<>();
    private ViewHolder holder;
    private int pos;
    private boolean onBind;
    private boolean xuan;
    private int checkedPosition = -1;
    int i;
    private int c;

    public RecAdapter(List<Bean.DataBean.DatasBean> bean, Context context) {
        this.bean = bean;
        this.context = context;
    }

    @NonNull
    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recitem, null);
        return new ViewHolder(view);
    }

    public void quan(int c) {
        this.c = c;
    }
    public void setboxed(int a) {
        if (a%2!=0){
            i=1;
        }else {
            i=0;
        }
        //   holder.box.setVisibility(View.VISIBLE);
    }
    public int getCheckedPosition() {
        return checkedPosition;
    }
    public Map<Integer, Boolean> getmapdata() {
        return map;
    }
    @Override
    public void onBindViewHolder(@NonNull RecAdapter.ViewHolder holder, final int position) {
        this.holder = holder;
        Glide.with(context).load(bean.get(position).getEnvelopePic()).into(holder.img);

        if (i==1){
            holder.box.setVisibility(View.VISIBLE);
        }else if (i==0){
            holder.box.setVisibility(View.GONE);
        }
       holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    bean.get(position).setBool(true);
                }else {
                    bean.get(position).setBool(false);
                }
           }
       });
        if (bean.get(position).bool) {
            holder.box.setChecked(true);
        } else {
            holder.box.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return bean.size();
    }

    public void shanchu() {
        List<Bean.DataBean.DatasBean> li=new ArrayList<>();
        for (Bean.DataBean.DatasBean datasBean : bean) {
            boolean bool = datasBean.isBool();
            if (bool==true){
                li.add(datasBean);
            }
        }
        bean.removeAll(li);
        notifyDataSetChanged();
    }

    public void qunaxian() {
        if (c==1){
            for (int g = 0; g < bean.size(); g++) {
                bean.get(g).setBool(true);
            }
        }else if(c==2){
            for (int g = 0; g < bean.size(); g++) {
                bean.get(g).setBool(false);
            };
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private CheckBox box;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            box=itemView.findViewById(R.id.box);
        }
    }
    private checkbox boxed;

    public void setBox(checkbox boxed) {
        this.boxed = boxed;
    }

    public interface checkbox{
        void check(CheckBox checkBox);
    }
}
