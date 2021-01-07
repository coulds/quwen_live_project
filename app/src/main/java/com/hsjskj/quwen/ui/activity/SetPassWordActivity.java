package com.hsjskj.quwen.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hjq.widget.view.CountdownView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.helper.InputTextHelper;

/**
 * @author : sen
 * time          : 2021年01月04日 09:21
 * description   : quwen_live
 */
public class SetPassWordActivity extends MyActivity {

    private EditText mPhoneView;
    private EditText mCodeView;
    private CountdownView mCountdownView;
    private TextView mCommitView;

    @Override
    protected int getLayoutId() {
        return R.layout.set_password_activity;
    }

    @Override
    protected void initView() {

        mPhoneView = findViewById(R.id.et_password_forget_code1);
        mCommitView = findViewById(R.id.set_email);
        mCountdownView = findViewById(R.id.cv_password_forget_countdown1);



        setOnClickListener(mCountdownView, mCommitView);

        InputTextHelper.with(this)
                .addView(mPhoneView)
                .setMain(mCommitView)
                .build();

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

        if (v == mCountdownView) {
            if (mPhoneView.getText().toString().length() != 11) {
                toast(R.string.common_phone_input_error);
                return;
            }

            if (true) {
                toast(R.string.common_code_send_hint);
                mCountdownView.start();
                return;
            }
        }
    }
}
