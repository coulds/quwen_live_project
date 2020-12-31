package com.hsjskj.quwen.http.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author : Jun
 * time   : 2020年12月31日10:04:56
 * desc   : 个人主页用户信息
 */
public final class UserInfoBean implements Parcelable {

    //登录和用户 公共
    public String id;
    public String user_nickname;
    public String avatar;
    public String sex;
    public String constellation;
    public int is_anchor;

    //登录返回
    public String birthday;
    public String create_time;
    public String user_level;
    public String anchor_level;
    public String token;

    //用户返回
    public int exp;
    public int attention_to_me_count;
    public int i_attention_count;
    public String professional;
    public int level;
    public int exprience;
    public String show_time;
    public int price;
    public int comments_count;
    public int is_follow;
    public List<String> label;

    public UserInfoBean() {
    }

    public String getShowName() {
        if (user_nickname == null) {
            return "" + id;
        }
        return user_nickname;
    }

    public String getUsername() {
        return getShowName();
    }

    public boolean isSetMale() {
        return "1".equals(sex) || "2".equals(sex);
    }

    public boolean isMale() {
        return "1".equals(sex);
    }

    public boolean isSexMale() {
        return isMale();
    }

    public boolean isAnchor() {
        return is_anchor == 1;
    }

    public boolean isFollow() {
        return is_follow == 1;
    }

    protected UserInfoBean(Parcel in) {
        id = in.readString();
        user_nickname = in.readString();
        avatar = in.readString();
        sex = in.readString();
        constellation = in.readString();
        is_anchor = in.readInt();
        birthday = in.readString();
        create_time = in.readString();
        user_level = in.readString();
        anchor_level = in.readString();
        token = in.readString();
        exp = in.readInt();
        attention_to_me_count = in.readInt();
        i_attention_count = in.readInt();
        professional = in.readString();
        level = in.readInt();
        exprience = in.readInt();
        show_time = in.readString();
        price = in.readInt();
        comments_count = in.readInt();
        is_follow = in.readInt();
        label = in.createStringArrayList();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
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
        dest.writeString(avatar);
        dest.writeString(sex);
        dest.writeString(constellation);
        dest.writeInt(is_anchor);
        dest.writeString(birthday);
        dest.writeString(create_time);
        dest.writeString(user_level);
        dest.writeString(anchor_level);
        dest.writeString(token);
        dest.writeInt(exp);
        dest.writeInt(attention_to_me_count);
        dest.writeInt(i_attention_count);
        dest.writeString(professional);
        dest.writeInt(level);
        dest.writeInt(exprience);
        dest.writeString(show_time);
        dest.writeInt(price);
        dest.writeInt(comments_count);
        dest.writeInt(is_follow);
        dest.writeStringList(label);
    }
}