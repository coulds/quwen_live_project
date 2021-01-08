package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : sen
 * time          : 2021年01月07日 11:00
 * description   : quwen_live
 */
public class BackHistoryApi implements IRequestApi {
    public String limit;
    public  int page;
    @Override
    public String getApi() {
        return "Feedback/lists";
    }
    public BackHistoryApi setvalue(String limit,int page){
        this.limit = limit;
        this.page = page;
        return this;
    }
}
