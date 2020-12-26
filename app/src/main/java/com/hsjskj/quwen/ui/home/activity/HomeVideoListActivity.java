package com.hsjskj.quwen.ui.home.activity;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.UiUtlis;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.helper.ItemDecorationHeleper;
import com.hsjskj.quwen.ui.activity.VideoPlayActivity;
import com.hsjskj.quwen.ui.home.adapter.HomeVideoListItemAdapter;
import com.hsjskj.quwen.ui.home.viewmodel.HomeVideoViewModel;
import com.hsjskj.quwen.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * @author : Jun
 * time          : 2020年12月26日 09:42
 * description   : 首页视频列表
 */
public class HomeVideoListActivity extends MyMvvmActivity<HomeVideoViewModel> implements StatusAction, OnRefreshLoadMoreListener, BaseAdapter.OnItemClickListener {
    private SmartRefreshLayout mRefreshLayout;
    private HintLayout mHintLayout;
    private WrapRecyclerView mRecyclerview;
    private HomeVideoListItemAdapter adapter;
    private static final int VIDEO_COLUMN = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.home_video_list_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mHintLayout = (HintLayout) findViewById(R.id.hint_layout);
        mRecyclerview = (WrapRecyclerView) findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, VIDEO_COLUMN));
        adapter = new HomeVideoListItemAdapter(this);
        adapter.setOnItemClickListener(this);
        mRecyclerview.setAdapter(adapter);
        ItemDecorationHeleper.addVideoItemDecoration(mRecyclerview, UiUtlis.dp2px(getContext(), 8), VIDEO_COLUMN);
    }

    @Override
    protected void initData() {
        mViewModel.getLiveDataVideoList().observe(this, objects -> {
            mRefreshLayout.finishLoadMore();
            mRefreshLayout.finishRefresh();
            showComplete();
            if (adapter.getPageNumber() == 1 && objects != null) {
                adapter.setData(objects);
            } else {
                adapter.addData(objects);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        showLoading();
        onRefresh(mRefreshLayout);
        postDelayed(() -> {
            //显示广告弹窗

        },1000);
    }

    @Override
    public HintLayout getHintLayout() {
        return mHintLayout;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        adapter.setPageNumber(adapter.getPageNumber() + 1);
        mViewModel.loadDataVideoList(adapter.getPageNumber(), 20);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        adapter.setPageNumber(1);
        mViewModel.loadDataVideoList(adapter.getPageNumber(), 20);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        VideoPlayActivity.start(this, "http://vfx.mtime.cn/Video/2019/06/29/mp4/190629004821240734.mp4", "速度与激情特别行动");
    }
}
