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
import com.hsjskj.quwen.http.response.CouponBean;
import com.hsjskj.quwen.ui.my.activity.MoneyDetailedActivity;

/**
 * Administrator :ZB
 * 2021/1/7 0007
 * describe :
 **/
public class ExtensionAdministrationAdapter extends MyAdapter<String> {
    public ExtensionAdministrationAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private TextView line;
        private ViewHolder() {
            super(R.layout.item_extension_administration);
            line= (TextView) findViewById(R.id.line);

        }

        @Override
        public void onBindView(int position) {
        if(position==3-1){
            line.setVisibility(View.GONE);
        }else {
            line.setVisibility(View.VISIBLE);
        }

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
