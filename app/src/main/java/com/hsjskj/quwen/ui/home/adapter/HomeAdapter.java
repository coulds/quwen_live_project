package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;

/**
 * @author : Jun
 * time          : 2020年12月24日16:56:09
 * description   : 用户信息保存
 */
public final class HomeAdapter extends MyAdapter<String> {

    public HomeAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {
        private NineGridView nineGridView;

        private ViewHolder() {
            super(R.layout.home_item_question);
            nineGridView = (NineGridView) findViewById(R.id.nineGridView);
            nineGridView.setGridSpacing(10);
            nineGridView.setMaxSize(3);
            nineGridView.setSingleImageSize(UiUtlis.dp2px(getContext(),110));
        }

        @Override
        public void onBindView(int position) {
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            for (int i = 0; i < position + 1; i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(getItem(position));
                info.setBigImageUrl(getItem(position));
                imageInfo.add(info);
            }
            nineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));
        }
    }
}