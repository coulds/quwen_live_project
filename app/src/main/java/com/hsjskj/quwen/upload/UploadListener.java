package com.hsjskj.quwen.upload;

/**
 * @author : Jun
 * time          : 2020年12月30日 13:12
 * description   : quwen_live
 */
public interface UploadListener {
    void upload(UploadBean bean, UploadCallback callback);

    void cancel();
}
