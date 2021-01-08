package com.hsjskj.quwen.ui.my.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.ui.my.object.BankCard;



public class BankCardAdapter extends MyAdapter<BankCard.DataBean> {

    public BankCardAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {
        TextView tv_bankAccount;
        TextView tv_bankCardNumber;
        TextView tv_bankCardWrite;

        private ViewHolder() {
            super(R.layout.bankcard_item);
            tv_bankAccount = (TextView) findViewById(R.id.tv_bankAccount);
            tv_bankCardNumber = (TextView) findViewById(R.id.tv_bankCardNumber);
            tv_bankCardWrite = (TextView) findViewById(R.id.tv_bankCardWrite);
        }

        @Override
        public void onBindView(int position) {
            BankCard.DataBean bankCard = getItem(position);
            Log.e("BankCardAdapter", "开户银行=" + bankCard.account_info.bank_name);
            tv_bankAccount.setText(bankCard.account_info.bank_name);
            tv_bankCardNumber.setText(bankCard.account_info.number);
        }
    }
}

