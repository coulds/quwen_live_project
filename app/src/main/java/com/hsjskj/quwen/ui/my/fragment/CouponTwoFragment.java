package com.hsjskj.quwen.ui.my.fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.common.MySmartRefreshLayoutFragment;
import com.hsjskj.quwen.http.response.CouponBean;
import com.hsjskj.quwen.ui.my.adapter.CouponAdapter;
import com.hsjskj.quwen.ui.my.viewmodel.MyCouponViewModel;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponTwoFragment extends MySmartRefreshLayoutFragment<CouponBean.DataBean> implements OnRefreshLoadMoreListener, StatusAction {
    private MyCouponViewModel myCouponViewModel;
    private CouponAdapter couponAdapter;
    private String tag;

    //1未使用，2已使用，3已过期
    public static CouponTwoFragment getInstance(String tag) {
        CouponTwoFragment couponFragment = new CouponTwoFragment();
        couponFragment.tag=tag;
        return couponFragment;
    }

    @Override
    public MyAdapter<CouponBean.DataBean> getAdapter() {
        couponAdapter=new CouponAdapter(getContext(),tag);
        return couponAdapter;
    }

    @Override
    public void initRecycler(WrapRecyclerView mRecyclerview) {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void loadHttp(int page) {
        myCouponViewModel.loadMyCoupon(this, tag, 10, page);
    }

    @Override
    protected void initData() {
        myCouponViewModel=new ViewModelProvider(this).get(MyCouponViewModel.class);
        myCouponViewModel.getMyCouponLiveData().observe(this, dataBeans -> {
            finishRefresh();
            setAdapterList(dataBeans);
        });

        showLoading();
        loadHttp(1);
    }

}
