package com.hsjskj.quwen.ui.home.activity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.UiUtlis;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.common.MySmartRefreshLayoutActivity;
import com.hsjskj.quwen.helper.ItemDecorationHeleper;
import com.hsjskj.quwen.http.response.HomeVideoListBean;
import com.hsjskj.quwen.ui.activity.VideoPlayActivity;
import com.hsjskj.quwen.ui.home.adapter.HomeVideoListItemAdapter;
import com.hsjskj.quwen.ui.home.viewmodel.HomeVideoViewModel;

/**
 * @author : Jun
 * time          : 2020年12月26日 09:42
 * description   : 首页视频列表
 */
public class HomeVideoListActivity extends MySmartRefreshLayoutActivity<HomeVideoListBean.DataBean> implements BaseAdapter.OnItemClickListener {
    private HomeVideoListItemAdapter adapter;
    private static final int VIDEO_COLUMN = 2;
    private HomeVideoViewModel homeFragmentViewModel;

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        HomeVideoListBean.DataBean item = adapter.getItem(position);
        VideoPlayActivity.start(this, "" + item.url, "" + item.title);
    }

    @Override
    public MyAdapter<HomeVideoListBean.DataBean> getAdapter() {
        adapter = new HomeVideoListItemAdapter(this);
        adapter.setOnItemClickListener(this);
        return adapter;
    }

    @Override
    public void initRecycler(WrapRecyclerView mRecyclerview) {
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, VIDEO_COLUMN));
        ItemDecorationHeleper.addVideoItemDecoration(mRecyclerview, UiUtlis.dp2px(getContext(), 8), VIDEO_COLUMN);
    }

    @Override
    public void loadHttp(int page) {
        homeFragmentViewModel.loadHomeVideoList(this,20 , page);
    }

    @Override
    public String getTitleStr() {
        return getResources().getString(R.string.home_go_ask_the_video);
    }

    @Override
    protected void initData() {
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeVideoViewModel.class);
        homeFragmentViewModel.getHomeVideoLiveData().observe(this, objects -> {
            finishRefresh();
            setAdapterList(objects);
        });

        //显示加载 页面 同时访问网络加载第一页
        showLoading();
        loadHttp(1);
    }
}
