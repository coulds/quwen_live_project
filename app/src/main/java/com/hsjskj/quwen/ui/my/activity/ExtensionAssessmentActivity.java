package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.ui.activity.ImageSelectActivity;
import com.hsjskj.quwen.ui.dialog.ExtensionDialog;
import com.hsjskj.quwen.ui.my.viewmodel.ExtensionAssessmentModel;
import com.hsjskj.quwen.upload.UploadListener;
import com.hsjskj.quwen.upload.UploadTxImpl;


import java.util.List;

/**
 * Administrator :ZB
 * 2021/1/7 0007
 * describe :
 **/
public class ExtensionAssessmentActivity extends MyMvvmActivity<ExtensionAssessmentModel> {

    private TitleBar title;
    private UploadListener listener;
    private String font;
    private String back;
    private EditText edName,edPhone,edCode;

    public static void start(Context context) {
        Intent intent = new Intent(context, ExtensionAssessmentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_extension_assessment;
    }

    @Override
    protected void initView() {
        super.initView();
        title = findViewById(R.id.title);
        edName=findViewById(R.id.ed_name);
        edPhone=findViewById(R.id.ed_phone);
        edCode=findViewById(R.id.ed_code);
        // 推广弹窗
        BaseDialog waitDialog = new ExtensionDialog.Builder(this)
                .show();

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
                // 等待对话框
                waitDialog.show();
            }
        });
        setOnClickListener(R.id.photo_left, R.id.photo_right, R.id.button);


    }

    @Override
    protected void onDestroy() {
        if (listener != null) {
            listener.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected void initData() {
        mViewModel.getTxCosLiveBean().observe(this, txCosBean -> {
            if (txCosBean != null) {
                listener = new UploadTxImpl(getContext(), txCosBean);
            }
        });
        loadTxCos();
    }

    private void loadTxCos() {
        mViewModel.loadTxCos(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photo_left:
                ImageSelectActivity.start(this, new ImageSelectActivity.OnPhotoSelectListener() {
                    @Override
                    public void onSelected(List<String> data) {
                        font = data.get(0);
                        GlideApp.with(getContext()).load(font).into(((ImageView) findViewById(R.id.photo_left)));

                    }
                });
                break;
            case R.id.photo_right:
                ImageSelectActivity.start(this, new ImageSelectActivity.OnPhotoSelectListener() {
                    @Override
                    public void onSelected(List<String> data) {
                        back = data.get(0);
                        GlideApp.with(getContext()).load(font).into(((ImageView) findViewById(R.id.photo_right)));
                    }
                });
                break;
            case R.id.button:
                if (listener == null) {
                    loadTxCos();
                    ToastUtils.show("获取上传cos失败");
                    return;
                }
                mViewModel.submitExtension(this, font, back, edName.getText().toString(), edPhone.getText().toString(), edCode.getText().toString(), listener).observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            finish();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
