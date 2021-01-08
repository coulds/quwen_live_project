package com.hsjskj.quwen.ui.my.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hsjskj.quwen.ui.my.fragment.CouponFragment;
import com.hsjskj.quwen.ui.my.fragment.CouponTwoFragment;
import com.hsjskj.quwen.ui.my.fragment.MyReleaseFragment;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class CouponPageAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;
    private String[] tag1 = {"1", "2", "3"};//优惠卷
    private String[] tag2 = {"1", "2"};//我的发布
    private String type;//1优惠券2我的发布

    public CouponPageAdapter(@NonNull FragmentManager fm, String type) {
        super(fm);
        if (type.equals("1")) {
            fragments = new Fragment[3];
        }
        if (type.equals("2")) {
            fragments = new Fragment[2];
        }
        this.type = type;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            if (type.equals("1")) {
                fragments[position] = CouponTwoFragment.getInstance(tag1[position]);
            } else if (type.equals("2")) {
                fragments[position] = MyReleaseFragment.getInstance(1);
            }

        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        if (type.equals("1")) {
            return tag1.length;
        } else if (type.equals("2")) {
            return tag2.length;
        }
        return 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (type.equals("1")) {
            return tag1[position];
        } else if (type.equals("2")) {
            return tag2[position];
        }
        return null;
    }
}
