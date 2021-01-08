package com.hsjskj.quwen.http.request;

import com.alibaba.fastjson.JSON;
import com.hjq.http.config.IRequestApi;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月30日14:40:17
 * description   : quwen_live
 */
public class HomePublishEditApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Dynamic/modify";
    }

    public String id;
    public String title;
    public String content;
    //附件，json ["https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2602848933.webp"]	|
    public String enclosure;

    public HomePublishEditApi(String title, String content, String enclosure) {
        this.title = title;
        this.content = content;
        this.enclosure = enclosure;
    }

    public HomePublishEditApi(String id,String title, String content, List<String> enclosures) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.enclosure = JSON.toJSONString(enclosures);
    }

}
