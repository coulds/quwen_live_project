package com.hjq.base.route;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author Jun
 * 模块间跳转，带参数，没有上下文，特殊参数 等三种情况例子
 * 2020年12月23日11:27:16
 */

public class RouteUtil {
    /**
     * Intent隐式启动 action
     */
    public static final String PATH_LAUNCHER = "/ui/activity/SplashActivity";
    public static final String PATH_LOGIN_INVALID = "/ui/user/activity/LoginActivity";

    /**
     * 这是个模拟，可复制页面
     */
    public static final String PATH_OBJECT = "/ui/copy/CopyActivity";

    /**
     * Intent隐式启动 action 参数key
     */
    public static final String KEY_LOGIN_TIP = "tip";
    public static final String KEY_OBJECT = "object";

    /**
     * 启动页
     */
    public static void forwardLauncher(Context context) {
        ARouter.getInstance().build(PATH_LAUNCHER)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .navigation();
    }

    /**
     * 登录过期
     */
    public static void forwardLoginInvalid(String tip) {
        ARouter.getInstance().build(PATH_LOGIN_INVALID)
                .withString(KEY_LOGIN_TIP, tip)
                .navigation();
    }

    /**
     * 跳转带参数
     *
     * @param context
     * @param object
     */
    public static void forwardObject(Context context, Parcelable object) {
        Postcard postcard = ARouter.getInstance().build(PATH_OBJECT);
        postcard.withParcelable(KEY_OBJECT, object);
        postcard.navigation(context);
    }


    //.........................

}
