package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.glide.GlideConfig;

/**
 * @author : Jun
 * time          : 2020年12月26日 14:22
 * description   : quwen_live
 */
public class HomePublishAdapter extends MyAdapter<Object> {

    public boolean isShowAdd = true;

    public HomePublishAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private final ImageView ivItemImage;
        private final ImageView ivItemDeleted;

        private ViewHolder() {
            super(R.layout.item_publish_pic_view);
            ivItemImage = (ImageView) findViewById(R.id.iv_item_image);
            ivItemDeleted = (ImageView) findViewById(R.id.iv_item_deleted);
        }

        @Override
        public void onBindView(int position) {
            if (isShowAdd && position == 0) {
                ivItemDeleted.setVisibility(View.GONE);
            } else {
                ivItemDeleted.setVisibility(View.VISIBLE);
            }
            GlideApp.with(getContext()).load(getItem(position)).apply(GlideConfig.requestOptions).into(ivItemImage);
        }
    }

}
