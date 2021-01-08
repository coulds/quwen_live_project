package com.hsjskj.quwen.http.response;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月08日 11:42
 * description   : quwen_live
 */
public class ConcernBean {

    public List<concernDataBean> concerndata;

    public static class concernDataBean{
        public String touid;
        public String avatar;
        public String user_nickname;
    }
}
