package com.hsjskj.quwen.ui.my.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hsjskj.quwen.http.response.CouponBean;

/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class MyCouponViewModel extends ViewModel {
    private MutableLiveData<CouponBean> couponBeanMutableLiveData;
    public MutableLiveData<CouponBean> getMyCouponLiveData() {
        if (couponBeanMutableLiveData == null) {
            couponBeanMutableLiveData = new MutableLiveData<>();
        }
        return couponBeanMutableLiveData;
    }
}
