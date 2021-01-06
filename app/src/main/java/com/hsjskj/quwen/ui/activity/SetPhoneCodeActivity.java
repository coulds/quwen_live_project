package com.hsjskj.quwen.ui.activity;

import android.view.View;

import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.CountdownView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;

/**
 * @author : sen
 * time          : 2020年12月31日 16:51
 * description   : quwen_live
 */
public class SetPhoneCodeActivity extends MyActivity {
    private ClearEditText mclearEditText;
    private CountdownView countdownView;
    @Override
    protected int getLayoutId() {
        return R.layout.set_phonecode_activity;
    }

    @Override
    protected void initView() {
        mclearEditText = findViewById(R.id.sb_phone_code);
        countdownView = findViewById(R.id.send_phone_code);
        setOnClickListener(this.countdownView);


    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        String phonecoud_input = mclearEditText.getText().toString();
        if (v == countdownView){
            if (phonecoud_input.length() != 11){
                toast(R.string.common_phone_input_error);
                return;
            }else {
                toast(R.string.common_code_send_hint);
                countdownView.start();
                return;
            }


        }
    }
}
