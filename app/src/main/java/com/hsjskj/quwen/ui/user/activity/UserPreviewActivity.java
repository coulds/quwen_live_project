package com.hsjskj.quwen.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.aop.CheckNet;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.http.response.UserInfoBean;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.ui.user.viewmodel.UserPreviewViewModel;
import com.hsjskj.quwen.widget.HintLayout;

/**
 * @author : Jun
 * time          : 2020年12月26日 15:17
 * description   : 用户信息 这里区分主播和非主播身份
 */
public class UserPreviewActivity extends MyMvvmActivity<UserPreviewViewModel> implements StatusAction {

    private NestedScrollView scrollView;
    private TextView tvFollow;
    private int height = 80;

    //当前是否已关注
    private boolean isFollow = false;

    public static void start(Context context, String userId) {
        Intent intent = new Intent(context, UserPreviewActivity.class);
        intent.putExtra(IntentKey.USER_ID, userId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_user;
    }

    @Override
    protected void initView() {
        super.initView();
        initViewScrollView();
        tvFollow = findViewById(R.id.tv_follow);
        setOnClickListener(tvFollow);
    }

    @CheckNet
    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == tvFollow) {
            mViewModel.loadFollowUserInfoLiveData(this, getString(IntentKey.USER_ID));
        }
    }

    public void getHttpData() {
        mViewModel.loadUserInfoLiveData(this, getString(IntentKey.USER_ID));
    }

    @Override
    protected void initData() {
        mViewModel.getFollowUserInfoLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                isFollow = !isFollow;
            }
            upgradeStatusFollow();
        });
        mViewModel.getUserInfoLiveData().observe(this, userInfoBean -> {
            if (userInfoBean != null) {
                showComplete();
                upgradeUserInfo(userInfoBean);
            } else {
                showError(v -> getHttpData());
            }
        });
        showLoading();
        getHttpData();
    }

    private void upgradeUserInfo(UserInfoBean userInfoBean) {
        //TODO 用户资料显示
    }

    private void upgradeStatusFollow() {
        if (isFollow) {
            tvFollow.setText("取消关注");
        } else {
            tvFollow.setText("+关注");
        }
    }

    private void initViewScrollView() {
        scrollView = findViewById(R.id.nestedScrollView);
        scrollView.post(() -> {
            if (getTitleBar() != null) {
                height = getTitleBar().getHeight();
            }
        });
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY > (height / 2)) {
                setLeftIcon(R.drawable.arrows_left_ic);
            } else {
                setLeftIcon(R.drawable.arrow_back_fff);
            }
            if (scrollY >= height) {
                setTitleBarColor(Color.WHITE);
            } else if (scrollY >= 0) {
                float persent = scrollY * 1f / (height);
                int alpha = (int) (255 * persent);
                int color = Color.argb(alpha, 255, 255, 255);
                setTitleBarColor(color);
            }
        });
    }

    public void setTitleBarColor(int color) {
        if (getTitleBar() != null) {
            getTitleBar().setBackgroundColor(color);
        }
    }

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hint_layout);
    }
}
