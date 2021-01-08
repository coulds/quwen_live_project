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
import com.hsjskj.quwen.upload.UploadBean;

/**
 * @author : Jun
 * time          : 2020年12月26日 14:22
 * description   : quwen_live
 */
public class HomePublishAdapter extends MyAdapter<UploadBean> {

    public boolean isShowAdd = true;
    private static final int ADAPTER_TYPE_1 = 1;
    private static final int ADAPTER_TYPE_2 = 2;

    public HomePublishAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAdd && position == 0) {
            return ADAPTER_TYPE_1;
        } else {
            return ADAPTER_TYPE_2;
        }
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ADAPTER_TYPE_1) {
            return new ViewHolderAdd();
        } else {
            return new ViewHolder();
        }
    }

    private final class ViewHolderAdd extends MyAdapter.ViewHolder {

        private final ImageView ivItemImage;
        private final ImageView ivItemDeleted;

        private ViewHolderAdd() {
            super(R.layout.item_publish_pic_view);
            ivItemImage = (ImageView) findViewById(R.id.iv_item_image);
            ivItemDeleted = (ImageView) findViewById(R.id.iv_item_deleted);
        }

        @Override
        public void onBindView(int position) {
            ivItemDeleted.setVisibility(View.GONE);
            GlideApp.with(getContext()).load(R.drawable.publish_defalut_icon).into(ivItemImage);
        }
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
            ivItemDeleted.setVisibility(View.VISIBLE);
            GlideApp.with(getContext()).load(getItem(position).getLocalPath()).apply(GlideConfig.requestOptions).into(ivItemImage);
        }
    }

}
