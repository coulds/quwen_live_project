package com.hsjskj.quwen.ui.activity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.other.AppConfig;
import com.hsjskj.quwen.ui.user.LoginActivity;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * @author : Jun
 * time   : 2020年12月24日10:58:14
 * desc   : 闪屏界面
 */
public final class SplashActivity extends MyActivity implements CancelAdapt {

    private View mDebugView;

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initView() {
        mDebugView = findViewById(R.id.iv_splash_debug);
    }

    @Override
    protected void initData() {
        if (AppConfig.isDebug()) {
            mDebugView.setVisibility(View.VISIBLE);
        } else {
            mDebugView.setVisibility(View.INVISIBLE);
        }

        if (!MyUserInfo.getInstance().isLogin()) {
            startActivity(LoginActivity.class);
            finish();
            return;
        } else {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    //没有网络请求验证token直接进首页
                    startActivity(HomeActivity.class);
                    finish();
                }
            }, 1000);
        }
        // 刷新用户信息
        //TODO 网络请求
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 隐藏状态栏和导航栏
                .hideBar(BarHide.FLAG_HIDE_BAR);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}