package com.hsjskj.quwen.upload;

import com.hsjskj.quwen.common.MyUserInfo;

/**
 * @author : Jun
 * time          : 2020年12月30日 13:11
 * description   : quwen_live
 */
public class UploadBean {
    private String localPath = "";
    private String mResultUrl = "";
    private String fileName = "";

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

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

    public UploadBean(String localPath, String fileName) {
        this.localPath = localPath;
        this.fileName = System.currentTimeMillis() + "_" + fileName;
    }

    public UploadBean(String localPath) {
        this.localPath = localPath;
        try {
            this.fileName = System.currentTimeMillis() + "_" + MyUserInfo.getInstance().getId();
        } catch (Exception e) {
            this.fileName = System.currentTimeMillis() + "_";
        }
    }
}
