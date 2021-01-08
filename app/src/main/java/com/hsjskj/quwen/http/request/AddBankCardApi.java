package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

public class AddBankCardApi implements IRequestApi{
    @Override
    public String getApi() {
        return "User/addbank";
    }

    public String username;//持卡人
    public String number;//卡号
    public String bank_name;//开户行
    public String mark;//备注

    public AddBankCardApi(String username, String number, String bank_name, String mark) {
        this.username = username;
        this.number = number;
        this.bank_name = bank_name;
        this.mark = mark;
    }

}
