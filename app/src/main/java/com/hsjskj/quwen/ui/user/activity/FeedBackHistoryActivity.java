package com.hsjskj.quwen.ui.user.activity;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.view.SwitchButton;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.event.FeedBackEvent;
import com.hsjskj.quwen.event.UserInfoUpgradeEvent;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.ProblemBackApi;
import com.hsjskj.quwen.http.response.BackFeedHistoryListBean;
import com.hsjskj.quwen.ui.adapter.FeedBackHistoryAdapter;
import com.hsjskj.quwen.ui.user.viewmodel.UserInfoViewModel;
import com.hsjskj.quwen.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月04日 16:02
 * description   : 反馈历史
 */
public class FeedBackHistoryActivity extends MyActivity implements OnRefreshListener, StatusAction {
    private Button history_btn;
    private SmartRefreshLayout smartRefreshLayout;
    private List<BackFeedHistoryListBean.DataBean> messagelist;
    private FeedBackHistoryAdapter feedBackHistoryAdapter;
    private RecyclerView mrecyclerView;
    private UserInfoViewModel userInfoViewModel;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.feedbackhistory;
    }

    @Override
    protected void initView() {
        history_btn = (Button) findViewById(R.id.back_history_btn);
        smartRefreshLayout = findViewById(R.id.back_history_refresh);
        setOnClickListener(this.history_btn);

        mrecyclerView = (RecyclerView) findViewById(R.id.back_history_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(linearLayoutManager);

        messagelist = new ArrayList<>();
        feedBackHistoryAdapter = new FeedBackHistoryAdapter(messagelist);
        mrecyclerView.setAdapter(feedBackHistoryAdapter);
        smartRefreshLayout.setOnRefreshListener(this);

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void initData() {
        userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);

        userInfoViewModel.getbackhistory().observe(this, s -> {
            smartRefreshLayout.finishRefresh();
            if (s != null) {
                if (page == 1) {
                    if (s.isEmpty()) {
                        showEmpty();
                    } else {
                        showComplete();

                    }
                    messagelist.clear();
                }
                messagelist.addAll(0, s);
                feedBackHistoryAdapter.notifyDataSetChanged();
                if (page == 1) {
                    mrecyclerView.post(() -> {
                        int i = messagelist.size() - 1;
                        if (i < 0) {
                            return;
                        }
                        mrecyclerView.scrollToPosition(i);
                    });
                }

                page++;
            } else {
                showError(v -> {
                    onRefresh(smartRefreshLayout);
                });
            }
        });

        onRefresh(smartRefreshLayout);
        showLoading();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpgradeUserInfoEvent(FeedBackEvent event) {
        mrecyclerView.post(new Runnable() {
            @Override
            public void run() {
                page = 1;
                smartRefreshLayout.finishRefresh();
                onRefresh(smartRefreshLayout);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == history_btn) {
            startActivity(PorblemFeedBackActivity.class);
        }

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        userInfoViewModel.loadbackhistory(this, "10", page);
    }

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hint_layout);
    }
}
