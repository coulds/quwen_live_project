package com.hsjskj.quwen.http.response;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月30日 15:06
 * description   : quwen_live
 */
public class HomePublishBean {

    public List<DataBean> data;

    public static class DataBean {
        public String id;
        public String user_id;
        public String title;
        public String content;
        public String create_time;
        public String user_nickname;
        public String avatar;
        public int sex;
        public String constellation;
        public List<String> enclosure;


        public String getShowName() {
            if (user_nickname == null) {
                return "" + id;
            }
            return user_nickname;
        }

        public boolean isMale() {
            return sex != 2;
        }
    }
}
