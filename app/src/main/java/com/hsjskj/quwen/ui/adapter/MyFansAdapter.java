package com.hsjskj.quwen.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.http.response.BackFeedHistoryListBean;
import com.hsjskj.quwen.http.response.FansBean;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月07日 16:45
 * description   : quwen_live
 */
public class MyFansAdapter extends RecyclerView.Adapter<MyFansAdapter.ViewHolder> {

    private List<FansBean.DataBean> fensdata;

    public MyFansAdapter(List<FansBean.DataBean> fensdata) {
        this.fensdata = fensdata;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_concern_item, parent, false);
        return new MyFansAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
