package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;

/**
 * @author : sen
 * time          : 2021年01月04日 11:03
 * description   : quwen_live
 */
public class ProblemFeedBackAdapter extends MyAdapter<Object> {

    public boolean isShowAdd1 = true;
    public ProblemFeedBackAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }
    private final class ViewHolder extends MyAdapter.ViewHolder{
        private final ImageView ivItemImage1;
        private final ImageView ivItemDeleted1;

        public ViewHolder() {
            super(R.layout.item_publish_pic_view);
            ivItemImage1 = (ImageView) findViewById(R.id.iv_item_image);
            ivItemDeleted1 = (ImageView) findViewById(R.id.iv_item_deleted);
        }

        @Override
        public void onBindView(int position) {
            if (isShowAdd1 && position ==0){
                ivItemDeleted1.setVisibility(View.GONE);
            }else {
                ivItemDeleted1.setVisibility(View.VISIBLE);
            }
            GlideApp.with(getContext()).load(getItem(position)).into(ivItemImage1);

        }
    }
//    private final class ViewHolder extends MyAdapter.ViewHolder
}
