package com.hsjskj.quwen.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.hjq.base.BaseAdapter;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;

public class FollowAdapter extends MyAdapter<Object> {
    public FollowAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    public final class ViewHolder extends BaseAdapter.ViewHolder {
        private ViewHolder() {
            super(R.layout.my_concern_item);
        }

        @Override
        public void onBindView(int position) {
        }


    }
}
