package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : sen
 * time          : 2020年12月31日 10:07
 * description   : quwen_live
 */
public class UserSetSexApi implements IRequestApi {
    private int value;
    @Override
    public String getApi() {
        return "User/setSex";
    }

    public UserSetSexApi setvalue(int value){
        this.value = value;
        return this;

    }


}
