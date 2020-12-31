package com.hsjskj.quwen.ui.home.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
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
import com.hsjskj.quwen.action.StatusTwoAction;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.http.response.HomeVideoListBean;
import com.hsjskj.quwen.other.IntentKey;
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
        , StatusAction{

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
            showComplete();
            homeBannerView.setBannerPic(bannerBeans);
        });
        homeFragmentViewModel.getHomeNoticeLiveData().observe(this, noticeBean -> {
            homeNoticeView.setNotices(Collections.singletonList(noticeBean));
        });
        homeFragmentViewModel.getHomeVideoLiveData().observe(this, dataBeans -> {
            mRefreshLayout.finishLoadMore();
            mRefreshLayout.finishRefresh();
            if (mAdapter.getPageNumber() == 1 && dataBeans != null) {
                mAdapter.setData(dataBeans);
            } else {
                if (dataBeans == null || dataBeans.isEmpty()) {
                    mRefreshLayout.finishLoadMoreWithNoMoreData();
                }
                mAdapter.addData(dataBeans);
            }
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
        mAdapter.setPageNumber(mAdapter.getPageNumber() + 1);
        homeFragmentViewModel.loadHomePublishList(this, mAdapter.getPageNumber(), 20);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //加载轮播图
        mAdapter.setPageNumber(1);
        homeFragmentViewModel.loadHomeBanner(this);
        homeFragmentViewModel.loadHomeNotice(this);
        homeVideoViewModel.loadHomeVideoList(this);
        homeFragmentViewModel.loadHomePublishList(this);
        //模拟数据 直播数据
        postDelayed(() -> {
            homeLiveView.setData(liveData());
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
            startActivity(new Intent(getContext(), HomeQuestionDetails.class)
                    .putExtra(IntentKey.ID, mAdapter.getItem(position).id)
            );
        }
    }

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hint_layout);
    }

}