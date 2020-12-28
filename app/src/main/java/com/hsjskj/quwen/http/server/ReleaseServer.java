package com.hsjskj.quwen.http.server;

import com.hjq.http.config.IRequestServer;
import com.hjq.http.model.BodyType;

/**
 *    @author : jun
 *    time   : 2020年12月28日09:46:40
 *    desc   : 正式环境
 */
public class ReleaseServer implements IRequestServer {

    @Override
    public String getHost() {
        return "http://10.0.1.151:4606/";
    }

    @Override
    public BodyType getType() {
        return BodyType.FORM;
    }

    @Override
    public String getPath() {
        return "api/";
    }
}