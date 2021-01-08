package com.hsjskj.quwen.ui.my.activity;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.my.adapter.BankCardAdapter;
import com.hsjskj.quwen.ui.my.object.BankCard;
import com.hsjskj.quwen.ui.my.viewmodel.BankCardNetworkRequest;
import com.hsjskj.quwen.ui.my.viewmodel.RequestDataBackListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class BankCardActivity extends MyActivity {
    RecyclerView rcv_bankCard;
    List<BankCard.DataBean> bankCards;
    SmartRefreshLayout refresh_layout;
    BankCardAdapter bankCardAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank;
    }

    @Override
    protected void initView() {
        rcv_bankCard = findViewById(R.id.rcv_bankCard);
        refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();//传入false表示刷新失败
            }
        });
        refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore();//传入false表示加载失败
            }
        });
    }

    @Override
    protected void initData() {
        //初始化数据

        BankCardNetworkRequest bankCardNetworkRequest = new BankCardNetworkRequest();
        bankCardNetworkRequest.getBankCardList(this,1, new RequestDataBackListener() {
            @Override
            public void onFinish(List<BankCard.DataBean> responseData) {
                //第一页清空
                bankCards.clear();

                bankCards.addAll(responseData);
                bankCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Exception e) {
                Log.e("printStackTrace", "" + e);
            }
        });
        bankCards = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_bankCard.setLayoutManager(linearLayoutManager);
        bankCardAdapter = new BankCardAdapter(this, bankCards);
        rcv_bankCard.setAdapter(bankCardAdapter);
    }


    @Override
    public void onRightClick(View v) {
        startActivity(AddBankCardActivity.class);
    }
}