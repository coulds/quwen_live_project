package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time   : 2019/12/07
 * desc   : 获取用户信息
 */
public final class UserInfoApi implements IRequestApi {

    @Override
    public String getApi() {
        return "User/userInfo";
    }


    public String touid;

    public UserInfoApi(String touid) {
        this.touid = touid;
    }
}