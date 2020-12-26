package com.hsjskj.quwen.ui.home.fragment;

import android.view.View;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;


/**
 * @author : Jun
 * time   : 2020年12月26日13:05:25
 * desc   : 项目框架使用示例
 */
public final class MessageFragment extends MyFragment<HomeActivity> {


    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}