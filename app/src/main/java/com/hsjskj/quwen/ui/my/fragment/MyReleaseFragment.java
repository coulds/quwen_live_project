package com.hsjskj.quwen.ui.my.fragment;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.common.MySmartRefreshLayoutFragment;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.response.CouponBean;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.ui.dialog.ExtensionDialog;
import com.hsjskj.quwen.ui.dialog.MessageDialog;
import com.hsjskj.quwen.ui.dialog.UIDialog;
import com.hsjskj.quwen.ui.home.activity.HomePublishActivity;
import com.hsjskj.quwen.ui.my.adapter.CouponAdapter;
import com.hsjskj.quwen.ui.my.adapter.ReleaseAdapter;
import com.hsjskj.quwen.ui.my.viewmodel.MyCouponViewModel;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class MyReleaseFragment extends MySmartRefreshLayoutFragment<HomePublishBean.DataBean> implements OnRefreshLoadMoreListener, StatusAction, BaseAdapter.OnChildClickListener {
    private MyCouponViewModel myCouponViewModel;
    private ReleaseAdapter releaseAdapter;
    private int type;

    //1未使用，2已使用，3已过期
    public static MyReleaseFragment getInstance(int type) {
        MyReleaseFragment couponFragment = new MyReleaseFragment();
        couponFragment.type = type;
        return couponFragment;
    }

    @Override
    public MyAdapter getAdapter() {
        releaseAdapter = new ReleaseAdapter(getContext(), type);
        releaseAdapter.setOnChildClickListener(R.id.delete, this);
        releaseAdapter.setOnChildClickListener(R.id.tv_edit, this);
        return releaseAdapter;
    }

    @Override
    public void initRecycler(WrapRecyclerView mRecyclerview) {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void loadHttp(int page) {
        myCouponViewModel.loadMyReleaseLeft(this, 6, page);
    }

    @Override
    protected void initData() {
        myCouponViewModel = new ViewModelProvider(this).get(MyCouponViewModel.class);
        myCouponViewModel.getMyReleaseLiveData().observe(this, dataBeans -> {
            finishRefresh();
            setAdapterList(dataBeans);
        });
        myCouponViewModel.postReleaseDeleteLiveData().observe(this, index -> {
            if (index >= 0) {
                releaseAdapter.removeItem(index);
                setAdapterList(releaseAdapter.getData());
            }
        });
        showLoading();
        loadHttp(1);
    }


    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        if (childView.getId() == R.id.delete) {
            new MessageDialog.Builder(getContext())
                    //.setTitle("是否删除发布内容")
                    .setMessage("是否删除发布内容")
                    .setConfirm(getString(R.string.common_confirm))
                    .setCancel(getString(R.string.common_cancel))
                    .setListener(new MessageDialog.OnListener() {
                        @Override
                        public void onConfirm(BaseDialog dialog) {
                            myCouponViewModel.loadMyReleaseDelete(getViewLifecycleOwner(), releaseAdapter.getItem(position).id, position);
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                        }
                    })
                    .show();
        } else if (childView.getId() == R.id.tv_edit) {
            HomePublishActivity.start(getContext(), releaseAdapter.getItem(position));
        }
    }
}
