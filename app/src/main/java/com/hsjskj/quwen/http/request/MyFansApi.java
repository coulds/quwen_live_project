package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : sen
 * time          : 2021年01月07日 16:31
 * description   : quwen_live
 */
public class MyFansApi implements IRequestApi {
    @Override
    public String getApi() {
        return "User/fanslists";
    }
}
