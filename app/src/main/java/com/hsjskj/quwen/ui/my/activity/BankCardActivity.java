package com.hsjskj.quwen.ui.my.activity;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.my.adapter.BankCardAdapter;
import com.hsjskj.quwen.ui.my.object.BankCard;

import java.util.ArrayList;
import java.util.List;

public class BankCardActivity extends MyActivity {
    RecyclerView rcv_bankCard;
    List<BankCard> bankCards;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank;
    }

    @Override
    protected void initView() {
        rcv_bankCard=findViewById(R.id.rcv_bankCard);

    }

    @Override
    protected void initData() {

        bankCards=new ArrayList<>();
        BankCard bankCard01=new BankCard("01","小明","7232","中国建设银行");
        bankCards.add(bankCard01);
        BankCard bankCard02=new BankCard("02","小王","0852","中国农业银行");
        bankCards.add(bankCard02);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        rcv_bankCard.setLayoutManager(linearLayoutManager);
        BankCardAdapter bankCardAdapter=new BankCardAdapter(this,bankCards);
        rcv_bankCard.setAdapter(bankCardAdapter);
    }



    @Override
    public void onRightClick(View v) {
        startActivity(AddBankCardActivity.class);
    }
}