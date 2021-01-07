package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : sen
 * time          : 2021年01月07日 16:31
 * description   : quwen_live
 */
public class MyFansApi implements IRequestApi {
    public String limit;
    public  int page;
    @Override
    public String getApi() {
        return "User/fanslists";
    }

    public MyFansApi setvalue(String limit,int page){
        this.limit = limit;
        this.page = page;
        return this;
    }
}
