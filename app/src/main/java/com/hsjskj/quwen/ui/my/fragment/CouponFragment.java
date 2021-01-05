package com.hsjskj.quwen.ui.my.fragment;

import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.ui.adapter.AccountBalanceAdapter;
import com.hsjskj.quwen.ui.my.adapter.CouponAdapter;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponFragment extends MyFragment {
    private RecyclerView rvCoupon;
    private CouponAdapter couponAdapter;
    private String tag;
    private TextView tv;
    public static CouponFragment getInstance(String tag) {
        CouponFragment recordFragment = new CouponFragment();
        recordFragment.tag = tag;
        return recordFragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    protected void initView() {
        rvCoupon = (RecyclerView) findViewById(R.id.rv_coupon);
        couponAdapter=new CouponAdapter(getContext());
        rvCoupon.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCoupon.setAdapter(couponAdapter);
    }

    @Override
    protected void initData() {

    }
}
