package com.hsjskj.quwen.ui.activity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.common.MySmartRefreshLayoutActivity;
import com.hsjskj.quwen.http.response.ConcernBean;
import com.hsjskj.quwen.http.response.FansBean;
import com.hsjskj.quwen.ui.adapter.MyConcernAdapter;
import com.hsjskj.quwen.ui.user.viewmodel.MyConcernViewModel;

/**
 * @author : sen
 * time          : 2021年01月07日 14:36
 * description   : quwen_live
 */
public class MyConcernActivity extends MySmartRefreshLayoutActivity<ConcernBean.concernDataBean> implements BaseAdapter.OnChildClickListener {
    private MyConcernAdapter concernAdapter;
    private MyConcernViewModel concernViewModel;
    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {

    }

    @Override
    public MyAdapter<ConcernBean.concernDataBean> getAdapter() {
        concernAdapter = new MyConcernAdapter(this);
        concernAdapter.setOnChildClickListener(R.id.fans_stuta,this);
        return concernAdapter;

    }

    @Override
    public void initRecycler(WrapRecyclerView mRecyclerview) {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void loadHttp(int page) {
        concernViewModel.loadmyconcern(this,10,page);

    }

    @Override
    public String getTitleStr() {
        return "我的关注";
    }

    @Override
    protected void initData() {
        concernViewModel = new ViewModelProvider(this).get(MyConcernViewModel.class);
        concernViewModel.getmyconcern().observe(this,s->{
            finishRefresh();
            setAdapterList(s);
        });
        showDialog();
        loadHttp(1);

    }
}
