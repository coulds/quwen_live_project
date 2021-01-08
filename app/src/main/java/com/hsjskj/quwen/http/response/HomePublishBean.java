package com.hsjskj.quwen.http.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月30日 15:06
 * description   : quwen_live
 */
public class HomePublishBean {

    public List<DataBean> data;

    public static class DataBean  implements Parcelable {
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
        public List<String> enclosure_key;
        protected DataBean(){

        }
        protected DataBean(Parcel in) {
            id = in.readString();
            user_id = in.readString();
            title = in.readString();
            content = in.readString();
            create_time = in.readString();
            user_nickname = in.readString();
            avatar = in.readString();
            sex = in.readInt();
            status = in.readString();
            constellation = in.readString();
            enclosure = in.createStringArrayList();
            enclosure_key = in.createStringArrayList();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(user_id);
            dest.writeString(title);
            dest.writeString(content);
            dest.writeString(create_time);
            dest.writeString(user_nickname);
            dest.writeString(avatar);
            dest.writeInt(sex);
            dest.writeString(status);
            dest.writeString(constellation);
            dest.writeStringList(enclosure);
            dest.writeStringList(enclosure_key);
        }
    }
}
