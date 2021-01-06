package com.hsjskj.quwen.ui.home.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyCacheInfo;
import com.hsjskj.quwen.http.response.BannerBean;
import com.hsjskj.quwen.http.response.HomeVideoListBean;
import com.hsjskj.quwen.ui.home.adapter.HomeVideoAdapter;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月25日 09:27
 * description   : quwen_live
 */
public class HomeVideoView extends LinearLayout {

    private RecyclerView recyclerView;
    private HomeVideoAdapter adapter;

    public HomeVideoView(Context context) {
        this(context, null);
    }

    public HomeVideoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeVideoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(context).inflate(R.layout.layout_home_video_view, this);
        initView();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int layoutPosition = parent.getChildLayoutPosition(view);
                if (layoutPosition == 0) {
                    outRect.left = UiUtlis.dp2px(getContext(), 15);
                }
            }
        });
        adapter = new HomeVideoAdapter(getContext());
        adapter.setOnItemClickListener((recyclerView, itemView, position) -> {
            if (listener != null) {
                listener.onItemVideoClick(position, adapter.getItem(position));
            }
        });
        recyclerView.setAdapter(adapter);
        try {
            String cache = MyCacheInfo.getInstance().getHomeVideoCache();
            if (!"".equals(cache) && !TextUtils.isEmpty(cache)) {
                setData(JSON.parseArray(cache, HomeVideoListBean.DataBean.class));
            }
        } catch (Exception e) {
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);
        findViewById(R.id.tv_show_more).setOnClickListener(v -> {
            if (listener != null) {
                listener.onMoreClick();
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setData(List<HomeVideoListBean.DataBean> datas) {
//        if (datas == null || datas.isEmpty()) {
//            setVisibility(GONE);
//        } else {
//            setVisibility(VISIBLE);
//        }
        if (datas != null) {
            MyCacheInfo.getInstance().setHomeVideoCache(JSON.toJSONString(datas));
        }
        adapter.setData(datas);
    }

    private HomeVideoViewListener listener;

    public void setListener(HomeVideoViewListener listener) {
        this.listener = listener;
    }

    public interface HomeVideoViewListener {
        void onMoreClick();

        void onItemVideoClick(int index, HomeVideoListBean.DataBean url);
    }
}
