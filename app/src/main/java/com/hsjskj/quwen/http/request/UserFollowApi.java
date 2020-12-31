package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time   : 2019/12/07
 * desc   : 关注/取关
 */
public final class UserFollowApi implements IRequestApi {

    @Override
    public String getApi() {
        return "User/follow";
    }


    public String touid;

    public UserFollowApi(String touid) {
        this.touid = touid;
    }
}