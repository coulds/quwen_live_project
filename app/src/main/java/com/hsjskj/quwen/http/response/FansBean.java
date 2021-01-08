package com.hsjskj.quwen.http.response;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月07日 16:26
 * description   : quwen_live
 */
public class FansBean {

    public List<DataBean> data;

    public static  class DataBean{

        public String touid;
        public String avatar;
        public String user_nickname;
        public String status;


    }

}
