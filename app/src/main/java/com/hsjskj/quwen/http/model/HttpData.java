package com.hsjskj.quwen.http.model;

/**
 *    @author : Jun
 *    time   : 2020年12月28日15:05:30
 *    desc   : 统一接口数据结构
 */
public class HttpData<T> {

    /** 返回码 */
    public int code;
    /** 提示语 */
    public String msg;
    /** 数据 */
    public T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return msg;
    }

    public T getData() {
        return data;
    }
}