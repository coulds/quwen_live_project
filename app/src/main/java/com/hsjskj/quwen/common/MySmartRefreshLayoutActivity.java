package com.hsjskj.quwen.common;

import androidx.annotation.NonNull;

import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * @author : Jun
 * time          : 2021年01月08日 10:53
 * description   : 通用刷新列表
 */
public abstract class MySmartRefreshLayoutActivity<T> extends MyActivity implements StatusAction, OnRefreshLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerview;

    public abstract MyAdapter<T> getAdapter();

    public abstract void initRecycler(WrapRecyclerView mRecyclerview);

    public abstract void loadHttp(int page);

    public abstract String getTitleStr();

    @Override
    protected int getLayoutId() {
        return R.layout.common_list_activity;
    }

    @Override
    protected void initView() {
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerview = findViewById(R.id.recyclerview);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        getAdapter();
        initRecycler(mRecyclerview);
        mRecyclerview.setAdapter(getAdapter());
        setTitle(getTitleStr());
    }

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hint_layout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        getAdapter().setPageNumber((getAdapter().getPageNumber() + 1));
        loadHttp(getAdapter().getPageNumber());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getAdapter().setPageNumber(1);
        loadHttp(getAdapter().getPageNumber());
    }

    protected void finishRefresh() {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

    protected void setAdapterList(List<T> objects) {
        if (getAdapter().getPageNumber() == 1) {
            if (objects == null || objects.isEmpty()) {
                showEmpty();
            } else {
                showComplete();
            }
            getAdapter().setData(objects);
        } else {
            if (objects == null || objects.isEmpty()) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            getAdapter().addData(objects);
        }
    }

}
