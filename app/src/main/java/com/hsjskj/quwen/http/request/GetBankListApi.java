package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time          : 2020年12月28日 16:07
 * description   : quwen_live
 */
public class GetBankListApi implements IRequestApi {
    @Override
    public String getApi() {
        return "User/mybanklists";
    }

    public int limit;
    public int page;

    public GetBankListApi(int limit, int page) {
        this.limit = limit;
        this.page = page;
    }

}
