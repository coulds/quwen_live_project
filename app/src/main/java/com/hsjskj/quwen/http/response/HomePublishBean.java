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
        private String status;
        public String constellation;
        public List<String> enclosure;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getConstellation() {
            return constellation;
        }

        public void setConstellation(String constellation) {
            this.constellation = constellation;
        }

        public List<String> getEnclosure() {
            return enclosure;
        }

        public void setEnclosure(List<String> enclosure) {
            this.enclosure = enclosure;
        }

        public String getShowName() {
            if (user_nickname == null) {
                return "" + id;
            }
            return user_nickname;
        }

        public boolean isSetMale() {
            return sex != 0;
        }

        public boolean isMale() {
            return sex != 2;
        }
    }
}
