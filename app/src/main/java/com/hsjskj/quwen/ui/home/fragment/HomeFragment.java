package com.hsjskj.quwen.ui.home.fragment;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;
import com.hsjskj.quwen.ui.home.adapter.HomeAdapter;
import com.hsjskj.quwen.widget.HomeBannerView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time   : 2020年12月24日15:47:50
 * desc   : 项目首页
 */
public final class HomeFragment extends MyFragment<HomeActivity> implements OnRefreshLoadMoreListener {

    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView recyclerview;
    private HomeAdapter mAdapter;
    private HomeBannerView homeBannerView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {
        mRefreshLayout = findViewById(R.id.refresh_layout);
        recyclerview = findViewById(R.id.recyclerview);
        homeBannerView = new HomeBannerView(getContext());
        homeBannerView.addBannerLifecycleObserver(getActivity());
        recyclerview.addHeaderView(homeBannerView);

        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(this, recyclerview);
    }

    @Override
    protected void initData() {
        mAdapter = new HomeAdapter(getContext());
        recyclerview.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.addData(analogData());
            mRefreshLayout.finishLoadMore();
            toast("加载完成");
        }, 1000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.clearData();
            mAdapter.setData(analogData());
            mRefreshLayout.finishRefresh();
            homeBannerView.setBannerPic(bannerData());
            toast("刷新完成");
        }, 1000);
    }

    private List<String> analogData() {
        List<String> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add("item_" + i);
        }
        return objects;
    }

    private List<String> bannerData() {
        List<String> objects = new ArrayList<>();
        objects.add("https://www.baidu.com/img/bd_logo.png");
        objects.add("https://www.baidu.com/img/bd_logo.png");
        return objects;
    }

}