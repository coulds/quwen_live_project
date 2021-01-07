package com.hsjskj.quwen.ui.my.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.aop.CheckNetAspect;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.http.response.CouponBean;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.ui.my.adapter.CouponAdapter;
import com.hsjskj.quwen.ui.my.adapter.ReleaseAdapter;
import com.hsjskj.quwen.ui.my.viewmodel.MyCouponViewModel;
import com.hsjskj.quwen.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponFragment extends MyFragment implements OnRefreshLoadMoreListener, StatusAction {
    private MyCouponViewModel myCouponViewModel;

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView rvCoupon;
    private CouponAdapter couponAdapter;
    private ReleaseAdapter releaseAdapter;
    private String tag;
    private String type;
    private WrapRecyclerView reRelease;


    public static CouponFragment getInstance(String tag, String type) {
        CouponFragment couponFragment = new CouponFragment();
        couponFragment.tag = tag;
        couponFragment.type = type;
        return couponFragment;
    }

    //srl_order
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    protected void initView() {
        myCouponViewModel = new ViewModelProvider(this).get(MyCouponViewModel.class);

        rvCoupon = (RecyclerView) findViewById(R.id.rv_coupon);
        mRefreshLayout = (SmartRefreshLayout) findViewById(R.id.srl_order);
        reRelease = (WrapRecyclerView) findViewById(R.id.re_Release);


    }

    private void initListener() {
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        if(type.equals("1")){
            myCouponViewModel.getMyCouponLiveData().observe(this, dataBeans -> {
                mRefreshLayout.finishLoadMore();
                mRefreshLayout.finishRefresh();
                if (couponAdapter.getPageNumber() == 1) {
                    if (dataBeans == null || dataBeans.isEmpty()) {
                        showEmpty();
                    } else {
                        showComplete();
                    }
                    couponAdapter.setData(dataBeans);
                } else {
                    if (dataBeans == null || dataBeans.isEmpty()) {
                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                    couponAdapter.addData(dataBeans);
                }
            });
        }else {
            myCouponViewModel.getMyReleaseLiveData().observe(this, dataBeans -> {
                mRefreshLayout.finishLoadMore();
                mRefreshLayout.finishRefresh();
                if (releaseAdapter.getPageNumber() == 1) {
                    if (dataBeans == null || dataBeans.isEmpty()) {
                        showEmpty();
                    } else {
                        showComplete();
                    }
                    releaseAdapter.setData(dataBeans);
                } else {
                    if (dataBeans == null || dataBeans.isEmpty()) {
                        mRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                    releaseAdapter.addData(dataBeans);
                }
            });
        }
        onRefresh(mRefreshLayout);
        showLoading();

    }

    @Override
    protected void initData() {
        if (type.equals("1")) {
            rvCoupon.setVisibility(View.VISIBLE);
            reRelease.setVisibility(View.GONE);
            couponAdapter = new CouponAdapter(getContext(),tag);
            rvCoupon.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCoupon.setAdapter(couponAdapter);

        } else if (type.equals("2")) {
            rvCoupon.setVisibility(View.GONE);
            reRelease.setVisibility(View.VISIBLE);
            releaseAdapter = new ReleaseAdapter(getContext(), 1);
            reRelease.setLayoutManager(new LinearLayoutManager(getContext()));
            reRelease.setAdapter(releaseAdapter);
        }
        initListener();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (type.equals("1")) {
            couponAdapter.setPageNumber(couponAdapter.getPageNumber() + 1);
            myCouponViewModel.loadMyCoupon(this, tag, 7, couponAdapter.getPageNumber());
        }else {
            releaseAdapter.setPageNumber(couponAdapter.getPageNumber() + 1);
            myCouponViewModel.loadMyReleaseLeft(this,6,releaseAdapter.getPageNumber());
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (type.equals("1")) {
            couponAdapter.setPageNumber(1);
            myCouponViewModel.loadMyCoupon(this, tag, 7, 1);
        }else {
            releaseAdapter.setPageNumber(1);
            myCouponViewModel.loadMyReleaseLeft(this,6,1);
        }

    }

    @Override
    public HintLayout getHintLayout() {
        return (HintLayout) findViewById(R.id.hint_layout);
    }
}
