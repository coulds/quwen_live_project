package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.glide.GlideConfig;
import com.hsjskj.quwen.http.response.HomeVideoListBean;

/**
 * @author : Jun
 * time          : 2020年12月25日 11:08
 * description   : quwen_live
 */
public class HomeVideoListItemAdapter extends MyAdapter<HomeVideoListBean.DataBean> {

    public HomeVideoListItemAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HomeVideoListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeVideoListItemAdapter.ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private ImageView imv_image;
        private TextView tv_item_title;

        private ViewHolder() {
            super(R.layout.layout_home_video_list_child_view);
            imv_image = (ImageView) findViewById(R.id.iv_image);
            tv_item_title = (TextView) findViewById(R.id.tv_item_title);
        }

        @Override
        public void onBindView(int position) {
            tv_item_title.setText("" + getItem(position).title);
            GlideApp.with(getContext()).load(getItem(position).cover).apply(GlideConfig.requestOptions).into(imv_image);
        }
    }
}
