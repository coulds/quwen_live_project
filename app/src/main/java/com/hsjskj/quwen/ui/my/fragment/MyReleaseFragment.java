package com.hsjskj.quwen.ui.my.fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.common.MySmartRefreshLayoutFragment;
import com.hsjskj.quwen.http.response.CouponBean;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.ui.my.adapter.CouponAdapter;
import com.hsjskj.quwen.ui.my.adapter.ReleaseAdapter;
import com.hsjskj.quwen.ui.my.viewmodel.MyCouponViewModel;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class MyReleaseFragment extends MySmartRefreshLayoutFragment<HomePublishBean.DataBean> implements OnRefreshLoadMoreListener, StatusAction, ReleaseAdapter.ItemViewListener {
    private MyCouponViewModel myCouponViewModel;
    private ReleaseAdapter releaseAdapter;
    private int type;

    //1未使用，2已使用，3已过期
    public static MyReleaseFragment getInstance(int type) {
        MyReleaseFragment couponFragment = new MyReleaseFragment();
        couponFragment.type=type;
        return couponFragment;
    }

    @Override
    public MyAdapter getAdapter() {
        releaseAdapter=new ReleaseAdapter(getContext(),type);
        releaseAdapter.setItemViewListener(this);
        return releaseAdapter;
    }

    @Override
    public void initRecycler(WrapRecyclerView mRecyclerview) {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void loadHttp(int page) {
        myCouponViewModel.loadMyReleaseLeft(this,6,page);
    }

    @Override
    protected void initData() {
        myCouponViewModel=new ViewModelProvider(this).get(MyCouponViewModel.class);
        myCouponViewModel.getMyReleaseLiveData().observe(this, dataBeans -> {
            finishRefresh();
            setAdapterList(dataBeans);
        });

        showLoading();
        loadHttp(1);
    }

    @Override
    public void delete(String id, int pos) {
        toast("删除点击"+pos);
    }

}
