package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time          : 2020年12月30日14:40:17
 * description   : quwen_live
 */
public class HomePublishListApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Dynamic/lists";
    }

    public int limit;
    public int page ;

    public HomePublishListApi(int limit,int page) {
        this.limit = limit;
        this.page = page;
    }
}
