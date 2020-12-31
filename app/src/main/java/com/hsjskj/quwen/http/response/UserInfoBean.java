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

    public int id;
    public String user_nickname;
    public String avatar;
    public int sex;
    public String constellation;
    public int is_anchor;
    public int exp;
    public int attention_to_me_count;
    public int i_attention_count;
    public String professional;
    public int level;
    public int exprience;
    public String show_time;
    public int price;
    public int comments_count;
    public List<String> label;

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        id = in.readInt();
        user_nickname = in.readString();
        avatar = in.readString();
        sex = in.readInt();
        constellation = in.readString();
        is_anchor = in.readInt();
        attention_to_me_count = in.readInt();
        i_attention_count = in.readInt();
        professional = in.readString();
        level = in.readInt();
        exprience = in.readInt();
        show_time = in.readString();
        price = in.readInt();
        comments_count = in.readInt();
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
        dest.writeInt(id);
        dest.writeString(user_nickname);
        dest.writeString(avatar);
        dest.writeInt(sex);
        dest.writeString(constellation);
        dest.writeInt(is_anchor);
        dest.writeInt(attention_to_me_count);
        dest.writeInt(i_attention_count);
        dest.writeString(professional);
        dest.writeInt(level);
        dest.writeInt(exprience);
        dest.writeString(show_time);
        dest.writeInt(price);
        dest.writeInt(comments_count);
        dest.writeStringList(label);
    }
}