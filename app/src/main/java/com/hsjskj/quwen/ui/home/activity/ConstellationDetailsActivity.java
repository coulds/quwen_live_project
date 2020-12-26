package com.hsjskj.quwen.ui.home.activity;

import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.activity.BrowserActivity;
import com.hsjskj.quwen.ui.home.widget.StarTagView;
import com.hsjskj.quwen.widget.BrowserView;

/**
 * @author : Jun
 * time          : 2020年12月25日 17:24
 * description   : quwen_live
 */
public class ConstellationDetailsActivity extends MyActivity {

    private AppCompatTextView mTvTemp1;
    private AppCompatImageView mIvSex;
    private AppCompatImageView mIvIcon2;
    private AppCompatTextView mTvTime;
    private StarTagView mStarTag;
    private FrameLayout mLayoutWeb;

    private BrowserView mBrowserView;

    @Override
    protected int getLayoutId() {
        return R.layout.constellation_details_activity;
    }

    @Override
    protected void initView() {

        mTvTemp1 = (AppCompatTextView) findViewById(R.id.tv_temp_1);
        mIvSex = (AppCompatImageView) findViewById(R.id.iv_sex);
        mIvIcon2 = (AppCompatImageView) findViewById(R.id.iv_icon_2);
        mTvTime = (AppCompatTextView) findViewById(R.id.tv_time);
        mStarTag = (StarTagView) findViewById(R.id.star_tag);
        mLayoutWeb = (FrameLayout) findViewById(R.id.layout_web);

        mBrowserView = new BrowserView(getContext());
        mBrowserView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mLayoutWeb.addView(mBrowserView);

    }

    @Override
    protected void initData() {
        mBrowserView.loadUrl("https://www.baidu.com");

    }

    @Override
    protected void onResume() {
        mBrowserView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mBrowserView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBrowserView.onDestroy();
        super.onDestroy();
    }
}
