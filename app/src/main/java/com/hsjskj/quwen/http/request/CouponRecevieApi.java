package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time          : 2020年12月28日 16:07
 * description   : quwen_live
 */
public class CouponRecevieApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Coupon/receive";
    }

    public String id;

    public CouponRecevieApi(String id) {
        this.id = id;
    }
}
