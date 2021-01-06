package com.hsjskj.quwen.ui.my.fragment;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.ui.my.adapter.CouponAdapter;
import com.hsjskj.quwen.ui.my.adapter.ReleaseAdapter;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponFragment extends MyFragment {
    private RecyclerView rvCoupon;
    private CouponAdapter couponAdapter;
    private ReleaseAdapter releaseAdapter;
    private String tag;
    private TextView tv;
    private String type;
    private WrapRecyclerView reRelease;
    private TextView no_data;

    public static CouponFragment getInstance(String tag, String type) {
        CouponFragment couponFragment = new CouponFragment();
        couponFragment.tag = tag;
        couponFragment.type = type;
        return couponFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    protected void initView() {
        no_data = (TextView) findViewById(R.id.no_data);
        rvCoupon = (RecyclerView) findViewById(R.id.rv_coupon);
        reRelease = (WrapRecyclerView) findViewById(R.id.re_Release);
        if (type.equals("1")) {
            rvCoupon.setVisibility(View.VISIBLE);
            reRelease.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            couponAdapter = new CouponAdapter(getContext());
            rvCoupon.setLayoutManager(new LinearLayoutManager(getContext()));
            rvCoupon.setAdapter(couponAdapter);
        } else if (type.equals("2")) {
            rvCoupon.setVisibility(View.GONE);
            reRelease.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.GONE);
            releaseAdapter=new ReleaseAdapter(getContext(),1);
            reRelease.setLayoutManager(new LinearLayoutManager(getContext()));
            reRelease.setAdapter(releaseAdapter);
        }

    }

    @Override
    protected void initData() {

    }
}
