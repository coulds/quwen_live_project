package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time          : 2020年12月28日 16:07
 * description   : quwen_live
 */
public class HomeBannerApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Home/banner";
    }

    public int mold;

    public HomeBannerApi(int mold) {
        this.mold = mold;
    }
}
