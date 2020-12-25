package com.hsjskj.quwen.ui.home.fragment;

import android.widget.TextView;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月25日 15:46
 * description   : quwen_live
 */
public class ToAskFragment extends MyFragment<HomeActivity> {

    private TextView tv_device;

    public static ToAskFragment newInstance() {
        return new ToAskFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.to_ask_fragment;
    }

    @Override
    protected void initView() {
        tv_device = findViewById(R.id.tv_device);
    }

    @Override
    protected void initData() {
        XXPermissions.with(getActivity())
                .permission(Permission.CAMERA)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });
    }

}
