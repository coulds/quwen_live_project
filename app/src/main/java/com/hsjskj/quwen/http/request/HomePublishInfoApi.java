package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time          : 2020年12月30日14:40:17
 * description   : quwen_live
 */
public class HomePublishInfoApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Dynamic/info";
    }

    public String id;

    public HomePublishInfoApi(String id) {
        this.id = id;
    }
}
