package com.hsjskj.quwen.http.response;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月07日 11:26
 * description   : quwen_live
 */
public class BackFeedHistoryListBean {

    public List<DataBean> data;

    public static  class DataBean{


        public String phone;
        public String content;
        public String thumb;
        public String mold;
        public String create_time;

    }

}
