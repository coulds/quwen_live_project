package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * Administrator :ZB
 * 2021/1/8 0008
 * describe :
 **/
public class MyReleasePostApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Dynamic/del";
    }

    private String id;

    public MyReleasePostApi(String id) {
        this.id = id;
    }
}
