package com.hsjskj.quwen.ui.my.object;


import java.util.List;

//银行卡
public class BankCard {

    public List<DataBean> data;

    public static class DataBean{
        public String username;
        public String number;
        public String bank_name;
        public String mark;
    }
}
