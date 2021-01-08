package com.hsjskj.quwen.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.my.viewmodel.BankCardNetworkRequest;
import com.hsjskj.quwen.ui.my.viewmodel.RequestDataBackAddListener;

public class AddBankCardActivity extends MyActivity {
    EditText edt_username;
    EditText edt_number;
    EditText edt_bank_name;
    EditText edt_mark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return  R.layout.activity_add_banck_card;
    }

    @Override
    protected void initView() {
        edt_username = findViewById(R.id.edt_username);
        edt_number = findViewById(R.id.edt_number);
        edt_bank_name = findViewById(R.id.edt_bank_name);
        edt_mark = findViewById(R.id.edt_mark);
        setOnClickListener(R.id.btn_OK);
    }

    @Override
    protected void initData() {



    }

    @Override
    public void onClick(View v) {
                String mark="";
                String username = edt_username.getText().toString();
                String number = edt_number.getText().toString();
                String bank_name = edt_bank_name.getText().toString();
                mark = edt_mark.getText().toString();
        //初始化数据

        BankCardNetworkRequest bankCardNetworkRequest = new BankCardNetworkRequest();
        bankCardNetworkRequest.addBankCard(this,username,number,bank_name,mark, new RequestDataBackAddListener() {
            @Override
            public void onFinish(int responseData) {
                Toast.makeText(AddBankCardActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            }

            @Override
            public void onError(Exception e) {
                Log.e("AddBankCardActivity",e+"");
                Toast.makeText(AddBankCardActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
            }
        });


    }

}