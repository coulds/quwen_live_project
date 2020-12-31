package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : sen
 * time   :2020年12月31日09:21:09
 * desc   : 修改性别
 */
public final class UserSetBirthApi implements IRequestApi {

    @Override
    public String getApi() {
        return "User/setBirth";
    }

    private String value;

    public UserSetBirthApi setValue(String value) {
        this.value = value;
        return this;
    }
}