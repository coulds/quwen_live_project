package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;

/**
 * @author : Jun
 * time          : 2020年12月25日 11:08
 * description   : quwen_live
 */
public class HomeVideoListItemAdapter extends MyAdapter<Object> {

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

        private ViewHolder() {
            super(R.layout.layout_home_video_list_child_view);
            imv_image = (ImageView) findViewById(R.id.iv_image);
        }

        @Override
        public void onBindView(int position) {
            GlideApp.with(getContext()).load(getItem(position)).into(imv_image);
        }
    }
}
