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
public abstract class MySmartRefreshLayoutFragment<T> extends MyFragment implements StatusAction, OnRefreshLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerview;
    private MyAdapter<T> myAdapter;

    public abstract MyAdapter<T> getAdapter();

    public abstract void initRecycler(WrapRecyclerView mRecyclerview);

    public abstract void loadHttp(int page);

    @Override
    protected int getLayoutId() {
        return R.layout.common_list_fragment;
    }

    @Override
    protected void initView() {
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.refresh_layout);
        mRecyclerview = (WrapRecyclerView) findViewById(R.id.recyclerview);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        myAdapter = getAdapter();
        initRecycler(mRecyclerview);
        mRecyclerview.setAdapter(myAdapter);
    }

    @Override
    public HintLayout getHintLayout() {
        return (HintLayout) findViewById(R.id.hint_layout);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        myAdapter.setPageNumber((myAdapter.getPageNumber() + 1));
        loadHttp(myAdapter.getPageNumber());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        myAdapter.setPageNumber(1);
        loadHttp(myAdapter.getPageNumber());
    }

    protected void finishRefresh() {
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

    protected void setAdapterList(List<T> objects) {
        if (myAdapter.getPageNumber() == 1) {
            if (objects == null || objects.isEmpty()) {
                showEmpty();
            } else {
                showComplete();
            }
            myAdapter.setData(objects);
        } else {
            if (objects == null || objects.isEmpty()) {
                mRefreshLayout.finishLoadMoreWithNoMoreData();
            }
            myAdapter.addData(objects);
        }
    }

}
