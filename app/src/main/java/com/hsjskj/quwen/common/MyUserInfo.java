package com.hsjskj.quwen.common;

import android.content.Context;
import android.text.TextUtils;

import com.tencent.mmkv.MMKV;

/**
 * @author : Jun
 * time          : 2020年12月24日 10:03
 * description   : 用户信息保存
 */
public class MyUserInfo {
    private static volatile MyUserInfo sInstance;
    private static final String TAG = "MyUserInfo";

    //默认保存一次时间
    private static final String KEY_SAVE_INSTALL = "KEY_SAVE_INSTALL";
    //用户token
    private static final String KEY_USER_TOKEN = "KEY_USER_TOKEN";
    //用户id
    private static final String KEY_USER_ID = "KEY_USER_ID";

    private MMKV mmkv;

    private MyUserInfo() {

    }

    public static MyUserInfo getInstance() {
        // 加入双重校验锁
        if (sInstance == null) {
            synchronized (MyUserInfo.class) {
                if (sInstance == null) {
                    sInstance = new MyUserInfo();
                }
            }
        }
        return sInstance;
    }

    public void initContext(Context context) {
        MMKV.initialize(context);
        mmkv = MMKV.mmkvWithID(TAG);
        long defaultTimer = mmkv.decodeLong(KEY_SAVE_INSTALL, 0);
        if (defaultTimer == 0) {
            mmkv.encode(KEY_SAVE_INSTALL, System.currentTimeMillis());
        }
    }

    public boolean isLogin() {
        try {
            String token = getToken();
            if (token != null && !TextUtils.isEmpty(token)) {
                return true;
            }
        } catch (Exception e) {
        }
        clearUserInfo();
        return false;
    }

    public String getToken() {
        checkNullPointer(mmkv);
        return mmkv.decodeString(KEY_USER_TOKEN, "");
    }

    public String getId() {
        checkNullPointer(mmkv);
        return mmkv.decodeString(KEY_USER_ID, "");
    }

    public void setToken(String token) {
        checkNullPointer(mmkv);
        mmkv.decodeString(KEY_USER_TOKEN, token);
    }

    public void setId(String id) {
        checkNullPointer(mmkv);
        mmkv.encode(KEY_USER_ID, id);
    }

    //添加其他保存信息.....

    //清空用户信息，保留安装时间
    public void clearUserInfo() {
        checkNullPointer(mmkv);
        long defaultTimer = mmkv.decodeLong(KEY_SAVE_INSTALL, 0);
        mmkv.clearAll();
        mmkv.encode(KEY_SAVE_INSTALL, defaultTimer);
    }

    private static void checkNullPointer(Object object) {
        if (object == null) {
            throw new NullPointerException("MyUserInfo mmkv is null");
        }
    }
}
