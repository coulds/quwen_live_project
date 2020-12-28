package com.hsjskj.quwen.http.server;

/**
 *    @author : jun
 *    time   : 2020年12月28日09:46:52
 *    desc   : 测试环境
 */
public class TestServer extends ReleaseServer {

    @Override
    public String getHost() {
        return "http://10.0.1.151:4606/";
    }
}