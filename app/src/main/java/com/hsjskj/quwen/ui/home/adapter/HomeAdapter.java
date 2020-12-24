package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;

/**
 * @author : Jun
 * time          : 2020年12月24日16:56:09
 * description   : 用户信息保存
 */
public final class HomeAdapter extends MyAdapter<String> {

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private ViewHolder() {
            super(R.layout.home_item_question);
        }

        @Override
        public void onBindView(int position) {

        }
    }
}