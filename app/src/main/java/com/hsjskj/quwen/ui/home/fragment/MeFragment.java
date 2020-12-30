package com.hsjskj.quwen.ui.home.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.ui.activity.MyFansActivity;
import com.hsjskj.quwen.ui.activity.PersonalDataActivity;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;
import com.hsjskj.quwen.ui.user.activity.LoginActivity;
import com.hsjskj.quwen.ui.activity.SettingActivity;

/**
 * @author :Jun
 * time   : 2020年12月26日13:05:16
 * desc   : 项目界面跳转示例
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
        setOnClickListener(R.id.setting_btn, R.id.touxiang, R.id.fs_layout);

    }

    @Override
    protected void initData() {
        GlideApp.with(getContext()).load(MyUserInfo.getInstance().getLogin().avatar).into((ImageView) findViewById(R.id.touxiang));
        ((TextView) findViewById(R.id.textView)).setText(MyUserInfo.getInstance().getLogin().getUsername());

    }

    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fs_layout) {
            startActivity(MyFansActivity.class);
        } else if (id == R.id.setting_btn) {
            startActivity(SettingActivity.class);
        } else if (id == R.id.touxiang) {
            startActivity(PersonalDataActivity.class);
        }

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}