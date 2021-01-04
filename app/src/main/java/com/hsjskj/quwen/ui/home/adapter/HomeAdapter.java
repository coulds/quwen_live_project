package com.hsjskj.quwen.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.glide.GlideConfig;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.ui.home.widget.StarTagView;
import com.hsjskj.quwen.ui.user.activity.UserPreviewActivity;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月24日16:56:09
 * description   : 首页去问适配器
 */
public final class HomeAdapter extends MyAdapter<HomePublishBean.DataBean> {

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
        private ImageView iv_item_avatar;
        private TextView tv_item_name;
        private TextView tv_item_time;
        private TextView tv_item_title;
        private TextView tv_item_content;
        private StarTagView star_tag;

        private ViewHolder() {
            super(R.layout.home_item_question);
            nineGridView = (NineGridView) findViewById(R.id.nineGridView);
            iv_item_avatar = (ImageView) findViewById(R.id.iv_item_avatar);
            tv_item_name = (TextView) findViewById(R.id.tv_item_name);
            tv_item_time = (TextView) findViewById(R.id.tv_item_time);
            tv_item_title = (TextView) findViewById(R.id.tv_item_title);
            tv_item_content = (TextView) findViewById(R.id.tv_item_content);
            star_tag = (StarTagView) findViewById(R.id.star_tag);
            nineGridView.setGridSpacing(10);
            nineGridView.setMaxSize(3);
            nineGridView.setSingleImageSize(UiUtlis.dp2px(getContext(), 110));
        }

        @Override
        public void onBindView(int position) {
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            HomePublishBean.DataBean item = getItem(position);
            List<String> enclosure = item.enclosure;
            if (enclosure != null) {
                for (String s : enclosure) {
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(s);
                    info.setBigImageUrl(s);
                    imageInfo.add(info);
                }
            }
            nineGridView.setAdapter(new NineGridViewClickAdapter(getContext(), imageInfo));
            tv_item_name.setText(item.getShowName());
            tv_item_time.setText("" + item.create_time);
            tv_item_content.setText("" + item.content);
            tv_item_title.setText("" + item.title);
            star_tag.setTagText(item.constellation, item.isMale(),item.isSetMale());
            GlideApp.with(getContext()).load(item.avatar).apply(GlideConfig.requestOptionsAvatar).into(iv_item_avatar);
            iv_item_avatar.setOnClickListener(v -> {
                UserPreviewActivity.start(getContext(), item.user_id);
            });
        }
    }
}