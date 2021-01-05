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
import com.hsjskj.quwen.ui.activity.MoneyDetailedActivity;
import com.hsjskj.quwen.ui.adapter.AccountBalanceAdapter;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponAdapter extends MyAdapter<String> {
    public CouponAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private ViewHolder() {
            super(R.layout.item_coupon);
        }

        @Override
        public void onBindView(int position) {


        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }

}
