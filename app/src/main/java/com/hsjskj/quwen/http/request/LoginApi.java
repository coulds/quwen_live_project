package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;
import com.hsjskj.quwen.other.AppConfig;

/**
 *    @author : Jun
 *    time   : 2020年12月28日13:56:42
 *    desc   : 用户登录
 */
public final class LoginApi implements IRequestApi {

    @Override
    public String getApi() {
        return "Passport/login";
    }

    /** 手机号 */
    private String username;
    /** 登录密码 */
    private String password;

    public LoginApi setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginApi setPassword(String password) {
        this.password = AppConfig.rsa(password);
        return this;
    }
}