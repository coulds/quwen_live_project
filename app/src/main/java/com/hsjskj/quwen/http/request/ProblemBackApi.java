package com.hsjskj.quwen.http.request;

import com.alibaba.fastjson.JSON;
import com.hjq.http.config.IRequestApi;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月07日 09:53
 * description   : quwen_live
 */
public class ProblemBackApi implements IRequestApi {
    public String phone;
    public String content;
    public String thumb;
    @Override
    public String getApi() {
        return "Feedback/submit";
    }

    public ProblemBackApi setvalue(String phone, String content, List<String> thumb) {
       this.phone = phone;
       this.content = content;
       this.thumb = JSON.toJSONString(thumb);
       return this;
    }


}
