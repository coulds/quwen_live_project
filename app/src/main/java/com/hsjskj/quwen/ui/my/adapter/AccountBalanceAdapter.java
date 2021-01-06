package com.hsjskj.quwen.ui.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.ui.my.activity.MoneyDetailedActivity;

/**
 * Administrator :ZB
 * 2021/1/4 0004
 * describe :
 **/
public class AccountBalanceAdapter extends MyAdapter<String> {
    private Context context;
    private int type;//1余额账户记录2//现金账户

    public AccountBalanceAdapter(@NonNull Context context, int type) {
        super(context);
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private TextView tvType, tvTime, tvMoney;
        private RelativeLayout rlItem;


        private ViewHolder() {
            super(R.layout.item_capital_details);
            tvType = (TextView) findViewById(R.id.type);
            tvTime = (TextView) findViewById(R.id.tv_time);
            tvMoney = (TextView) findViewById(R.id.money);
            rlItem = (RelativeLayout) findViewById(R.id.rl_item);
        }

        @Override
        public void onBindView(int position) {
            if (type == 2) {
                rlItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MoneyDetailedActivity.class);
                        context.startActivity(intent);
                    }
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
