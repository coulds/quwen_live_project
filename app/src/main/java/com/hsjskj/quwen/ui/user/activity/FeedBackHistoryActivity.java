package com.hsjskj.quwen.ui.user.activity;

import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.widget.view.SwitchButton;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.adapter.FeedBackHistoryAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月04日 16:02
 * description   : 反馈历史
 */
public class FeedBackHistoryActivity extends MyActivity{
    private Button history_btn;
    private SmartRefreshLayout smartRefreshLayout;
    private List<Object> messagelist;
    private FeedBackHistoryAdapter feedBackHistoryAdapter;
    private RecyclerView mrecyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.feedbackhistory;
    }

    @Override
    protected void initView() {
        history_btn = (Button)findViewById(R.id.back_history_btn);
        setOnClickListener(this.history_btn);
    }

    @Override
    protected void initData() {
        mrecyclerView = (RecyclerView)findViewById(R.id.back_history_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(linearLayoutManager);
        messagelist=new ArrayList<>();
        messagelist.add(new Object());
        messagelist.add(new Object());
        messagelist.add(new Object());
        feedBackHistoryAdapter = new FeedBackHistoryAdapter(messagelist);
        mrecyclerView.setAdapter(feedBackHistoryAdapter);

    }

    @Override
    public void onClick(View v) {
       if (v == history_btn){
           startActivity(PorblemFeedBackActivity.class);
       }

    }
}
