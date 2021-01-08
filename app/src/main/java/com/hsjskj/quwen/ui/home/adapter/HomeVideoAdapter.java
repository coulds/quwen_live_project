package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.request.RequestOptions;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.response.HomeVideoListBean;

/**
 * @author : Jun
 * time          : 2020年12月25日 11:08
 * description   : quwen_live
 */
public class HomeVideoAdapter extends MyAdapter<HomeVideoListBean.DataBean> {
    public RequestOptions requestOptions;

    public HomeVideoAdapter(Context context) {
        super(context);
        requestOptions = new RequestOptions()
                .placeholder(R.drawable.defalut_home_video)
                .error(R.drawable.defalut_home_video);
    }

    @NonNull
    @Override
    public HomeVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeVideoAdapter.ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private ImageView imv_image;

        private ViewHolder() {
            super(R.layout.layout_home_video_child_view);
            imv_image = (ImageView) findViewById(R.id.iv_image);
        }

        @Override
        public void onBindView(int position) {
            GlideApp.with(getContext()).load(getItem(position).cover).apply(requestOptions).into(imv_image);
        }
    }
}
