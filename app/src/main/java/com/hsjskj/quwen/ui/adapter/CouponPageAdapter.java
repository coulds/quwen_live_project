package com.hsjskj.quwen.ui.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hsjskj.quwen.ui.my.fragment.CouponFragment;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponPageAdapter extends FragmentPagerAdapter {
    private final Fragment[] fragments;
    private String[] tag = {"1","2","3"};//优惠卷
    public CouponPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments = new Fragment[3];
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            fragments[position] = CouponFragment.getInstance(tag[position]);
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return tag.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tag[position];
    }
}
