package com.hsjskj.quwen.ui.activity;

import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.common.MySmartRefreshLayoutActivity;
import com.hsjskj.quwen.http.response.FansBean;
import com.hsjskj.quwen.ui.adapter.FollowAdapter;
import com.hsjskj.quwen.ui.user.viewmodel.MyFansViewModel;

public class MyFansActivity extends MySmartRefreshLayoutActivity<FansBean.DataBean> implements BaseAdapter.OnChildClickListener {
    private FollowAdapter adapter;
    private MyFansViewModel myFansViewModel;

    @Override
    public MyAdapter<FansBean.DataBean> getAdapter() {
        adapter = new FollowAdapter(this);
        adapter.setOnChildClickListener(R.id.fans_stuta, this);
        return adapter;
    }

    @Override
    public void initRecycler(WrapRecyclerView mRecyclerview) {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void loadHttp(int page) {
        myFansViewModel.loadmyfans(this, 10, page);
    }

    @Override
    public String getTitleStr() {
        return "我的粉丝";
    }

    @Override
    protected void initData() {
        myFansViewModel = new ViewModelProvider(this).get(MyFansViewModel.class);
        myFansViewModel.getmyfans().observe(this, s -> {
            finishRefresh();
            setAdapterList(s);
        });
        showLoading();
        loadHttp(1);
    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        showDialog();
        myFansViewModel.loadFollowUserInfoLiveData(this, adapter.getItem(position).touid).observe(this, aBoolean -> {
            hideDialog();
            if (aBoolean) {
                //关注 或者取关成功
                String status = adapter.getItem(position).status;
                if ("1".equals(status)) {
                    adapter.getItem(position).status = "0";
                } else {
                    adapter.getItem(position).status = "1";
                }
                adapter.notifyItemChanged(position);
            }
        });
    }

}
