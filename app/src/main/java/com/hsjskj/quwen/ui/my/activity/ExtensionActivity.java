package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
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
    private TitleBar title;

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
        title=findViewById(R.id.title);
        title.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                ExtensionAssessmentActivity.start(ExtensionActivity.this);
            }
        });
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
