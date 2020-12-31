package com.hsjskj.quwen.http.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author : jun
 * time   : 2020年12月28日14:02:46
 * desc   : 登录返回
 */
public final class LoginBean implements Parcelable {
    public String id;
    public String user_nickname;
    public String sex;
    public String birthday;
    public String create_time;
    public String avatar;
    public String constellation;
    public String user_level;
    public String anchor_level;
    public String is_anchor;
    public String token;

    public boolean isSexMale() {
        return !"2".equals(this.sex);
    }

    public String getUsername() {
        String str = this.user_nickname;
        if (str == null) {
            return this.id;
        }
        return str;
    }


    public LoginBean(){

    }

    public LoginBean(Parcel in) {
        id = in.readString();
        user_nickname = in.readString();
        sex = in.readString();
        birthday = in.readString();
        create_time = in.readString();
        avatar = in.readString();
        constellation = in.readString();
        user_level = in.readString();
        anchor_level = in.readString();
        is_anchor = in.readString();
        token = in.readString();
    }

    public static final Creator<LoginBean> CREATOR = new Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel in) {
            return new LoginBean(in);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_nickname);
        dest.writeString(sex);
        dest.writeString(birthday);
        dest.writeString(create_time);
        dest.writeString(avatar);
        dest.writeString(constellation);
        dest.writeString(user_level);
        dest.writeString(anchor_level);
        dest.writeString(is_anchor);
        dest.writeString(token);
    }
}