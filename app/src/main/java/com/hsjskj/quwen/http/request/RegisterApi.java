package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 *    @author : Jun
 *    time   : 2020年12月28日09:49:05
 *    desc   : 用户注册
 */
public final class RegisterApi implements IRequestApi {

    @Override
    public String getApi() {
        return "Passport/register";
    }

    /** 手机号 */
    private String username;
    /** 注册模式 1手机号  2邮箱 */
    private String mode;
    /** 验证码 */
    private String code;
    /** 密码 RSA加密 */
    private String password;
    /** 邀请码 */
    private String invite;

    public RegisterApi setUsername(String username) {
        this.username = username;
        return this;
    }

    public RegisterApi setCode(String code) {
        this.code = code;
        return this;
    }

    public RegisterApi setPassword(String password) {
        this.password = password;
        return this;
    }

    public RegisterApi setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public RegisterApi setInvite(String invite) {
        this.invite = invite;
        return this;
    }
}