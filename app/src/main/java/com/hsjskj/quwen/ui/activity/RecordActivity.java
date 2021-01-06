package com.hsjskj.quwen.ui.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.adapter.AccountBalanceAdapter;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class RecordActivity extends MyActivity {
    private RecyclerView reDetailed;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    @Override
    protected void initView() {
        reDetailed = findViewById(R.id.re_detailed);
        reDetailed.setLayoutManager(new LinearLayoutManager(this));
        reDetailed.setAdapter(new AccountBalanceAdapter(this,1));
    }

    @Override
    protected void initData() {

    }
}
