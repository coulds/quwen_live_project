package com.hsjskj.quwen.ui.my.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.ui.home.widget.StarTagView;
import com.lzy.ninegrid.NineGridView;


/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class ReleaseAdapter extends MyAdapter<String> {
    private int TYPE_LEFT = 1;
    private int TYPE_RIGHT = 2;
    private int TYPE;

    public ReleaseAdapter(@NonNull Context context, int TYPE) {
        super(context);
        this.TYPE = TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LEFT) {
            return new ViewHolderLeft();
        } else {
            return new ViewHolderRight();
        }

    }

    private final class ViewHolderLeft extends MyAdapter.ViewHolder {
        private NineGridView nineGridView;
        private ImageView iv_item_avatar;
        private TextView tv_item_name;
        private TextView tv_item_time;
        private TextView tv_item_title;
        private TextView tv_item_content;
        private StarTagView star_tag;

        private ViewHolderLeft() {
            super(R.layout.item_release_left);
            nineGridView = (NineGridView) findViewById(R.id.nineGridView);
            nineGridView.setGridSpacing(10);
            nineGridView.setMaxSize(3);
            nineGridView.setSingleImageSize(UiUtlis.dp2px(getContext(), 110));
        }

        @Override
        public void onBindView(int position) {
            // nineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));
        }
    }

    private final class ViewHolderRight extends MyAdapter.ViewHolder {


        private ViewHolderRight() {
            super(R.layout.item_release_left);

        }

        @Override
        public void onBindView(int position) {
            // nineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if(TYPE==1){
            return TYPE_LEFT;
        }
        if (TYPE==1) {
            return TYPE_RIGHT;
        }
        return super.getItemViewType(position);
    }
}
