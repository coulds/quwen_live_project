package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : sen
 * time          : 2020年12月31日 14:04
 * description   : quwen_live
 */
public class UserSetAvatarApi implements IRequestApi {
    private String value=
            "https://dss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3107090837,3877532430&fm=115&gp=0.jpg";
    @Override
    public String getApi() {
        return "User/setAvatar";
    }

    public UserSetAvatarApi setvalue(String value){
        this.value = value;
        return this;
    }
}
