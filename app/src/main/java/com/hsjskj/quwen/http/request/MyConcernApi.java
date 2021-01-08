package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : sen
 * time          : 2021年01月08日 11:47
 * description   : quwen_live
 */
public class MyConcernApi implements IRequestApi {
    public int limit;
    public int page;
    @Override
    public String getApi() {
        return "User/followlists";
    }
    public MyConcernApi setvalue(int limit,int page){
      this.limit = limit;
      this.page = page;
        return this;
    }
}
