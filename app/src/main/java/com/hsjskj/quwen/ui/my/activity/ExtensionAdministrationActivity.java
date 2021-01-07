package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.my.adapter.ExtensionAdministrationAdapter;

/**
 * Administrator :ZB
 * 2021/1/7 0007
 * describe :
 **/
public class ExtensionAdministrationActivity extends MyActivity {
    private ExtensionAdministrationAdapter extensionAdministrationAdapterTop;
    private ExtensionAdministrationAdapter extensionAdministrationAdapterBottom;
    private RecyclerView reDay,reMonth;
    public static void start(Context context) {
        Intent intent = new Intent(context, ExtensionAdministrationActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_extension_administration;
    }

    @Override
    protected void initView() {
        reDay =findViewById(R.id.re_day);
        reMonth=findViewById(R.id.re_month);
        extensionAdministrationAdapterTop= new ExtensionAdministrationAdapter(this);
        reDay.setLayoutManager(new LinearLayoutManager(this));
        reDay.setAdapter(extensionAdministrationAdapterTop);
        extensionAdministrationAdapterBottom=new ExtensionAdministrationAdapter(this);
        reMonth.setLayoutManager(new LinearLayoutManager(this));
        reMonth.setAdapter(extensionAdministrationAdapterBottom);


    }

    @Override
    protected void initData() {

    }
}
