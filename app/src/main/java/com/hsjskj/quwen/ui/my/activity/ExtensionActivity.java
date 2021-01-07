package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;

import me.jessyan.autosize.internal.CustomAdapt;

/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class ExtensionActivity extends MyActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, ExtensionActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_extension;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

}
