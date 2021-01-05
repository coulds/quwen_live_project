package com.hsjskj.quwen.ui.my.activity;

import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.ui.adapter.CouponPageAdapter;
import com.hsjskj.quwen.widget.TabEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponActivity extends MyActivity {
    private ViewPager vpCoupon;
    private CommonTabLayout tabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private List<String> mTitles=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initView() {
        vpCoupon=findViewById(R.id.vp_coupon);
        tabLayout=findViewById(R.id.my_tab_layout);
        mTitles.add("未使用");
        mTitles.add("已使用");
        mTitles.add("已过期");
        for (String mTitle : mTitles) {
            mTabEntities.add(new TabEntity(mTitle, 0, 0));
        }
        tabLayout.setTabData(mTabEntities);
        vpCoupon.setAdapter(new CouponPageAdapter(getSupportFragmentManager()));
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
