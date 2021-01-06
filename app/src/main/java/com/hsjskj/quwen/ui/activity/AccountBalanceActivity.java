package com.hsjskj.quwen.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.ui.adapter.AccountBalanceAdapter;
import com.hsjskj.quwen.ui.user.activity.UserPreviewActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Administrator :ZB
 * 2021/1/4 0004
 * describe :账户余额
 **/
public class AccountBalanceActivity extends MyActivity  {
    private RecyclerView reDetailed;
    private LinearLayout lineRight;
    private TextView tvWithdrawal;
    private SmartRefreshLayout mRefreshLayout;

    public static void start(Context context) {
        Intent intent = new Intent(context, AccountBalanceActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_balance;
    }

    @Override
    protected void initView() {
        lineRight = findViewById(R.id.line_right);
        setOnClickListener(R.id.line_right);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        reDetailed = findViewById(R.id.re_detailed);
        reDetailed.setLayoutManager(new LinearLayoutManager(this));
        reDetailed.setAdapter(new AccountBalanceAdapter(this,2));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();//传入false表示刷新失败
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore();//传入false表示加载失败
            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent =new Intent();
        switch (v.getId()){
            case R.id.line_right:
                intent.setClass(AccountBalanceActivity.this,WithdrawalActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void initData() {

    }


}
