package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.base.BaseAdapter;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.helper.KeyboardUtils;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.model.ButtonModel;
import com.hsjskj.quwen.ui.my.adapter.RechargeButtonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class AccountMoneyActivity extends MyActivity  {
    private RecyclerView reMoney;
    private ButtonModel buttonModel;
    private TextView tvContent;
    private TitleBar title;
    private RelativeLayout rlCoupon;
    private RechargeButtonAdapter rechargeButtonAdapter;
    private List<ButtonModel> list = new ArrayList<>();
    private RelativeLayout rlAli,rlWeChat;
    private int ALI=1;
    private int WE_CHAT=2;
    private int TYPE=1;
    private boolean isAli=true,isWeChat=false;
    private ImageView round1,round2;
    private List<ImageView> imageViewList =new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, AccountMoneyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_moeny;
    }

    @Override
    protected void initView() {
        rlAli =findViewById(R.id.rl_ali);
        tvContent = findViewById(R.id.tv_content);
        title = findViewById(R.id.title);
        round1= findViewById(R.id.round1);
        round2= findViewById(R.id.round2);
        SpannableStringBuilder spannable = new SpannableStringBuilder("充值即代表阅读并同意《用户充值协议》");
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#14DBE1")), 10, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new TextClick(), 10, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        tvContent.setText(spannable);
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                buttonModel = new ButtonModel(true);
            } else {
                buttonModel = new ButtonModel(false);
            }
            list.add(buttonModel);
        }

        reMoney = findViewById(R.id.re_money);
        rechargeButtonAdapter = new RechargeButtonAdapter(this, list);
        rechargeButtonAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                rechargeButtonAdapter.getList(position);
                KeyboardUtils.hideKeyboard(itemView);
            }
        });
        reMoney.setNestedScrollingEnabled(true);
        reMoney.setLayoutManager(new GridLayoutManager(getContext(),3));
        reMoney.setAdapter(rechargeButtonAdapter);
        title.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                Intent intent = new Intent(AccountMoneyActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });
        imageViewList.add(round1);
        imageViewList.add(round2);
        setOnClickListener(R.id.rl_ali, R.id.rl_we_chat, R.id.rl_coupon);
        getType(isAli,round1);
        getType(isWeChat,round2);
    }

    @Override
    protected void initData() {

    }

    private class TextClick extends ClickableSpan {

        @Override
        public void onClick(@NonNull View widget) {
            Log.d("TAG", "onClick: ");
            Toast.makeText(AccountMoneyActivity.this, "点击了这个", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_ali:
               isAli=true;
               isWeChat=false;
                getType(isAli,round1);
                getType(isWeChat,round2);
                break;
            case R.id.rl_we_chat:
                isAli=false;
                isWeChat=true;
                getType(isAli,round1);
                getType(isWeChat,round2);
                break;
            case R.id.rl_coupon:
                Intent intent = new Intent(AccountMoneyActivity.this, CouponActivity.class);
                startActivity(intent);
                break;
        }
    }
    private void getType(boolean type,ImageView imageView){
        if(type){
            GlideApp.with(getContext())
                    .load(R.drawable.round_select)
                    .into(imageView);
        }else {
            GlideApp.with(getContext())
                    .load(R.drawable.round)
                    .into(imageView);
        }

    }
}
