package com.hsjskj.quwen.upload;

/**
 * @author : Jun
 * time          : 2020年12月30日 13:13
 * description   : quwen_live
 */
public interface UploadCallback {
    void onSuccess(UploadBean bean);

    void onFailure();
}
