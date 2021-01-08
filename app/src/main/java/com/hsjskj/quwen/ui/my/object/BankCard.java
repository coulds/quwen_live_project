package com.hsjskj.quwen.ui.my.object;


import java.util.List;

//银行卡
public class BankCard {

    public List<DataBean> data;

    public static class DataBean{

        public String id;
        public AccountInfoBean account_info;

        public static class AccountInfoBean{
            public String username;
            public String number;
            public String bank_name;
            public String mark;
        }
    }
////
////    {
////        "code": 200,
////            "msg": "添加成功",
////            "data": []
////    }
//  public static class DataBeanAdd{
//    private Integer code;
//    private String msg;
//}
}
