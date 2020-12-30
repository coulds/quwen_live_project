package com.hsjskj.quwen.ui.home.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseAdapter;
import com.hjq.base.BaseDialog;
import com.hjq.base.UiUtlis;
import com.hjq.toast.ToastUtils;
import com.hjq.widget.layout.WrapRecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.http.response.BannerBean;
import com.hsjskj.quwen.http.response.HasCouponBean;
import com.hsjskj.quwen.http.response.HomeVideoListBean;
import com.hsjskj.quwen.ui.activity.VideoPlayActivity;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;
import com.hsjskj.quwen.ui.home.activity.HomeQuestionDetails;
import com.hsjskj.quwen.ui.home.activity.HomeVideoListActivity;
import com.hsjskj.quwen.ui.home.adapter.HomeAdapter;
import com.hsjskj.quwen.ui.home.viewmodel.HomeFragmentViewModel;
import com.hsjskj.quwen.ui.home.viewmodel.HomeVideoViewModel;
import com.hsjskj.quwen.ui.home.widget.HomeBannerView;
import com.hsjskj.quwen.ui.home.widget.HomeLiveView;
import com.hsjskj.quwen.ui.home.widget.HomeNoticeView;
import com.hsjskj.quwen.ui.home.widget.HomeVideoView;
import com.hsjskj.quwen.widget.HintLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author : Jun
 * time   : 2020年12月24日15:47:50
 * desc   : 项目首页
 */
public final class HomeFragment extends MyFragment<HomeActivity> implements OnRefreshLoadMoreListener
        , HomeVideoView.HomeVideoViewListener
        , HomeLiveView.HomeVideoViewListener
        , BaseAdapter.OnItemClickListener
        , StatusAction {

    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView recyclerviewQuerstion;
    private HomeAdapter mAdapter;
    private HomeBannerView homeBannerView;
    private HomeVideoView homeVideoView;
    private HomeLiveView homeLiveView;
    private HomeNoticeView homeNoticeView;

    private HomeFragmentViewModel homeFragmentViewModel;
    private HomeVideoViewModel homeVideoViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView() {
        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        homeVideoViewModel = new ViewModelProvider(this).get(HomeVideoViewModel.class);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        recyclerviewQuerstion = findViewById(R.id.recyclerviewQuerstion);
        homeBannerView = findViewById(R.id.bannerView);
        homeVideoView = findViewById(R.id.videoView);
        homeLiveView = findViewById(R.id.liveView);
        homeNoticeView = findViewById(R.id.noticeView);
        // 给这个 ToolBar 设置顶部内边距，才能和 TitleBar 进行对齐
        ImmersionBar.setTitleBar(this, findViewById(R.id.ll_bg));
    }

    private void initListener() {
        homeVideoView.setListener(this);
        homeLiveView.setListener(this);
        mRefreshLayout.setOnRefreshLoadMoreListener(this);

        homeFragmentViewModel.getHomeBannerLiveData().observe(this, bannerBeans -> {
            homeBannerView.setBannerPic(bannerBeans);
        });
        homeFragmentViewModel.getHomeNoticeLiveData().observe(this, noticeBean -> {
            homeNoticeView.setNotices(Collections.singletonList(noticeBean));
        });
        homeVideoViewModel.getHomeVideoLiveData().observe(this, dataBeans -> {
            homeVideoView.setData(dataBeans);
        });

        onRefresh(mRefreshLayout);
        showLoading();
        homeFragmentViewModel.loadHomeHasCoupon(this).observe(this, hasCouponBean -> {
            if (hasCouponBean.isShowDialog()) {
                showRewardDialog(hasCouponBean.couponId);
            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new HomeAdapter(getContext());
        recyclerviewQuerstion.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (getContext() != null) {
                    outRect.bottom = UiUtlis.dp2px(getContext(), 10);
                }
            }
        });
        mAdapter.setOnItemClickListener(this);
        recyclerviewQuerstion.setAdapter(mAdapter);
        initListener();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.addData(analogData());
            mRefreshLayout.finishLoadMore();
            toast("加载完成");
        }, 1000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //加载轮播图
        homeFragmentViewModel.loadHomeBanner(this);
        homeFragmentViewModel.loadHomeNotice(this);
        homeVideoViewModel.loadHomeVideoList(this);

        //模拟数据
        postDelayed(() -> {
            mAdapter.clearData();
            mAdapter.setData(analogData());
            mRefreshLayout.finishRefresh();

            homeLiveView.setData(liveData());
            showComplete();
        }, 1000);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (homeNoticeView != null) {
            homeNoticeView.startFlipping();

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (homeNoticeView != null) {
            homeNoticeView.stopFlipping();
        }
    }


    private void showRewardDialog(final String couponId) {
        new BaseDialog.Builder(getActivity())
                .setContentView(R.layout.dialog_home_reward_view)
                .setAnimStyle(BaseDialog.ANIM_SCALE)
                .setOnClickListener(R.id.tv_yes_dialog, (BaseDialog.OnClickListener<View>) (dialog, view) -> {
                    sendHttpRecevice(couponId, dialog);
                })
                .setOnClickListener(R.id.iv_cancel_dialog, (BaseDialog.OnClickListener<View>) (dialog, view) -> {
                    dialog.dismiss();
                })
                .show();
    }

    private void sendHttpRecevice(String couponId, BaseDialog dialog) {
        showLoadDialog();
        homeFragmentViewModel.loadHomeHasCoupon(this, couponId).observe(this, aBoolean -> {
            hideLoadDialog();
            if (aBoolean) {
                dialog.dismiss();
            }
        });
    }

    private List<String> analogData() {
        List<String> objects = new ArrayList<>();
        objects.add("data:image/jpeg;base64,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");
        objects.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2879484891,1434073852&fm=15&gp=0.jpg");
        objects.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2879484891,1434073852&fm=15&gp=0.jpg");
        objects.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2879484891,1434073852&fm=15&gp=0.jpg");
        objects.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2879484891,1434073852&fm=15&gp=0.jpg");
        return objects;
    }

    private List<Object> liveData() {
        List<Object> objects = new ArrayList<>();
        objects.add(new Object());
        objects.add(new Object());
        return objects;
    }

    @Override
    public void onMoreClick() {
        startActivity(new Intent(getContext(), HomeVideoListActivity.class));
    }

    @Override
    public void onItemVideoClick(int index, HomeVideoListBean.DataBean item) {
        VideoPlayActivity.start(getAttachActivity(), item.url, item.title);
    }

    @Override
    public void onItemLiveClick(int index) {
        toast("直播" + index);
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        if (recyclerView == recyclerviewQuerstion) {
            startActivity(new Intent(getContext(), HomeQuestionDetails.class));
        }
    }

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hint_layout);
    }
}