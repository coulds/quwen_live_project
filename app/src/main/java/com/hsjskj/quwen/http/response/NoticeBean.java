package com.hsjskj.quwen.http.response;

/**
 * @author : Jun
 * time          : 2020年12月28日 16:38
 * description   : quwen_live
 */
public class NoticeBean {

    /**
     * id : 1
     * title : 1
     */

    public String id;
    public String title;

    public NoticeBean(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public NoticeBean() {
        this.id = "";
        this.title = "暂无公告";
    }
}
