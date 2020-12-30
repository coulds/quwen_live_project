package com.hsjskj.quwen.http.response;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月30日 09:05
 * description   : quwen_live
 */
public class HomeVideoListBean {

    public List<DataBean> data;

    public static class DataBean {

        public int id;
        public String title;
        public String url;
        public String cover;
        public String create_time;
    }
}
