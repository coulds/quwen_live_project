package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * @author : Jun
 * time          : 2020年12月28日 16:07
 * description   : quwen_live
 */
public class HomeQuestionApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Constellation/index";
    }

    //1 女 2 男
    public int sex;
    public String birthday;

    public HomeQuestionApi(int sex, String birthday) {
        this.sex = sex;
        this.birthday = birthday;
    }
}
