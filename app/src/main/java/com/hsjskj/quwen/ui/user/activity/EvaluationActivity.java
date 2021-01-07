package com.hsjskj.quwen.ui.user.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.my.adapter.BankCardAdapter;
import com.hsjskj.quwen.ui.my.object.BankCard;
import com.hsjskj.quwen.ui.user.adapter.EvaluationAdapter;
import com.hsjskj.quwen.ui.user.object.Evaluation;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class EvaluationActivity extends MyActivity {
    private RecyclerView rcv_evaluations;
    private List<Evaluation> evaluations;
    private SmartRefreshLayout to_refresh_layout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluation;
    }

    @Override
    protected void initView() {
         rcv_evaluations = findViewById(R.id.rcv_evaluations);
         setOnClickListener(R.id.btn_evaluation);
        to_refresh_layout = findViewById(R.id.to_refresh_layout);
        to_refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh();//传入false表示刷新失败
            }
        });
        to_refresh_layout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore();//传入false表示加载失败
            }
        });
    }

    @Override
    protected void initData() {

        evaluations=new ArrayList<>();
        Evaluation evaluation=new Evaluation("01",R.drawable.p2,"花花花花花花er","12-7 09:48","主播真的厉害，把我近期的状态说的太对了，也解答了我这么久以来的疑问，谢谢您谢谢您");
        evaluations.add(evaluation);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_evaluations.setLayoutManager(linearLayoutManager);
        EvaluationAdapter evaluationAdapter = new EvaluationAdapter(this,evaluations);
        rcv_evaluations.setAdapter(evaluationAdapter);
    }

    @Override
    public void onClick(View v) {
        startActivity(WriteEvaluationActivity.class);
    }
}
