package com.hsjskj.quwen.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.base.route.RouteUtil;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.DebugLog;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.helper.InputTextHelper;
import com.hsjskj.quwen.helper.SpannableStringHelper;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.LoginApi;
import com.hsjskj.quwen.http.response.LoginBean;
import com.hsjskj.quwen.other.IntentKey;
import com.hsjskj.quwen.wxapi.WXEntryActivity;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hsjskj.umeng.Platform;
import com.hsjskj.umeng.UmengClient;
import com.hsjskj.umeng.UmengLogin;

/**
 * author : Jun
 * time   : 2020年12月24日10:58:33
 * desc   : 登录界面
 */
@Route(path = RouteUtil.PATH_LOGIN_INVALID)
public final class LoginActivity extends MyActivity
        implements UmengLogin.OnLoginListener, SpannableStringHelper.ProtocolHelperListener {

    @DebugLog
    public static void start(Context context, String phone, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IntentKey.PHONE, phone);
        intent.putExtra(IntentKey.PASSWORD, password);
        context.startActivity(intent);
    }

    private ViewGroup mBodyLayout;
    private EditText mPhoneView;
    private EditText mPasswordView;

    private View mForgetView;
    private TextView tvLoginProtocol;
    private Button mCommitView;
    private View mWeChatView;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        mBodyLayout = findViewById(R.id.ll_login_body);
        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mForgetView = findViewById(R.id.tv_login_forget);
        mCommitView = findViewById(R.id.btn_login_commit);
        mWeChatView = findViewById(R.id.iv_login_wechat);
        tvLoginProtocol = findViewById(R.id.tv_login_protocol);
        setOnClickListener(mForgetView, mCommitView, mWeChatView);

        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mPasswordView)
                .setMain(mCommitView)
                .build();
    }

    @Override
    protected void initData() {
        post(this::initProtocol);
        // 判断用户当前有没有安装微信
        if (!UmengClient.isAppInstalled(this, Platform.WECHAT)) {
            mWeChatView.setVisibility(View.GONE);
        }

        // 填充传入的手机号和密码
        mPhoneView.setText(getString(IntentKey.PHONE));
        mPasswordView.setText(getString(IntentKey.PASSWORD));
    }

    private void initProtocol() {
        String str1 = "登录/注册即代表同意";
        String str2 = "和";
        String protocol = "《用户服务协议》";
        String secrecy = "《隐私政策》";

        tvLoginProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        tvLoginProtocol.setText(SpannableStringHelper.setProtocolString(this, str1, str2, protocol, secrecy, this));
        tvLoginProtocol.setHighlightColor(ContextCompat.getColor(this, android.R.color.transparent));
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
            startActivity(PasswordForgetActivity.class);
        } else if (v == mCommitView) {
            if (mPhoneView.getText().toString().length() != 11) {
                toast(R.string.common_phone_input_error);
                return;
            }

            if (true) {
                showDialog();
                postDelayed(() -> {
                    hideDialog();
                    startActivity(HomeActivity.class);
                    finish();
                }, 2000);
                return;
            }

            EasyHttp.post(this)
                    .api(new LoginApi()
                            .setPhone(mPhoneView.getText().toString())
                            .setPassword(mPasswordView.getText().toString()))
                    .request(new HttpCallback<HttpData<LoginBean>>(this) {

                        @Override
                        public void onSucceed(HttpData<LoginBean> data) {
                            // 更新 Token
                            EasyConfig.getInstance()
                                    .addParam("token", data.getData().getToken());
                            // 跳转到主页
                            startActivity(HomeActivity.class);
                            finish();
                        }
                    });
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
    public void protocolClick() {
        toast("用户服务协议");
    }

    @Override
    public void secrecyClick() {
        toast("点击隐私政策");
    }
}