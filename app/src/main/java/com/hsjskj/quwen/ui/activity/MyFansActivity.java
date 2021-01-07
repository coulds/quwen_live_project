package com.hsjskj.quwen.ui.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.adapter.FollowAdapter;
import com.hsjskj.quwen.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import java.util.ArrayList;

public class MyFansActivity extends MyActivity implements StatusAction, OnRefreshLoadMoreListener {
    private FollowAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_my_fans;
    }

    @Override
    public void initView() {
        WrapRecyclerView mrecyclerView = (WrapRecyclerView) findViewById(R.id.myfans_WrapRecyclerView);
        SmartRefreshLayout myfans_SmartRefreshLayout = (SmartRefreshLayout) findViewById(R.id.myfans_SmartRefreshLayout);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        this.adapter = new FollowAdapter(this);
        mrecyclerView.setLayoutManager(manager);
        mrecyclerView.setAdapter(this.adapter);
        myfans_SmartRefreshLayout.setOnRefreshLoadMoreListener(this);
        showLoading();
        onRefresh(myfans_SmartRefreshLayout);
    }

    @Override
    public void initData() {
    }

    @Override
    public void onLoadMore(final RefreshLayout refreshLayout) {
        FollowAdapter followAdapter = this.adapter;
        followAdapter.setPageNumber(followAdapter.getPageNumber() + 1);
        postDelayed(new Runnable() {

            public void run() {
                MyFansActivity.this.showComplete();
                refreshLayout.finishLoadMore();
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(new Object());
                objects.add(new Object());
                objects.add(new Object());
                MyFansActivity.this.adapter.addData(objects);
            }
        }, 1000);
    }

    @Override
    public void onRefresh(final RefreshLayout refreshLayout) {
        this.adapter.setPageNumber(1);
        postDelayed(new Runnable() {

            public void run() {
                MyFansActivity.this.showComplete();
                refreshLayout.finishRefresh();
                ArrayList<Object> objects = new ArrayList<>();
                objects.add(new Object());
                objects.add(new Object());
                objects.add(new Object());
                MyFansActivity.this.adapter.setData(objects);
            }
        }, 1000);
    }

    @Override
    public HintLayout getHintLayout() {
        return (HintLayout) findViewById(R.id.myfans_HintLayout);
    }
}
