package com.hsjskj.quwen.ui.user.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.base.route.RouteUtil;
import com.hjq.widget.view.RegexEditText;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.EditCloseAction;
import com.hsjskj.quwen.aop.DebugLog;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.helper.InputTextHelper;
import com.hsjskj.quwen.http.response.UserInfoBean;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.ui.home.activity.HomeActivity;
import com.hsjskj.quwen.ui.user.viewmodel.LoginViewModel;
import com.hsjskj.quwen.wxapi.WXEntryActivity;
import com.hsjskj.umeng.Platform;
import com.hsjskj.umeng.UmengClient;
import com.hsjskj.umeng.UmengLogin;

import java.util.regex.Pattern;

/**
 * @author : Jun
 * time   : 2020年12月24日10:58:33
 * desc   : 登录界面
 */
@Route(path = RouteUtil.PATH_LOGIN_INVALID)
public final class LoginActivity extends MyActivity
        implements UmengLogin.OnLoginListener, EditCloseAction {

    @DebugLog
    public static void start(Context context, String phone, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IntentKey.PHONE, phone);
        intent.putExtra(IntentKey.PASSWORD, password);
        context.startActivity(intent);
    }

    private EditText mPhoneView;
    private EditText mPasswordView;

    private View mForgetView;
    private Button mCommitView;
    private View mWeChatView;
    private LoginViewModel loginViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        loginViewModel = new LoginViewModel();
        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mForgetView = findViewById(R.id.tv_login_forget);
        mCommitView = findViewById(R.id.btn_login_commit);
        mWeChatView = findViewById(R.id.iv_login_wechat);
        setOnClickListener(mForgetView, mCommitView, mWeChatView);

        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .build();
    }

    @Override
    protected void initData() {
        // 判断用户当前有没有安装微信
        if (!UmengClient.isAppInstalled(this, Platform.WECHAT)) {
            mWeChatView.setVisibility(View.GONE);
        }

        // 填充传入的手机号和密码
        mPhoneView.setText(getString(IntentKey.PHONE));
        mPasswordView.setText(getString(IntentKey.PASSWORD));
    }


    @Override
    public void onRightClick(View v) {
        // 跳转到注册界面
        startActivityForResult(RegisterActivity.class, (resultCode, data) -> {
            // 如果已经注册成功，就执行登录操作
            if (resultCode == RESULT_OK && data != null) {
                mPhoneView.setText(data.getStringExtra(IntentKey.PHONE));
                mPasswordView.setText(data.getStringExtra(IntentKey.PASSWORD));
                mPasswordView.setSelection(mPasswordView.getText().length());
                onClick(mCommitView);
            }
        });
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == mForgetView) {
            startActivity(PhoneMailForgetActivity.class);
        } else if (v == mCommitView) {
            login();
        } else if (v == mWeChatView) {
            toast("记得改好第三方 AppID 和 AppKey，否则会调不起来哦");
            Platform platform;
            if (v == mWeChatView) {
                platform = Platform.WECHAT;
                toast("也别忘了改微信 " + WXEntryActivity.class.getSimpleName() + " 类所在的包名哦");
            } else {
                throw new IllegalStateException("are you ok?");
            }
            UmengClient.login(this, platform, this);
        }
    }

    private void login() {
        String inputAccount = mPhoneView.getText().toString();
        boolean matches = Pattern.compile(RegexEditText.REGEX_EMAIL).matcher(inputAccount).matches();
        boolean matches2 = Pattern.compile(RegexEditText.REGEX_MOBILE).matcher(inputAccount).matches();
        if (!matches && !matches2) {
            toast(R.string.common_phone_and_email_input_error);
            return;
        }
        String password = mPasswordView.getText().toString();
        boolean matches3 = Pattern.compile(RegexEditText.REGEX_PSW).matcher(password).matches();
        if (!matches3) {
            toast(R.string.register_password_hint1);
            return;
        }
        showDialog();
        loginViewModel.sendLogin(this, inputAccount, password).observe(this, a -> {
            hideDialog();
            loginResult(a);
        });
    }

    private void loginResult(UserInfoBean a) {
        if (a != null) {
            MyUserInfo.getInstance().setToken(a.token);
            MyUserInfo.getInstance().setId(a.id);
            MyUserInfo.getInstance().setLogin(a);
            // 跳转到主页
            startActivity(HomeActivity.class);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 友盟登录回调
        UmengClient.onActivityResult(this, requestCode, resultCode, data);
    }

    /**
     * {@link UmengLogin.OnLoginListener}
     */

    /**
     * 授权成功的回调
     *
     * @param platform 平台名称
     * @param data     用户资料返回
     */
    @Override
    public void onSucceed(Platform platform, UmengLogin.LoginData data) {
        // 判断第三方登录的平台
        switch (platform) {
            case WECHAT:
                break;
            default:
                break;
        }

        toast("昵称：" + data.getName() + "\n" + "性别：" + data.getSex());
        toast("id：" + data.getId());
        toast("token：" + data.getToken());
    }

    /**
     * 授权失败的回调
     *
     * @param platform 平台名称
     * @param t        错误原因
     */
    @Override
    public void onError(Platform platform, Throwable t) {
        toast("第三方登录出错：" + t.getMessage());
    }

    /**
     * 授权取消的回调
     *
     * @param platform 平台名称
     */
    @Override
    public void onCancel(Platform platform) {
        toast("取消第三方登录");
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }

    @Override
    protected boolean isStatusBarDarkFont() {
        return false;
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        dispatchTouchEventEdit(ev);
        return super.dispatchTouchEvent(ev);
    }
}