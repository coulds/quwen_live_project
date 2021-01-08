package com.hsjskj.quwen.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.base.BaseAdapter;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.glide.GlideConfig;
import com.hsjskj.quwen.http.response.FansBean;
import com.hsjskj.quwen.ui.user.viewmodel.UserInfoViewModel;

import java.util.List;

public class FollowAdapter extends MyAdapter<FansBean.DataBean> {
    public FollowAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FollowAdapter.ViewHolder();
    }

    public final class ViewHolder extends BaseAdapter.ViewHolder {
        private ImageView fans_touxiang;
        private TextView fans_stuta;
        private TextView fans_id;
        private TextView fans_nickname;


        private ViewHolder() {
            super(R.layout.my_concern_item);
            fans_touxiang = (ImageView) findViewById(R.id.fans_touxiang);
            fans_nickname = (TextView) findViewById(R.id.fans_title);
            fans_stuta= (TextView) findViewById(R.id.fans_stuta);


        }

        @Override
        public void onBindView(int position) {

            GlideApp.with(getContext())
                    .load(getItem(position).avatar)
                    .apply(GlideConfig.requestOptionsAvatar)
                    .into(fans_touxiang);
            fans_nickname.setText(getItem(position).user_nickname);
            fans_stuta.setText(getItem(position).status);


        }






    }
}
