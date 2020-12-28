package com.hsjskj.quwen.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.alibaba.android.arouter.launcher.ARouter;
import com.billy.android.swipe.SmartSwipeBack;
import com.hjq.bar.TitleBar;
import com.hjq.bar.initializer.LightBarInitializer;
import com.hsjskj.quwen.BuildConfig;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.SwipeAction;
import com.hsjskj.quwen.helper.ActivityStackManager;
import com.hsjskj.quwen.http.cookie.CookieJarImpl;
import com.hsjskj.quwen.http.cookie.store.SPCookieStore;
import com.hsjskj.quwen.http.intercept.TokenIntercept;
import com.hsjskj.quwen.http.model.RequestHandler;
import com.hsjskj.quwen.http.server.ReleaseServer;
import com.hsjskj.quwen.http.server.TestServer;
import com.hsjskj.quwen.other.AppConfig;
import com.hjq.http.EasyConfig;
import com.hjq.http.config.IRequestServer;
import com.hjq.toast.ToastInterceptor;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.other.GlideImageLoader;
import com.hsjskj.umeng.UmengClient;
import com.lzy.ninegrid.NineGridView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import static com.scwang.smartrefresh.layout.util.SmartUtil.dp2px;

/**
 * @author : Jun
 * time   : 2020年12月28日09:48:22
 * desc   : 项目中的 Application 基类
 */
public final class MyApplication extends Application implements LifecycleOwner {

    private final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);

    @Override
    public void onCreate() {
        super.onCreate();
        mLifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        MyUserInfo.getInstance().initContext(this);
        NineGridView.setImageLoader(new GlideImageLoader());
        initSdk(this);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }

    /**
     * 初始化一些第三方框架
     */
    public static void initSdk(Application application) {
        // 吐司工具类
        ToastUtils.init(application);

        // 设置 Toast 拦截器
        ToastUtils.setToastInterceptor(new ToastInterceptor() {
            @Override
            public boolean intercept(Toast toast, CharSequence text) {
                boolean intercept = super.intercept(toast, text);
                if (intercept) {
                    Log.e("Toast", "空 Toast");
                } else {
                    Log.i("Toast", text.toString());
                }
                return intercept;
            }
        });
        TitleBar.setDefaultInitializer(new LightBarInitializer() {
            @Override
            public TextView getTitleView(Context context) {
                TextView titleView = super.getTitleView(context);
                titleView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                titleView.setTextSize(18);
                return titleView;
            }

            @Override
            public TextView getRightView(Context context) {
                TextView rightView = super.getRightView(context);
                rightView.setTextSize(15);
                return rightView;
            }

            @Override
            public Drawable getBackgroundDrawable(Context context) {
                return super.getBackgroundDrawable(context);
            }

            @Override
            public Drawable getBackIcon(Context context) {
                Drawable backIcon = getDrawableResources(context, R.drawable.arrows_left_ic);
                backIcon.setBounds(0, 0, dp2px(20), dp2px(20));
                return backIcon;
            }
        });

        // 本地异常捕捉
//        CrashHandler.register(application);

        // 友盟统计、登录、分享 SDK
        UmengClient.init(application);

        // Bugly 异常捕捉
        CrashReport.initCrashReport(application, AppConfig.getBuglyId(), AppConfig.isDebug());

        // 设置全局的 Header 构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new ClassicsHeader(context).setEnableLastTime(false));
        // 设置全局的 Footer 构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> new ClassicsFooter(context).setDrawableSize(20));

        // Activity 栈管理初始化
        ActivityStackManager.getInstance().init(application);

        // 网络请求框架初始化
        IRequestServer server;
        if (AppConfig.isDebug()) {
            server = new TestServer();
        } else {
            server = new ReleaseServer();
        }

        EasyConfig.with(getOkHttpClient(application))
                // 是否打印日志
                .setLogEnabled(AppConfig.isDebug())
                // 设置服务器配置
                .setServer(server)
                // 设置请求处理策略
                .setHandler(new RequestHandler(application))
                // 设置请求重试次数
                .setRetryCount(1)
                // 添加全局请求参数
//                .addParam("token", "6666666")
                // 添加全局请求头
                .addHeader("appVersion", AppConfig.getVersionName())
                .addHeader("Connection", "keep-alive")
                // 启用配置
                .into();

        // Activity 侧滑返回
        SmartSwipeBack.activitySlidingBack(application, activity -> {
            if (activity instanceof SwipeAction) {
                return ((SwipeAction) activity).isSwipeEnable();
            }
            return true;
        });

        //初始化 ARouter
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(application);
    }

    public static OkHttpClient  getOkHttpClient(Application application){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(AppConfig.TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(AppConfig.TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(AppConfig.TIMEOUT, TimeUnit.MILLISECONDS);
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(application)));
        builder.addInterceptor(new TokenIntercept());
        return  builder.build();
    }
}