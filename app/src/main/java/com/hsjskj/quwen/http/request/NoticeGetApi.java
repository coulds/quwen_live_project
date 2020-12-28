package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time   :2020年12月28日13:42:36
 * desc   : 协议获取
 */
public final class NoticeGetApi implements IRequestApi {

    @Override
    public String getApi() {
        return "Home/noticeInfo";
    }

    private String id;

    public NoticeGetApi setId(String id) {
        this.id = id;
        return this;
    }
}