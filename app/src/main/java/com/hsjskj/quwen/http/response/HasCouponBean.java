package com.hsjskj.quwen.http.response;

import android.text.TextUtils;

/**
 * @author : Jun
 * time          : 2020年12月28日 17:23
 * description   : quwen_live
 */
public class HasCouponBean {
    public String couponId;//可空

    public HasCouponBean() {
    }

    public boolean isShowDialog() {
        return couponId != null && !TextUtils.isEmpty(couponId);
    }
}
