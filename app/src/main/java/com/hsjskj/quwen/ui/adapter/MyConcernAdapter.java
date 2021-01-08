package com.hsjskj.quwen.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.BaseAdapter;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.glide.GlideConfig;
import com.hsjskj.quwen.http.response.ConcernBean;
import com.hsjskj.quwen.widget.FollowAppCompatButton;

/**
 * @author : sen
 * time          : 2021年01月08日 13:06
 * description   : quwen_live
 */
public class MyConcernAdapter extends MyAdapter<ConcernBean.concernDataBean> {
    public MyConcernAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new MyConcernAdapter.ViewHolder();
    }

    public final class ViewHolder extends BaseAdapter.ViewHolder {
        private ImageView concern_touxiang;
        private FollowAppCompatButton concern_stuta;
        private TextView concern_id;
        private TextView concern_nickname;



        private ViewHolder() {
            super(R.layout.my_concern_item);
            concern_touxiang = (ImageView) findViewById(R.id.fans_touxiang);
            concern_nickname = (TextView) findViewById(R.id.fans_title);
            concern_stuta = (FollowAppCompatButton) findViewById(R.id.fans_stuta);


        }

        @Override
        public void onBindView(int position) {
            GlideApp.with(getContext()).load(getItem(position).avatar).apply(GlideConfig.requestOptionsAvatar).into(concern_touxiang);
            concern_nickname.setText(getItem(position).user_nickname);

            if ("1".equals(getItem(position).status)){
                concern_stuta.setText("已关注");
                concern_stuta.setFollow(true);
            }else {
                concern_stuta.setText("未关注");
                concern_stuta.setFollow(false);
            }

        }
    }
}
