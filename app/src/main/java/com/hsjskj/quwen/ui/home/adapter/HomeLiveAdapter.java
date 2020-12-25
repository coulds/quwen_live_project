package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;

/**
 * @author : Jun
 * time          : 2020年12月25日 11:08
 * description   : quwen_live
 */
public class HomeLiveAdapter extends MyAdapter<Object> {

    public HomeLiveAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public HomeLiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeLiveAdapter.ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private ImageView imv_image;
        private ImageView iv_voice;
        private ImageView tv_type;
        private TextView tv_id;
        private TextView tv_time_price;
        private TextView tv_content;

        private ViewHolder() {
            super(R.layout.layout_home_live_child_view);
            imv_image = (ImageView) findViewById(R.id.iv_image);
            iv_voice = (ImageView) findViewById(R.id.iv_voice);
            tv_type = (ImageView) findViewById(R.id.iv_voice);
            tv_id = (TextView) findViewById(R.id.tv_id);
            tv_time_price = (TextView) findViewById(R.id.tv_time_price);
            tv_content = (TextView) findViewById(R.id.tv_time_price);
        }

        @Override
        public void onBindView(int position) {
//            GlideApp.with(getContext()).load(getItem(position)).into(imv_image);
        }
    }
}
