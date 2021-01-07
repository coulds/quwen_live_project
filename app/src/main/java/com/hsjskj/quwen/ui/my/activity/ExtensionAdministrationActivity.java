package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;

/**
 * Administrator :ZB
 * 2021/1/7 0007
 * describe :
 **/
public class ExtensionAdministrationActivity extends MyActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, ExtensionAdministrationActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_extension_administration;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
