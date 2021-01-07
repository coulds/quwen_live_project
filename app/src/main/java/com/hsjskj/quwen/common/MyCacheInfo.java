package com.hsjskj.quwen.common;

import android.content.Context;
import com.tencent.mmkv.MMKV;


/**
 * @author : Jun
 * time          : 2020年12月24日 10:03
 * description   : 用户信息保存
 */
public class MyCacheInfo {
    private static volatile MyCacheInfo sInstance;
    private static final String TAG = "MyCacheInfo";

    //缓存首页轮播图key
    private static final String KEY_HOME_BANNER = "KEY_HOME_BANNER";
    //缓存首页视频
    private static final String KEY_HOME_VIDEO = "KEY_HOME_VIDEO";
    //缓存首页发布
    private static final String KEY_HOME_PUBLISH = "KEY_HOME_PUBLISH";

    private MMKV mmkv;

    private MyCacheInfo() {

    }

    public static MyCacheInfo getInstance() {
        // 加入双重校验锁
        if (sInstance == null) {
            synchronized (MyCacheInfo.class) {
                if (sInstance == null) {
                    sInstance = new MyCacheInfo();
                }
            }
        }
        return sInstance;
    }

    public void initContext(Context context) {
        MMKV.initialize(context);
        mmkv = MMKV.mmkvWithID(TAG);
    }

    public void setHomeBannerCache(String json) {
        checkNullPointer(mmkv);
        mmkv.encode(KEY_HOME_BANNER, json);
    }

    public String getHomeBannerCache() {
        checkNullPointer(mmkv);
        return mmkv.decodeString(KEY_HOME_BANNER,"");
    }

    public void setHomeVideoCache(String json) {
        checkNullPointer(mmkv);
        mmkv.encode(KEY_HOME_VIDEO, json);
    }

    public String getHomeVideoCache() {
        checkNullPointer(mmkv);
        return mmkv.decodeString(KEY_HOME_VIDEO);
    }

    public void setHomePublishCache(String json) {
        checkNullPointer(mmkv);
        mmkv.encode(KEY_HOME_PUBLISH, json);
    }

    public String getHomePublishCache() {
        checkNullPointer(mmkv);
        return mmkv.decodeString(KEY_HOME_PUBLISH);
    }

    private static void checkNullPointer(Object object) {
        if (object == null) {
            throw new NullPointerException("MyCacheInfo mmkv is null");
        }
    }

}
