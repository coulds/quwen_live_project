package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class ReleaseLeftGetApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Dynamic/myLists";
    }

    private int limit, page;

    public ReleaseLeftGetApi(int limit, int page) {
        this.limit = limit;
        this.page = page;
    }
}
