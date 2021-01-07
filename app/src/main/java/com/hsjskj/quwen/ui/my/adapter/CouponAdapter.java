package com.hsjskj.quwen.ui.my.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.response.CouponBean;

import static android.content.ContentValues.TAG;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponAdapter extends MyAdapter<CouponBean.DataBean> {
    private String mold;//1未使用，2已使用，3已过期

    public CouponAdapter(@NonNull Context context, String mold) {
        super(context);
        this.mold = mold;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {
        private TextView amount, content, time;
        private RelativeLayout item;
        private ViewHolder() {
            super(R.layout.item_coupon);
            amount = (TextView) findViewById(R.id.amount);
            content = (TextView) findViewById(R.id.content);
            time = (TextView) findViewById(R.id.time);
            item= (RelativeLayout) findViewById(R.id.item);
        }

        @Override
        public void onBindView(int position) {
            if(mold.equals("1")){
                item.setBackgroundResource(R.drawable.item_coupon);
            }else {
                item.setBackgroundResource(R.drawable.item_un_coupon);
            }
            CouponBean.DataBean item = getItem(position);
            amount.setText(item.getAmount());
            content.setText(item.getContent());
            time.setText(item.getEnd_time());
        }
    }


}
