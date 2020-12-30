package com.hsjskj.quwen.upload;

/**
 * @author : Jun
 * time          : 2020年12月30日 13:11
 * description   : quwen_live
 */
public class UploadBean {
    private String localPath = "";
    private String mResultUrl = "";

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getmResultUrl() {
        return mResultUrl;
    }

    public void setmResultUrl(String mResultUrl) {
        this.mResultUrl = mResultUrl;
    }

    public UploadBean(String localPath) {
        this.localPath = localPath;
    }

}
