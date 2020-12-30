package com.hsjskj.quwen.ui.activity;

import android.view.Gravity;
import android.view.View;

import com.hjq.base.BaseDialog;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.helper.ActivityStackManager;
import com.hsjskj.quwen.helper.CacheDataManager;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.LogoutApi;
import com.hsjskj.quwen.other.AppConfig;
import com.hsjskj.quwen.ui.dialog.MenuDialog;
import com.hsjskj.quwen.ui.dialog.SafeDialog;
import com.hsjskj.quwen.ui.dialog.UpdateDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.SwitchButton;
import com.hsjskj.quwen.ui.user.activity.LoginActivity;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/03/01
 *    desc   : 设置界面
 */
public final class SettingActivity extends MyActivity
        implements SwitchButton.OnCheckedChangeListener {

    @Override
    protected int getLayoutId() {
        return R.layout.setting_activity;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.sb_setting_language:
//                // 底部选择框
//                new MenuDialog.Builder(this)
//                        // 设置点击按钮后不关闭对话框
//                        //.setAutoDismiss(false)
//                        .setList(R.string.setting_language_simple, R.string.setting_language_complex)
//                        .setListener((MenuDialog.OnListener<String>) (dialog, position, string) -> {
//                            mLanguageView.setRightText(string);
//                            BrowserActivity.start(getActivity(), "https://github.com/getActivity/MultiLanguages");
//                        })
//                        .setGravity(Gravity.BOTTOM)
//                        .setAnimStyle(BaseDialog.ANIM_BOTTOM)
//                        .show();
//                break;
//            case R.id.sb_setting_update:
//                // 本地的版本码和服务器的进行比较
//                if (20 > AppConfig.getVersionCode()) {
//                    new UpdateDialog.Builder(this)
//                            // 版本名
//                            .setVersionName("2.0")
//                            // 是否强制更新
//                            .setForceUpdate(false)
//                            // 更新日志
//                            .setUpdateLog("修复Bug\n优化用户体验")
//                            // 下载 url
//                            .setDownloadUrl("https://raw.githubusercontent.com/getActivity/AndroidProject/master/AndroidProject.apk")
//                            .show();
//                } else {
//                    toast(R.string.update_no_update);
//                }
//                break;
//            case R.id.sb_setting_phone:
//                new SafeDialog.Builder(this)
//                        .setListener((dialog, phone, code) -> PhoneResetActivity.start(getActivity(), code))
//                        .show();
//                break;
//            case R.id.sb_setting_password:
//                new SafeDialog.Builder(this)
//                        .setListener((dialog, phone, code) -> PasswordResetActivity.start(getActivity(), phone, code))
//                        .show();
//                break;
//            case R.id.sb_setting_agreement:
//                BrowserActivity.start(this, "https://github.com/getActivity/Donate");
//                break;
//            case R.id.sb_setting_about:
//                startActivity(AboutActivity.class);
//                break;
//            case R.id.sb_setting_auto:
//                // 自动登录
//                mAutoSwitchView.setChecked(!mAutoSwitchView.isChecked());
//                break;
//            case R.id.sb_setting_cache:
//                // 清除内存缓存（必须在主线程）
//                GlideApp.get(getActivity()).clearMemory();
//                new Thread(() -> {
//                    CacheDataManager.clearAllCache(this);
//                    // 清除本地缓存（必须在子线程）
//                    GlideApp.get(getActivity()).clearDiskCache();
//                    post(() -> {
//                        // 重新获取应用缓存大小
//                        mCleanCacheView.setRightText(CacheDataManager.getTotalCacheSize(getActivity()));
//                    });
//                }).start();
//                break;
//            case R.id.sb_setting_exit:
//                if (true) {
//                    startActivity(LoginActivity.class);
//                    // 进行内存优化，销毁除登录页之外的所有界面
//                    ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
//                    return;
//                }
//
//                // 退出登录
//                EasyHttp.post(this)
//                        .api(new LogoutApi())
//                        .request(new HttpCallback<HttpData<Void>>(this) {
//
//                            @Override
//                            public void onSucceed(HttpData<Void> data) {
//                                startActivity(LoginActivity.class);
//                                // 进行内存优化，销毁除登录页之外的所有界面
//                                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
//                            }
//                        });
//                break;
//            default:
//                break;
//        }
    }

    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }
}