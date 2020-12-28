package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;
import com.hsjskj.quwen.other.AppConfig;

/**
 *    @author : Jun
 *    time   : 2020年12月28日14:40:16
 *    desc   : 用户忘记密码
 */
public final class ForgetPasswordApi implements IRequestApi {

    @Override
    public String getApi() {
        return "Passport/forget";
    }

    /** 手机号 */
    private String username;
    /** 注册模式 1手机号  2邮箱 */
    private String mode;
    /** 验证码 */
    private String code;
    /** 密码 RSA加密 */
    private String password;

    public ForgetPasswordApi setUsername(String username) {
        this.username = username;
        return this;
    }

    public ForgetPasswordApi setCode(String code) {
        this.code = code;
        return this;
    }

    public ForgetPasswordApi setPassword(String password) {
        this.password = AppConfig.rsa(password);
        return this;
    }

    public ForgetPasswordApi setMode(String mode) {
        this.mode = mode;
        return this;
    }

}