package com.hsjskj.quwen.ui.my.activity;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.common.MySmartRefreshLayoutActivity;
import com.hsjskj.quwen.ui.my.adapter.BankCardAdapter;
import com.hsjskj.quwen.ui.my.object.BankCard;
import com.hsjskj.quwen.ui.my.viewmodel.BankCardNetworkRequest;
import com.hsjskj.quwen.ui.my.viewmodel.RequestDataBackListener;

import java.util.List;

public class BankCardActivity extends MySmartRefreshLayoutActivity<BankCard.DataBean> implements BaseAdapter.OnChildClickListener {
    BankCardNetworkRequest bankCardNetworkRequest;


    @Override
    public MyAdapter<BankCard.DataBean> getAdapter() {
        BankCardAdapter bankCardAdapter = new BankCardAdapter(this);
        bankCardAdapter.setOnChildClickListener(R.id.tv_bankCardWrite, this);
        return bankCardAdapter;
    }

    @Override
    public void initRecycler(WrapRecyclerView mRecyclerview) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void loadHttp(int page) {
        bankCardNetworkRequest.getBankCardList(this, page, new RequestDataBackListener() {
            @Override
            public void onFinish(List<BankCard.DataBean> responseData) {
                finishRefresh();
                setAdapterList(responseData);
            }

            @Override
            public void onError(Exception e) {
                if (page == 1) {
                    showError(v -> loadHttp(1));
                }
            }
        });
    }

    @Override
    public String getTitleStr() {
        return "银行卡";
    }

    @Override
    protected void initData() {
        setRightTitle("添加银行卡");
        bankCardNetworkRequest = new BankCardNetworkRequest();

        showLoading();
        loadHttp(1);
    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        Intent intent = new Intent(getContext(), AddBankCardActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRightClick(View v) {
        startActivityForResult(AddBankCardActivity.class, (resultCode, data) -> {
            //回调
            initData();
        });
    }

}