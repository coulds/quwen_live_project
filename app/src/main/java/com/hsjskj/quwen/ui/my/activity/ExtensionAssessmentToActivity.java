package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;

/**
 * Administrator :ZB
 * 2021/1/8 0008
 * describe :
 **/
public class ExtensionAssessmentToActivity extends MyActivity {


    public static void start(Context context) {
        Intent intent = new Intent(context, ExtensionAssessmentToActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
       return R.layout.activity_extension_assessment_to;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
