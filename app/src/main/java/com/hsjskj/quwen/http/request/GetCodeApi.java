package com.hsjskj.quwen.http.request;

import com.hjq.http.config.IRequestApi;

/**
 *    @author : Jun
 *    time   : 2020年12月28日10:41:00
 *    desc   : 获取验证码
 */
public final class GetCodeApi implements IRequestApi {

    @Override
    public String getApi() {
        return "Tool/sendCode";
    }

    /** 手机号 */
    public String username;
    /** 发送模式 */
    public String mode;
    /** 图形验证码 */
    public String code;
    /** 验证场景 */
    public String scene;

    public String id;

    public GetCodeApi setUsername(String username) {
        this.username = username;
        return this;
    }

    public GetCodeApi setMode(String mode) {
        this.mode = mode;
        return this;
    }

    public GetCodeApi setCode(String code) {
        this.code = code;
        return this;
    }

    public GetCodeApi setScene(String scene) {
        this.scene = scene;
        return this;
    }
    public GetCodeApi setId(String id) {
        this.id = id;
        return this;
    }
}