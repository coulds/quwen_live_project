package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 * Administrator :ZB
 * 2021/1/7 0007
 * describe :
 **/
public class ExtensionAssessPostApi implements IRequestApi {
    @Override
    public String getApi() {
        return "Invite/apply";

    }

    private String name;
    private String mobile;
    private String idCard;
    private String front;
    private String back;

    public ExtensionAssessPostApi(String name, String mobile, String idCard, String front, String back) {
        this.name = name;
        this.mobile = mobile;
        this.idCard = idCard;
        this.front = front;
        this.back = back;
    }
}
