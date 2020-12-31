package com.hsjskj.quwen.ui.activity;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hjq.base.BaseDialog;
import com.hjq.widget.layout.SettingBar;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.helper.ActivityStackManager;
import com.hsjskj.quwen.helper.CacheDataManager;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.LogoutApi;
import com.hsjskj.quwen.other.AppConfig;
import com.hsjskj.quwen.ui.dialog.DateDialog;
import com.hsjskj.quwen.ui.dialog.MenuDialog;
import com.hsjskj.quwen.ui.dialog.MessageDialog;
import com.hsjskj.quwen.ui.dialog.SafeDialog;
import com.hsjskj.quwen.ui.dialog.SelectDialog;
import com.hsjskj.quwen.ui.dialog.UpdateDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.SwitchButton;
import com.hsjskj.quwen.ui.user.activity.LoginActivity;
import com.hsjskj.quwen.ui.user.viewmodel.UserInfoViewModel;

import java.util.HashMap;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/03/01
 *    desc   : 设置界面
 */
public final class SettingActivity extends MyActivity
        implements SwitchButton.OnCheckedChangeListener {

    private ViewGroup mAvatarLayout;
    private ImageView mAvatarView;
    private SettingBar mIDView;
    private SettingBar mNameView;
    private SettingBar mAddressView;
    private SettingBar msb_tuichu_about;
    private UserInfoViewModel userInfoViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.setting_activity;
    }

    @Override
    protected void initView() {
        userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        mAvatarLayout = findViewById(R.id.sb_setting_language);
        mAvatarView = findViewById(R.id.iv_person_data_avatar);
        mIDView = findViewById(R.id.sb_setting_update);
        mNameView = findViewById(R.id.sb_setting_phone);
        msb_tuichu_about = findViewById(R.id.sb_tuichu_about);
        mAddressView = (SettingBar) findViewById(R.id.sb_setting_password);


        setOnClickListener(this.mAvatarLayout, this.mNameView, mAddressView, this.mIDView,this.msb_tuichu_about);

    }

    @Override
    protected void initData() {

        userInfoViewModel.getUserInfoBean().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mAddressView.setRightText(s);
                MyUserInfo.getInstance().getLogin().birthday = s;
                MyUserInfo.getInstance().upDataUserInfo();
            }
        });

        userInfoViewModel.getUserInfoSexBean().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                MyUserInfo.getInstance().getLogin().sex = "" + s;
                mNameView.setRightText(s!=2 ? "男" : "女");
                MyUserInfo.getInstance().upDataUserInfo();
            }

        });



        GlideApp.with(getActivity())
                .load(R.drawable.avatar_placeholder_ic)
                .placeholder(R.drawable.avatar_placeholder_ic)
                .error(R.drawable.avatar_placeholder_ic)
                .circleCrop()
                .into(mAvatarView);

        mAddressView.setRightText("1999年12月30日");
//        GlideApp.with(getContext()).load(MyUserInfo.getInstance().getLogin().avatar).into(mAvatarView);
        mIDView.setRightText(MyUserInfo.
                getInstance().getLogin().getUsername());
        mNameView.setRightText(MyUserInfo.getInstance().getLogin().isSexMale() ? "男" : "女");

    }

    @SingleClick
    @Override
    public void onClick(View v) {

        if (v == mIDView) {
            startActivity(NickNameEditActivity.class);
        } else if (v == mAvatarLayout) {
            ImageSelectActivity.start(this, new ImageSelectActivity.OnPhotoSelectListener() {
                @Override
                public void onSelected(List<String> data) {
                    GlideApp.with(getActivity())
                            .load(data.get(0))
                            .placeholder(R.drawable.avatar_placeholder_ic)
                            .error(R.drawable.avatar_placeholder_ic)
                            .circleCrop()
                            .into(mAvatarView);

                    userInfoViewModel.loadUserInfoAvatarBean(SettingActivity.this,data.get(0));

                }
            });
        }else if (v == mNameView) {
            ((SelectDialog.Builder) new SelectDialog.Builder(this)
                    .setTitle("请选择你的性别"))
                    .setList("男", "女")
                    .setSingleSelect()
                    .setSelect(MyUserInfo.getInstance().getLogin().isSexMale() ? 0 : 1)
                    .setListener(new SelectDialog.OnListener<String>() {
                        @Override
                        public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
                            SettingActivity settingActivity = SettingActivity.this;
                            settingActivity.toast((CharSequence) ("确定了：" + data.toString()));
                            for (Integer integer : data.keySet()) {
                                userInfoViewModel.loadUserInfoSexBean(SettingActivity.this, integer + 1);
                            }

                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                            SettingActivity.this.toast((CharSequence) "取消了");
                        }
                    }).show();
        } else if (v == mAddressView) {
            ((DateDialog.Builder) ((DateDialog.Builder) ((DateDialog.Builder) new DateDialog.Builder(this).setTitle(getString(R.string.date_title))).setConfirm(getString(R.string.common_confirm))).setCancel(getString(R.string.common_cancel))).setListener(new DateDialog.OnListener() {

                @Override
                public void onSelected(BaseDialog dialog, int year, int month, int day) {
//                    PersonalDataActivity.this.toast((CharSequence) (year + PersonalDataActivity.this.getString(R.string.common_year) + month + PersonalDataActivity.this.getString(R.string.common_month) + day + PersonalDataActivity.this.getString(R.string.common_day)));
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.set(1, year);
//                    calendar.set(2, month - 1);
//                    calendar.set(5, day);
//                    PersonalDataActivity.this.toast((CharSequence) ("时间戳：" + calendar.getTimeInMillis()));
                    userInfoViewModel.loadUserInfoBean(SettingActivity.this, year + "-" + month + "-" + day);
                }

                @Override
                public void onCancel(BaseDialog dialog) {
                    SettingActivity.this.toast((CharSequence) "取消了");
                }
            }).show();
        }else if (v == msb_tuichu_about){
            new MessageDialog.Builder(this)
                    // 标题可以不用填写
                    .setTitle("退出登录")
                    // 内容必须要填写
                    .setMessage("是否退出当前登录")
                    // 确定按钮文本
                    .setConfirm(getString(R.string.common_confirm))
                    // 设置 null 表示不显示取消按钮
                    .setCancel(getString(R.string.common_cancel))
                    // 设置点击按钮后不关闭对话框
                    //.setAutoDismiss(false)
                    .setListener(new MessageDialog.OnListener() {

                        @Override
                        public void onConfirm(BaseDialog dialog) {
                            startActivity(LoginActivity.class);
                            // 进行内存优化，销毁除登录页之外的所有界面
                            ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                            toast("取消了");

                        }
                    })
                    .show();

        }

    }





//        switch (v.getId()){
//            case R.id.sb_tuichu_about:
//
//                new MessageDialog.Builder(this)
//                        // 标题可以不用填写
//                        .setTitle("退出登录")
//                        // 内容必须要填写
//                        .setMessage("是否退出当前登录")
//                        // 确定按钮文本
//                        .setConfirm(getString(R.string.common_confirm))
//                        // 设置 null 表示不显示取消按钮
//                        .setCancel(getString(R.string.common_cancel))
//                        // 设置点击按钮后不关闭对话框
//                        //.setAutoDismiss(false)
//                        .setListener(new MessageDialog.OnListener() {
//
//                            @Override
//                            public void onConfirm(BaseDialog dialog) {
//                                startActivity(LoginActivity.class);
//                                // 进行内存优化，销毁除登录页之外的所有界面
//                                ActivityStackManager.getInstance().finishAllActivities(LoginActivity.class);
//                            }
//
//                            @Override
//                            public void onCancel(BaseDialog dialog) {
//                                toast("取消了");
//
//                            }
//                        })
//                        .show();
//
//                break;
//
//        }
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
////                // 退出登录
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


    /**
     * {@link SwitchButton.OnCheckedChangeListener}
     */

    @Override
    public void onCheckedChanged(SwitchButton button, boolean isChecked) {
        toast(isChecked);
    }
}