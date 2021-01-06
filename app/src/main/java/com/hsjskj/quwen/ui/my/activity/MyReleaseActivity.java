package com.hsjskj.quwen.ui.my.activity;

import android.content.Context;
import android.content.Intent;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hjq.bar.TitleBar;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.my.adapter.CouponPageAdapter;
import com.hsjskj.quwen.widget.TabEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class MyReleaseActivity extends MyActivity {
    private TitleBar title;
    private ViewPager vpCoupon;
    private CommonTabLayout tabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> mTitles=new ArrayList<>();
    public static void start(Context context) {
        Intent intent = new Intent(context, MyReleaseActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initView() {
        title=findViewById(R.id.title);
        title.setTitle("我的发布");
        vpCoupon=findViewById(R.id.vp_coupon);
        tabLayout=findViewById(R.id.my_tab_layout);
        mTitles.add("去问");
        mTitles.add("问问");
        for (String mTitle : mTitles) {
            mTabEntities.add(new TabEntity(mTitle, 0, 0));
        }
        tabLayout.setTabData(mTabEntities);
        vpCoupon.setAdapter(new CouponPageAdapter(getSupportFragmentManager(),"2"));
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpCoupon.setCurrentItem(position, true);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        vpCoupon.setCurrentItem(0);
        vpCoupon.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
