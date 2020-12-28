package com.hsjskj.quwen.other;

import com.hsjskj.quwen.BuildConfig;
import com.hsjskj.quwen.http.rsa.Base64Utils;
import com.hsjskj.quwen.http.rsa.FileEncryptionManager;

/**
 * @author : Jun
 * time   : 2020-12-26 14:04:35
 * desc   : App 配置管理类
 */
public final class AppConfig {

    public static final int TIMEOUT=10*1000;

    /**
     * 当前是否为 Debug 模式
     */
    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 获取当前应用的包名
     */
    public static String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    /**
     * 获取当前应用的版本名
     */
    public static String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * 获取当前应用的版本码
     */
    public static int getVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    /**
     * 获取 BuglyId
     */
    public static String getBuglyId() {
        return BuildConfig.BUGLY_ID;
    }

    /**
     * 当前网络响应成功code
     */
    public static int getHttpSuccessCode() {
        return 200;
    }

    /**
     * 当前登录超时code
     */
    public static int getHttpLoginTimeCode() {
        return 700;
    }

    /**
     * 获取当前公钥
     */
    public static String getRsaPublic() {
        return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5S456hcCgbmoGen0gFLdHerV1\n" +
                "ww2HcW0PqonoqixADuy4XWH+2gvoF0nN3bbpaNpLbV4Rq9l+j1PBOKAhmB6hqJ05\n" +
                "Us2Z7TM6lrPGnXqipbnvB5karI7uPYT4dktLEwktziKulcU1f0VyLd+qSmh8/coG\n" +
                "Eig2RsHcnY80uJIt7QIDAQAB\n";
    }

    public static String rsa(String str) {
        try {
            FileEncryptionManager.getInstance().setRSAKey(getRsaPublic(), "", true);
            byte[] encryptByte = FileEncryptionManager.getInstance().encryptByPublicKey(str.getBytes());
            return Base64Utils.encode(encryptByte);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}