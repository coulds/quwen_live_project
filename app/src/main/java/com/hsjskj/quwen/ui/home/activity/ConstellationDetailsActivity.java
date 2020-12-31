package com.hsjskj.quwen.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.helper.HtmlHelper;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.ui.home.viewmodel.ConstellationViewModel;
import com.hsjskj.quwen.ui.home.widget.StarTagView;
import com.hsjskj.quwen.widget.BrowserView;
import com.hsjskj.quwen.widget.HintLayout;

/**
 * @author : Jun
 * time          : 2020年12月25日 17:24
 * description   : quwen_live
 */
public class ConstellationDetailsActivity extends MyActivity implements StatusAction {

    private AppCompatTextView mTvTemp1;
    private AppCompatImageView mIvSex;
    private AppCompatImageView mIvIcon2;
    private AppCompatTextView mTvTime;
    private StarTagView mStarTag;
    private FrameLayout mLayoutWeb;

    private BrowserView mBrowserView;
    private ConstellationViewModel mViewModel;


    public static void start(Context context, int sex, String birthday) {
        Intent intent = new Intent(context, ConstellationDetailsActivity.class);
        intent.putExtra(IntentKey.SEX, sex);
        intent.putExtra(IntentKey.BIRTHDAY, birthday);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.constellation_details_activity;
    }

    @Override
    protected void initView() {
        mViewModel = new ViewModelProvider(this).get(ConstellationViewModel.class);
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
        mViewModel.getLiveDataDetails().observe(this, o -> {
            if (o != null) {
                showComplete();
                mStarTag.setTagText(o.name, getInt(IntentKey.SEX) == 2,true);
                mBrowserView.loadDataWithBaseURL("", HtmlHelper.addHtml(o.fortune), "text/html", "UTF-8", "");
            } else {
                showError(v -> loadHttp());
            }
        });

        //初始化显示数据
        //是否是一个男的
        boolean b = getInt(IntentKey.SEX) == 2;
        mIvSex.setImageResource(b ? R.drawable.star_male : R.drawable.star_female);
        mStarTag.setTagText("", b,true);
        mTvTime.setText(getString(IntentKey.BIRTHDAY));

        showLoading(R.string.home_constellation_calculation);
        loadHttp();
    }

    private void loadHttp() {
        mViewModel.loadHomeQuestion(this, getInt(IntentKey.SEX), getString(IntentKey.BIRTHDAY));
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

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hint_layout);
    }
}
