package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : sen
 * time          : 2020年12月31日 10:57
 * description   : 修改昵称
 */
public class UsersetNickApi implements IRequestApi {
    private String value;
    @Override
    public String getApi() {
        return "User/setNick";
    }

    public UsersetNickApi setvalue(String value){
        this.value = value;
        return this;

    }
}
