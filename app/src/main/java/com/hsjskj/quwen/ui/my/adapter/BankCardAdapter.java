package com.hsjskj.quwen.ui.my.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.ui.my.activity.AddBankCardActivity;
import com.hsjskj.quwen.ui.my.object.BankCard;

import java.util.ArrayList;
import java.util.List;

public class BankCardAdapter extends RecyclerView.Adapter<BankCardAdapter.BankCardViewHolder> {
    Context context;
    List<BankCard.DataBean> bankCards;

    public BankCardAdapter(Context context, List<BankCard.DataBean> bankCards) {
        this.context = context;
        this.bankCards = bankCards;
    }

    @NonNull
    @Override
    public BankCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        BankCardViewHolder bankCardViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.bankcard_item, parent, false);
            bankCardViewHolder = new BankCardViewHolder(view);
            view.setTag(bankCardViewHolder);
        } else {
            bankCardViewHolder = (BankCardViewHolder) view.getTag();
        }
        bankCardViewHolder.tv_bankCardWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), AddBankCardActivity.class);
                context.startActivity(intent);
            }
        });
        return bankCardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BankCardViewHolder holder, int position) {
        BankCard.DataBean bankCard = bankCards.get(position);
        Log.e("BankCardAdapter", "开户银行="+bankCard.account_info.bank_name);
        holder.tv_bankAccount.setText(bankCard.account_info.bank_name);
        holder.tv_bankCardNumber.setText(bankCard.account_info.number);
    }

    @Override
    public int getItemCount() {
        Log.e("BankCardAdapter", String.valueOf(bankCards.size()));
        return bankCards.size();
    }


    class BankCardViewHolder extends RecyclerView.ViewHolder {
        TextView tv_bankAccount;
        TextView tv_bankCardNumber;
        TextView tv_bankCardWrite;

        public BankCardViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bankAccount = itemView.findViewById(R.id.tv_bankAccount);
            tv_bankCardNumber = itemView.findViewById(R.id.tv_bankCardNumber);
            tv_bankCardWrite = itemView.findViewById(R.id.tv_bankCardWrite);
        }
    }
}
