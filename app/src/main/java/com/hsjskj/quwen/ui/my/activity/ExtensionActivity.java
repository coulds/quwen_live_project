package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.king.zxing.util.CodeUtils;

import me.jessyan.autosize.internal.CustomAdapt;

/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class ExtensionActivity extends MyActivity implements CustomAdapt {
    private ImageView ivCode;

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
        ivCode = findViewById(R.id.iv_code);
    }

    @Override
    protected void initData() {
        ivCode.setImageBitmap(CodeUtils.createQRCode("it.qrCodeUrl", 142));
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 734;
    }
}
