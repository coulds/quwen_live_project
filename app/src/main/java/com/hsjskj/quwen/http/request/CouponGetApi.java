package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class CouponGetApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Coupon/myLists";
    }
    public String mold;
    public int limit;
    public int page;


    public CouponGetApi(String mold, int limit, int page) {
        this.mold = mold;
        this.limit = limit;
        this.page = page;
    }
}
