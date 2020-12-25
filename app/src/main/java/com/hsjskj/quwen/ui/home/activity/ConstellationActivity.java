package com.hsjskj.quwen.ui.home.activity;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.home.widget.HomeStarSelectDateView;

/**
 * @author : Jun
 * time          : 2020年12月25日 16:15
 * description   : 首页星座查询
 */
public class ConstellationActivity extends MyActivity {
    private FrameLayout mFlClick1;
    private AppCompatImageView mIvMale1;
    private AppCompatImageView mIvMale2;
    private FrameLayout mFlClick2;
    private AppCompatImageView mIvFemale1;
    private AppCompatImageView mIvFemale2;
    private HomeStarSelectDateView mStarDateSelect;
    private boolean isSelectMale = true;

    @Override
    protected int getLayoutId() {
        return R.layout.constellation_activity;
    }

    @Override
    protected void initView() {
        mFlClick1 = (FrameLayout) findViewById(R.id.fl_click_1);
        mIvMale1 = (AppCompatImageView) findViewById(R.id.iv_male_1);
        mIvMale2 = (AppCompatImageView) findViewById(R.id.iv_male_2);
        mFlClick2 = (FrameLayout) findViewById(R.id.fl_click_2);
        mIvFemale1 = (AppCompatImageView) findViewById(R.id.iv_female_1);
        mIvFemale2 = (AppCompatImageView) findViewById(R.id.iv_female_2);
        mStarDateSelect = (HomeStarSelectDateView) findViewById(R.id.star_date_select);

        setOnClickListener(mFlClick1, mFlClick2);
        setOnClickListener(R.id.btn_submit);
    }

    @Override
    protected void initData() {
        ungradeSexView();
    }

    @Override
    public void onClick(View v) {
        if (v == mFlClick1) {
            isSelectMale = true;
            ungradeSexView();
        } else if (v == mFlClick2) {
            isSelectMale = false;
            ungradeSexView();
        } else if (v.getId() == R.id.btn_submit) {
            if (mStarDateSelect.getYear() == 0) {
                ToastUtils.show(R.string.home_please_select_a_date);
                return;
            }
            startActivity(new Intent(this, ConstellationDetailsActivity.class));
        }
    }

    private void ungradeSexView() {
        if (isSelectMale) {
            mIvMale2.setVisibility(View.VISIBLE);
            mIvFemale2.setVisibility(View.GONE);
            mIvMale1.setAlpha(1.0f);
            mIvFemale1.setAlpha(0.6f);
        } else {
            mIvMale2.setVisibility(View.GONE);
            mIvFemale2.setVisibility(View.VISIBLE);
            mIvMale1.setAlpha(0.6f);
            mIvFemale1.setAlpha(1.0f);
        }
    }
}
