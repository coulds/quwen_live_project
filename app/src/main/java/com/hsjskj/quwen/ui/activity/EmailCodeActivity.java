package com.hsjskj.quwen.ui.activity;

import android.view.View;

import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.CountdownView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;

/**
 * @author : sen
 * time          : 2020年12月31日 17:21
 * description   : quwen_live
 */
public class EmailCodeActivity extends MyActivity {
    private ClearEditText mclearEditText;
    private CountdownView mcountdownView;
    @Override
    protected int getLayoutId() {
        return R.layout.email_code_activity;
    }

    @Override
    protected void initView() {
        mclearEditText = findViewById(R.id.sb_email_code);
        mcountdownView = findViewById(R.id.send_code_email);
        setOnClickListener(this.mcountdownView);


    }

    @Override
    protected void initData() {


    }

    @Override
    public void onClick(View v) {
        String email_input = mclearEditText.getText().toString();
        if (v == mcountdownView){
            if (email_input.length() != 11){
                toast(R.string.common_phone_input_error);
                return;
            }else {
                toast(R.string.common_code_send_hint);
                mcountdownView.start();
                return;
            }


        }
    }
}
