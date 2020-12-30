package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time          : 2020年12月28日 16:07
 * description   : quwen_live
 */
public class HomeVideoListApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Home/videoLists";
    }

    public int limit;
    public int page;

    public HomeVideoListApi(int limit, int page) {
        this.limit = limit;
        this.page = page;
    }

    public HomeVideoListApi() {
        this.limit = 20;
        this.page = 1;
    }
}
