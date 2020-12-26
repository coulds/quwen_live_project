package com.hsjskj.quwen.ui.home.fragment;

import android.view.View;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;
import com.hsjskj.quwen.ui.user.LoginActivity;
import com.hsjskj.quwen.ui.activity.SettingActivity;

/**
 *    @author :Jun
 *    time   : 2020年12月26日13:05:16
 *    desc   : 项目界面跳转示例
 */
public final class MeFragment extends MyFragment<HomeActivity> {

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.me_fragment;
    }

    @Override
    protected void initView() {
        setOnClickListener(R.id.btn_me_login,R.id.btn_message_setting);
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_me_login:
                startActivity(LoginActivity.class);
                break;
            case R.id.btn_message_setting:
                startActivity(SettingActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}