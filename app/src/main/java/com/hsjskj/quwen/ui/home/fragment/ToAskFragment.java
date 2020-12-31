package com.hsjskj.quwen.ui.home.fragment;

import android.app.Application;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.helper.ActivityStackManager;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;
import com.hsjskj.quwen.ui.user.activity.LoginActivity;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月25日 15:46
 * description   : quwen_live
 */
public class ToAskFragment extends MyFragment<HomeActivity> {


    public static ToAskFragment newInstance() {
        return new ToAskFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.to_ask_fragment;
    }

    @Override
    protected void initView() {
        setOnClickListener(R.id.login_out);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_out) {
            Application application = ActivityStackManager.getInstance().getApplication();
            Intent intent = new Intent(application, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            application.startActivity(intent);
            // 销毁除了登录页之外的界面
            MyUserInfo.getInstance().clearUserInfo();
            ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
        }
    }
}
