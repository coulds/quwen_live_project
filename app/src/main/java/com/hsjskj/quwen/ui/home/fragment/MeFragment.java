package com.hsjskj.quwen.ui.home.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyFragment;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.response.UserInfoBean;
import com.hsjskj.quwen.ui.activity.MyConcernActivity;
import com.hsjskj.quwen.ui.my.activity.AccountBalanceActivity;
import com.hsjskj.quwen.ui.my.activity.AccountMoneyActivity;
import com.hsjskj.quwen.ui.activity.MyFansActivity;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;
import com.hsjskj.quwen.ui.activity.SettingActivity;
import com.hsjskj.quwen.ui.my.activity.CouponActivity;
import com.hsjskj.quwen.ui.my.activity.ExtensionActivity;
import com.hsjskj.quwen.ui.my.activity.ExtensionAdministrationActivity;
import com.hsjskj.quwen.ui.my.activity.ExtensionAssessmentActivity;
import com.hsjskj.quwen.ui.my.activity.ExtensionAssessmentToActivity;
import com.hsjskj.quwen.ui.my.activity.MyReleaseActivity;
import com.hsjskj.quwen.ui.user.activity.UserPreviewActivity;
import com.hsjskj.quwen.ui.user.repositioy.UserPreviewRepository;

/**
 * @author :Jun
 * time   : 2020年12月26日13:05:16
 * desc   : 项目界面跳转示例
 */
public final class MeFragment extends MyFragment<HomeActivity> {

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.me_fragment;
    }

    @Override
    protected void initView() {
        setOnClickListener(R.id.setting_btn, R.id.touxiang, R.id.fs_layout, R.id.yu_er_layout, R.id.xian_jin_layout, R.id.fa_bu_layout, R.id.youhuijuan_layout, R.id.wo_tuiguang_layout, R.id.tuiguang_layout, R.id.gz_layout, R.id.kaohe_layout);

    }

    @Override
    protected void initData() {
        UserPreviewRepository.getCurrentUserInfoLiveData().observe(this, this::setUserInfoView);
        UserInfoBean login = MyUserInfo.getInstance().getLogin();
        setUserInfoView(login);
    }

    private void setUserInfoView(UserInfoBean userInfoView) {
        GlideApp.with(this)
                .load(userInfoView.avatar)
                .error(R.drawable.avatar_placeholder_ic)
                .circleCrop()
                .into(((ImageView) findViewById(R.id.touxiang)));

        ((TextView) findViewById(R.id.textView)).setText(userInfoView.getUsername());
        ((TextView) findViewById(R.id.gz_number)).setText("" + userInfoView.i_attention_count);
        ((TextView) findViewById(R.id.fs_number)).setText("" + userInfoView.attention_to_me_count);
        ((TextView) findViewById(R.id.jyz_number)).setText("" + userInfoView.exp);
        ((TextView) findViewById(R.id.textView2)).setText("Lv" + userInfoView.level);
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fs_layout) {
            startActivity(MyFansActivity.class);
        } else if (id == R.id.setting_btn) {
            startActivity(SettingActivity.class);
        } else if (id == R.id.touxiang) {
            UserPreviewActivity.start(getContext(), MyUserInfo.getInstance().getId());
        } else if (id == R.id.xian_jin_layout) {
            //现金账户
            AccountBalanceActivity.start(getContext());
        } else if (id == R.id.yu_er_layout) {
            AccountMoneyActivity.start(getContext());
        } else if (id == R.id.fa_bu_layout) {
            MyReleaseActivity.start(getContext());
        } else if (id == R.id.youhuijuan_layout) {
            Intent intent = new Intent(getContext(), CouponActivity.class);
            startActivity(intent);
        } else if (id == R.id.wo_tuiguang_layout) {
            ExtensionActivity.start(getContext());
        } else if (id == R.id.tuiguang_layout) {
            ExtensionAdministrationActivity.start(getContext());
        } else if (id == R.id.gz_layout) {
            startActivity(MyConcernActivity.class);
        } else if (id == R.id.kaohe_layout) {
            boolean promoterStatus = MyUserInfo.getInstance().getLogin().isPromoterStatus();
            if (promoterStatus) {
                //已经成为推广员
                ExtensionAssessmentToActivity.start(getContext());
            } else {
                //还没有成为推广员
                ExtensionAssessmentActivity.start(getContext());
            }
        }

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }
}